package com.example.brainbusters.ui.viewModels

import android.icu.text.Transliterator.Position
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import com.example.brainbusters.data.entities.User
import com.example.brainbusters.data.repositories.UsersRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
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
    fun login(email: String, password: String) : Boolean
    fun register(
        name: String,
        surname: String,
        username: String,
        email: String,
        password: String, image: String, position: String): Boolean
}

class UserViewModel(
    private val userRepository: UsersRepository
) : ViewModel() {

    val state = userRepository.users.map { UsersState(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UsersState(emptyList())
    )



    var emailU = ""
    var passwordU = ""

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

        override fun login(email: String, password: String): Boolean {
            return runBlocking {
                if(userRepository.getUserByEmail(email).isNotNull()) {
                    println("ciao")
                    false
                } else {
                    val user = userRepository.getUserByEmail(email).first()
                    if (user.userPassword == password) {
                        // Le credenziali sono corrette, esegui il login
                        println("Login effettuato con successo")
                        emailU = email
                        passwordU = password
                        true
                    } else {
                        // Le credenziali non sono corrette, gestisci l'errore
                        println("Email o password non corrette")
                        false
                    }
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
                println("Utente registrato con successo")
                true
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
