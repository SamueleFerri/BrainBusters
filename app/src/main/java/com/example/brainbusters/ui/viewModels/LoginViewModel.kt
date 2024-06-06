package com.example.brainbusters.ui.viewModels

import androidx.lifecycle.ViewModel
import com.example.brainbusters.data.daos.UsersDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class LoginViewModel(private val userDao: UsersDao) : ViewModel() {

    var userEmail = ""
    var userPassword = ""

    // Funzione per eseguire il login
    suspend fun login(email : String, password : String) : Boolean {
        // Esegui la logica di controllo email e password
        return withContext(Dispatchers.IO) {
            val user = userDao.getUserByEmail(email)

            if (user.first().userPassword == password) {
                // Le credenziali sono corrette, esegui il login
                println("Login effettuato con successo")
                userEmail = email
                userPassword = password
                true
            } else {
                // Le credenziali non sono corrette, gestisci l'errore
                println("Email o password non corrette")
                false
            }
        }
    }
}
