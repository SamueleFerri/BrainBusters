package com.example.brainbusters.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import com.example.brainbusters.data.entities.User
import com.example.brainbusters.data.repositories.UsersRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
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

    }
}
