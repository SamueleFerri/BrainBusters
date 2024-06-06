package com.example.brainbusters.ui.views

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.brainbusters.Routes
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.ByteArrayOutputStream
import java.util.*

@Composable
fun RegisterStepOneScreen(navController: NavController, profilePictureUri: Uri?, onProfilePictureChange: (Uri) -> Unit, firstName: String, onFirstNameChange: (String) -> Unit, lastName: String, onLastNameChange: (String) -> Unit, position: String, onPositionChange: (String) -> Unit, onProceed: () -> Unit) {
    val context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    // Launcher to request location permissions
    val locationPermissionRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true || permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
            getLastKnownLocation(fusedLocationClient, context) { city ->
                onPositionChange(city)
            }
        }
    }

    // Check and request location permissions
    LaunchedEffect(Unit) {
        when {
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                getLastKnownLocation(fusedLocationClient, context) { city ->
                    onPositionChange(city)
                }
            }
            else -> {
                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    // Launcher to pick an image from gallery
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onProfilePictureChange(it) }
    }

    // Launcher to take a photo using the camera
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            val uri = bitmapToUri(context, it)
            onProfilePictureChange(uri)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Create Account", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        // Profile Picture
        profilePictureUri?.let {
            Image(
                bitmap = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, it).asImageBitmap()
                } else {g
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    ImageDecoder.decodeBitmap(source).asImageBitmap()
                },
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .aspectRatio(1f)
            )
        } ?: run {
            // Placeholder for profile picture
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .aspectRatio(1f)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Add Photo", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Button to pick image from gallery
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { pickImageLauncher.launch("image/*") }) {
                Text("Pick from Gallery")
            }

            // Button to take photo with camera
            Button(onClick = { takePictureLauncher.launch(null) }) {
                Text("Take Photo")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = firstName,
            onValueChange = onFirstNameChange,
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = lastName,
            onValueChange = onLastNameChange,
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = position,
            onValueChange = onPositionChange,
            label = { Text("Position") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false // Disable manual editing of the position
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onProceed,
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text(text = "Next")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate(Routes.loginScreen) }) {
            Text(text = "Already have an account? Log in", fontSize = 16.sp)
        }
    }
}

private fun getLastKnownLocation(
    fusedLocationClient: FusedLocationProviderClient,
    context: Context,
    onLocationReceived: (String) -> Unit
) {
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        location?.let {
            val city = getCityName(context, it.latitude, it.longitude)
            onLocationReceived(city)
        }
    }
}

private fun getCityName(context: Context, latitude: Double, longitude: Double): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
    return addresses?.get(0)?.locality ?: "Unknown city"
}

private fun bitmapToUri(context: Context, bitmap: Bitmap): Uri {
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
    return Uri.parse(path)
}
