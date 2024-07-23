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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.brainbusters.R
import com.example.brainbusters.ui.components.PieChartScreen
import com.example.brainbusters.ui.viewModels.CareerViewModel
import com.example.brainbusters.ui.viewModels.QuizDoneViewModel
import com.example.brainbusters.ui.viewModels.UserViewModel
import kotlinx.coroutines.flow.firstOrNull
import org.koin.androidx.compose.koinViewModel

@Composable
fun Profile(navController: NavController) {
    val userEmail = UserViewModel.getEmail()
    Log.e("LOG", "$userEmail")

    var username by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf(0) }
    var userBadge by remember { mutableStateOf("") }
    var quizTaken by remember { mutableStateOf(0) }
    var userLevel by remember { mutableStateOf(0) }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    val viewModel: UserViewModel = koinViewModel()
    val careerViewModel: CareerViewModel = koinViewModel()
    val quizDoneViewModel: QuizDoneViewModel = koinViewModel()

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
            profileImageUri = Uri.parse(it.userImage)
            username = it.userUsername
            userId = it.userId
            userBadge = viewModel.actions.getBadgeByUserId(userId)?.title ?: ""
            quizTaken = careerViewModel.getQuizTaken(userId)
            userLevel = careerViewModel.getUserLevel(userId)

            Log.d("ProfileDebug", "Quiz taken: $quizTaken")

            val colorString = viewModel.actions.getHighestBadgeColor(userId)
            try {
                val badgeColor = Color(android.graphics.Color.parseColor(colorString))
                // Usa badgeColor dove necessario
            } catch (e: IllegalArgumentException) {
                Log.e("ProfileDebug", "Invalid color format: $colorString")
                // Gestisci il colore di fallback se necessario
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
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(profileImageUri)
                        .apply {
                            placeholder(R.drawable.logo_brain_busters) // Immagine di placeholder
                            error(R.drawable.logo_brain_busters) // Immagine in caso di errore
                        }
                        .build(),
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
                        .background(Color.Gray) // Modifica con il colore effettivo del badge
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
            PieChartScreen(userId = userId, viewModel = quizDoneViewModel)
        }
    }
}