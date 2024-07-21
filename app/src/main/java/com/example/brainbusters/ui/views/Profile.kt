package com.example.brainbusters.ui.views

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.brainbusters.ui.components.PieChartScreen
import com.example.brainbusters.ui.viewModels.CareerViewModel
import com.example.brainbusters.ui.viewModels.UserViewModel
import kotlinx.coroutines.flow.firstOrNull
import org.koin.androidx.compose.koinViewModel

@Composable
fun Profile(navController: NavController) {
    val userEmail = UserViewModel.getEmail()
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var username by remember { mutableStateOf("") }
    var userId by remember { mutableIntStateOf(1) }
    var userBadge by remember { mutableStateOf("") }
    var quizTaken by remember { mutableIntStateOf(0) }
    var userLevel by remember { mutableIntStateOf(0) }
    val viewModel: UserViewModel = koinViewModel()
    val careerViewModel: CareerViewModel = koinViewModel()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            profileImageUri = it
            viewModel.actions.saveProfileImageUri(userEmail, it.toString())
        }
    }

    LaunchedEffect(userEmail) {
        val user = viewModel.actions.getRepository().getUserByEmail(userEmail).firstOrNull()
        user?.let {
            Log.d("ProfileDebug", "User: $it")
            profileImageUri = Uri.parse(it.userImage)
            username = it.userUsername
        }
    }

    // Utilizza produceState per ottenere il colore del badge
    val badgeColor by produceState(initialValue = Color.Gray, key1 = userEmail) {
        val user = viewModel.actions.getRepository().getUserByEmail(userEmail).firstOrNull()
        user?.let {
            Log.d("ProfileDebug", "User: $it")
            profileImageUri = Uri.parse(it.userImage)
            username = it.userUsername
            userId = it.userId
            Log.d("ProfileDebug", "UserId: $userId")
            userBadge = viewModel.actions.getBadgeByUserId(userId)?.title ?: ""

            quizTaken = careerViewModel.getQuizTaken(userId)
            userLevel = careerViewModel.getUserLevel(userId)

            val colorString = viewModel.actions.getHighestBadgeColor(userId)
            Log.d("ProfileDebug", "Badge color string: $colorString")

            // Converte la stringa del colore in un oggetto Color
            value = try {
                Color(android.graphics.Color.parseColor(colorString))
            } catch (e: IllegalArgumentException) {
                // Gestisce il caso in cui la stringa del colore non sia valida
                Log.e("ProfileDebug", "Invalid color format: $colorString")
                Color.Gray // Imposta un colore di fallback
            }
        }
    }



    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = Modifier.size(120.dp), contentAlignment = Alignment.BottomEnd) {
                val painter = rememberAsyncImagePainter(
                    model = profileImageUri ?: Uri.EMPTY
                )
                Image(
                    painter = painter,
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        .clickable { launcher.launch("image/*") }
                )
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(badgeColor)
                        .border(2.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "★",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Column {
                    Text(
                        text = username,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = userBadge,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.padding(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quizzes taken: $quizTaken",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    Text(
                        text = "Level: $userLevel",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Spacer(modifier = Modifier.padding(8.dp))

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            PieChartScreen()
        }
    }
}