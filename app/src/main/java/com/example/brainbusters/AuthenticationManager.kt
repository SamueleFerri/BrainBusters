/*package com.example.brainbusters

import com.example.brainbusters.data.entities.User
import com.example.brainbusters.ui.viewModels.UserViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

class AuthenticationManager(private val userViewModel: UserViewModel) {

    var isLoggedIn = false
        private set

    var currentUser: User? = null
        private set

    fun login(email: String, password: String): Boolean {
        return runBlocking {
            if (userViewModel.actions.login(email, password)) {
                isLoggedIn = true
                currentUser = userViewModel.getUserByEmail(email)
                true
            } else {
                isLoggedIn = false
                currentUser = null
                false
            }
        }
    }

    fun logout() {
        isLoggedIn = false
        currentUser = null
    }

    private fun UserViewModel.getUserByEmail(email: String): User? {
        return runBlocking {
            userViewModel.actions.getRepository().getUserByEmail(email).firstOrNull()
        }
    }
}          */