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
import com.example.brainbusters.data.viewmodels.RegistrationViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

@Composable
fun RegisterStepOneScreen(
    navController: NavController,
    registrationViewModel: RegistrationViewModel
) {
    val context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val coroutineScope = rememberCoroutineScope()

    // Launcher to request location permissions
    val locationPermissionRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true || permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
            coroutineScope.launch {
                getLastKnownLocation(fusedLocationClient, context) { city ->
                    registrationViewModel.setPosition(city)
                }
            }
        }
    }

    // Check and request location permissions
    LaunchedEffect(Unit) {
        when {
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                coroutineScope.launch {
                    getLastKnownLocation(fusedLocationClient, context) { city ->
                        registrationViewModel.setPosition(city)
                    }
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
        uri?.let { registrationViewModel.setProfilePictureUri(it) }
    }

    // Launcher to take a photo using the camera
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            val uri = bitmapToUri(context, it)
            registrationViewModel.setProfilePictureUri(uri)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Create Account - Step 1", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        // Profile Picture
        registrationViewModel.profilePictureUri.value?.let {
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
            value = registrationViewModel.firstName.value,
            onValueChange = { registrationViewModel.setFirstName(it) },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = registrationViewModel.lastName.value,
            onValueChange = { registrationViewModel.setLastName(it) },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = registrationViewModel.username.value,
            onValueChange = { registrationViewModel.setUsername(it) },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = registrationViewModel.position.value,
            onValueChange = { /* No-op, position is updated automatically */ },
            label = { Text("Position") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false // Disable manual editing of the position
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate(Routes.registerStepTwo)
            },
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


suspend fun getLastKnownLocation(
    fusedLocationClient: FusedLocationProviderClient,
    context: Context,
    onSuccess: (String) -> Unit
) {
    withContext(Dispatchers.IO) {
        try {
            val location = fusedLocationClient.lastLocation.await()
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            val city = addresses?.firstOrNull()?.locality ?: "Unknown"
            onSuccess(city)
        } catch (e: Exception) {
            e.printStackTrace()
            onSuccess("Unknown")
        }
    }
}


fun bitmapToUri(context: Context, bitmap: Bitmap): Uri {
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
    return Uri.parse(path)
}

