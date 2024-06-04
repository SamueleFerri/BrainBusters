package com.example.brainbusters.data.repositories
import com.example.brainbusters.data.daos.UserDao
import com.example.brainbusters.data.entities.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) {

    suspend fun addUser(user: User) {
        userDao.insertUser(user)
    }


    // Altre funzioni per ottenere, aggiornare o eliminare utenti dal database,
    // a seconda delle tue esigenze
}
