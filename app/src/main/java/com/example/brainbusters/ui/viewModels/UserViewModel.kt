package com.example.brainbusters.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbusters.data.entities.User
import com.example.brainbusters.data.repositories.UsersRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
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
    fun getProfileImageUri(userEmail: String): Flow<String>
    fun saveProfileImageUri(userEmail: String, uri: String): Job
    fun login(email: String, password: String) : Boolean
    fun register(
        name: String,
        surname: String,
        username: String,
        email: String,
        password: String, image: String, position: String): Boolean
    fun changePassword(oldPassword: String, newPassword: String): Boolean
    fun printUserEmailsAndPasswords()
}

class UserViewModel(
    private val userRepository: UsersRepository
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

        // Funzione per ottenere l'immagine del profilo
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
                    // Le credenziali sono corrette, esegui il login
                    println("Login effettuato con successo")
                    setEmail(email)
                    setPassword(password)
                    true
                } else {
                    // Le credenziali non sono corrette, gestisci l'errore
                    println("Email o password non corrette")
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
                // Controllo se l'email è valida (deve contenere '@')
                if (!isValidEmail(email)) {
                    println("Email non valida")
                    return@runBlocking false
                }

                // Controllo se l'email esiste nel repository
                val existingUser = userRepository.getUserByEmail(email).firstOrNull()
                if (existingUser != null) {
                    println("Email già registrata")
                    return@runBlocking false
                }

                // Controllo se la password è valida (deve essere lunga almeno 8 caratteri)
                if (!isValidPassword(password)) {
                    println("Password non valida")
                    return@runBlocking false
                }

                // Creazione dell'oggetto User e inserimento nel repository
                val newUser = User(
                    userName = name,
                    userSurname =  surname,
                    userUsername = username,
                    userEmail = email,
                    userPassword = password,
                    userImage = image,
                    userPosition = position
                )
                userRepository.insertNewUser(newUser)
                setEmail(email)
                setPassword(password)
                println("Utente registrato con successo")
                true
            }
        }

        override fun changePassword(oldPassword: String, newPassword: String): Boolean {
            return runBlocking {
                // Recupera l'utente dal repository
                val user = userRepository.getUserByEmail(getEmail()).firstOrNull()
                println(getEmail() + "ds")
                println(user)
                // Controlla se l'utente esiste e la vecchia password corrisponde
                if (user != null && user.userPassword == oldPassword) {
                    // Controlla se la nuova password è valida
                    if (isValidPassword(newPassword)) {
                        // Aggiorna la password dell'utente
                        val updatedUser = user.copy(userPassword = newPassword)
                        userRepository.updateUser(updatedUser)
                        println("Password cambiata con successo")
                        return@runBlocking true
                    } else {
                        println("Nuova password non valida")
                        return@runBlocking false
                    }
                } else {
                    println("Utente non trovato o vecchia password errata")
                    return@runBlocking false
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

    // Funzione di validazione dell'email
    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && email.contains('@')
    }

    // Funzione di validazione della password
    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }
}