package com.example.brainbusters.ui.views

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.brainbusters.Routes
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.Locale

@Composable
fun RegisterStepOneScreen(
    navController: NavController,
    profilePictureUri: Uri?,
    onProfilePictureChange: (Uri) -> Unit,
    firstName: String,
    onFirstNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
    position: String,
    onPositionChange: (String) -> Unit,
    onProceed: () -> Unit
) {
    val context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    // Launcher to request location permissions
    val locationPermissionRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true || permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
            getLastKnownLocation(fusedLocationClient, context) { city ->
                onPositionChange(city)
            }
        } else {
            // Handle the case where permissions are denied
            onPositionChange("Location permission denied")
        }
    }

    // Check and request location permissions
    LaunchedEffect(Unit) {
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
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
                } else {
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
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "Next")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate(Routes.loginScreen) }) {
            Text(text = "Already have an account? Log in", fontSize = 16.sp)
        }
    }
}

@SuppressLint("MissingPermission")
private fun getLastKnownLocation(
    fusedLocationClient: FusedLocationProviderClient,
    context: Context,
    onLocationReceived: (String) -> Unit
) {
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        location?.let {
            getCityNameAsync(context, it.latitude, it.longitude) { city ->
                onLocationReceived(city)
            }
        } ?: run {
            // Handle the case where location is null
            onLocationReceived("Location not available")
        }
    }.addOnFailureListener {
        // Handle failure
        onLocationReceived("Failed to get location")
    }
}

private fun getCityNameAsync(
    context: Context,
    latitude: Double,
    longitude: Double,
    onCityNameReceived: (String) -> Unit
) {
    val geocoder = Geocoder(context, Locale.getDefault())
    CoroutineScope(Dispatchers.IO).launch {
        try {
            // Increase the timeout if necessary by retrying the request or using alternative methods
            val addresses = withTimeoutOrNull(10000L) { // 10 seconds timeout
                geocoder.getFromLocation(latitude, longitude, 1)
            }
            val cityName = addresses?.firstOrNull()?.locality ?: "Unknown city"
            withContext(Dispatchers.Main) {
                onCityNameReceived(cityName)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                onCityNameReceived("Error getting city name")
            }
        }
    }
}

private fun bitmapToUri(context: Context, bitmap: Bitmap): Uri {
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
    return Uri.parse(path)
}