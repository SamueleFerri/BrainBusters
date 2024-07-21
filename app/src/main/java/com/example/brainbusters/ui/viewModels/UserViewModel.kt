package com.example.brainbusters.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbusters.data.entities.Badge
import com.example.brainbusters.data.entities.Career
import com.example.brainbusters.data.entities.User
import com.example.brainbusters.data.repositories.BadgeRepository
import com.example.brainbusters.data.repositories.CareerRepository
import com.example.brainbusters.data.repositories.UsersRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

data class UsersState(val users: List<User>)

interface UsersActions {
    fun addUser(user: User): Job
    fun removeUser(userId: Int): Job
    fun updateUser(user: User): Job
    fun getRepository(): UsersRepository
    fun getUserIdByEmail(email: String): Int
    fun getProfileImageUri(userEmail: String): Flow<String>
    fun saveProfileImageUri(userEmail: String, uri: String): Job
    fun login(email: String, password: String): Boolean
    fun register(
        name: String,
        surname: String,
        username: String,
        email: String,
        password: String, image: String, position: String): Boolean
    fun createCareer(userId: Int): Job
    suspend fun getHighestBadgeColor(userId: Int): String
    suspend fun getBadgeByUserId(userId: Int): Badge?
    fun changePassword(oldPassword: String, newPassword: String): Boolean
    fun printUserEmailsAndPasswords()
}

class UserViewModel(
    private val userRepository: UsersRepository,
    private val careerRepository: CareerRepository,
    private val badgeRepository: BadgeRepository
) : ViewModel() {

    val state = userRepository.users.map { UsersState(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UsersState(emptyList())
    )

    companion object {
        private var emailU: String = ""
        private var passwordU: String = ""

        fun getEmail(): String {
            return emailU
        }

        fun setEmail(email: String) {
            emailU = email
        }

        fun getPassword(): String {
            return passwordU
        }

        fun setPassword(password: String) {
            passwordU = password
        }
    }

    val actions = object : UsersActions {
        override fun addUser(user: User) = viewModelScope.launch {
            try {
                userRepository.insertNewUser(user)
            } catch (e: Exception) {
                throw e
            }
        }

        override fun removeUser(userId: Int) = viewModelScope.launch {
            try {
                userRepository.deleteUserById(userId)
            } catch (e: Exception) {
                throw e
            }
        }

        override fun updateUser(user: User) = viewModelScope.launch {
            try {
                userRepository.updateUser(user)
            } catch (e: Exception) {
                throw e
            }
        }

        override fun getRepository(): UsersRepository {
            return userRepository
        }

        override fun getUserIdByEmail(email: String): Int {
            return runBlocking {
                try {
                    userRepository.getUserIdfromMail(email)
                } catch (e: Exception) {
                    0 // or handle the exception in a suitable way
                }
            }
        }

        override fun getProfileImageUri(userEmail: String): Flow<String> {
            return userRepository.getUserByEmail(userEmail).map { it.userImage }
        }

        override fun saveProfileImageUri(userEmail: String, uri: String): Job = viewModelScope.launch {
            viewModelScope.launch {
                try {
                    userRepository.updateUserImage(userEmail, uri)
                } catch (e: Exception) {
                    Log.e("ProfileDebug", "Error saving profile image URI", e)
                }
            }
        }

        override fun login(email: String, password: String): Boolean {
            return runBlocking {
                val user = userRepository.getUserByEmail(email).firstOrNull()
                if (user != null && user.userPassword == password) {
                    setEmail(email)
                    setPassword(password)
                    true
                } else {
                    false
                }
            }
        }

        override fun register(
            name: String,
            surname:  String,
            username: String,
            email: String,
            password: String,
            image: String,
            position: String
        ): Boolean {
            return runBlocking {
                if (!isValidEmail(email)) {
                    return@runBlocking false
                }

                val existingUser = userRepository.getUserByEmail(email).firstOrNull()
                if (existingUser != null) {
                    return@runBlocking false
                }

                if (!isValidPassword(password)) {
                    return@runBlocking false
                }

                val newUser = User(
                    userName = name,
                    userSurname = surname,
                    userUsername = username,
                    userEmail = email,
                    userPassword = password,
                    userImage = image,
                    userPosition = position
                )

                try {
                    userRepository.insertNewUser(newUser)
                    val createdUser = userRepository.getUserByEmail(email).firstOrNull()

                    if (createdUser != null) {
                        setEmail(email)
                        setPassword(password)
                        createCareer(createdUser.userId)
                        true
                    } else {
                        false
                    }
                } catch (e: Exception) {
                    false
                }
            }
        }

        override fun createCareer(userId: Int) = viewModelScope.launch {
            try {
                val user = userRepository.getUserById(userId).firstOrNull()
                if (user == null) {
                    Log.e("UserViewModel", "User not found for userId: $userId")
                    return@launch
                }

                // Verifica se il badge con ID 0 esiste, altrimenti lo crea
                val badge = badgeRepository.getBadgeById(1)

                if (badge != null) {
                    val newCareer = Career(score = 0, userId = userId, badgeId = badge.badgeId)
                    careerRepository.insertNewCareer(newCareer)
                    Log.d("UserViewModel", "Career created successfully")
                } else {
                    Log.e("UserViewModel", "Failed to create badge with ID 0")
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error creating career", e)
            }
        }

        // Funzione per ottenere il colore del badge con l'ID più alto per un dato userId
        override suspend fun getHighestBadgeColor(userId: Int): String {
            return try {
                // Assicurati che questa funzione venga chiamata in un contesto di coroutine
                var badgeColor = "#808080" // Colore di fallback
                careerRepository.getCareerByUserId(userId).firstOrNull()?.let { careers ->
                    Log.d("UserViewModel", "Careers: $careers")

                    val badge = badgeRepository.getBadgeById(careers.badgeId)
                    badgeColor = badge?.color ?: "#808080"
                    Log.d("UserViewModel", "Badge Color: $badgeColor")
                }
                badgeColor
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error retrieving badge color", e)
                "#808080" // Colore di fallback in caso di errore
            }
        }

        override suspend fun getBadgeByUserId(userId: Int): Badge? {
            return try {
                val career = careerRepository.getCareerByUserId(userId).firstOrNull()
                val badge = career?.let { badgeRepository.getBadgeById(it.badgeId) }
                badge
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error retrieving career", e)
                null
            }
        }

        override fun changePassword(oldPassword: String, newPassword: String): Boolean {
            return runBlocking {
                val user = userRepository.getUserByEmail(getEmail()).firstOrNull()
                if (user != null && user.userPassword == oldPassword) {
                    if (isValidPassword(newPassword)) {
                        val updatedUser = user.copy(userPassword = newPassword)
                        userRepository.updateUser(updatedUser)
                        true
                    } else {
                        false
                    }
                } else {
                    false
                }
            }
        }

        val usersFlow: Flow<List<User>> = userRepository.getAllUsers()

        override fun printUserEmailsAndPasswords() {
            viewModelScope.launch {
                usersFlow.collect { userList ->
                    userList.forEach { user ->
                        println("Email: ${user.userEmail}, Password: ${user.userPassword}")
                    }
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && email.contains('@')
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }
}