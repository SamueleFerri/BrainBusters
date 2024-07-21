package com.example.brainbusters.data.repositories

import com.example.brainbusters.data.daos.UsersDao
import com.example.brainbusters.data.entities.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class UsersRepository(private val usersDAO: UsersDao) {

    val users: Flow<List<User>> = usersDAO.getAllUsers()

    suspend fun insertNewUser(user: User) {
        usersDAO.insert(user)
    }

    suspend fun updateUser(user: User) {
        usersDAO.update(user)
    }

    suspend fun deleteUserById(userId: Int) {
        usersDAO.deleteUserById(userId)
    }

    suspend fun updateUserImage(userEmail: String, userImage: String) {
        usersDAO.updateUserImage(userEmail, userImage)
    }

    fun getUserById(userId: Int): Flow<User> {
        return usersDAO.getUserById(userId)
    }

    fun getUserByUserName(userName: String): Flow<User> {
        return usersDAO.getUserByUserName(userName)
    }

    fun getUserByEmail(userEmail: String): Flow<User>{
        return usersDAO.getUserByEmail(userEmail)
    }

    fun getUserIdByEmail(userEmail: String): Flow<Int?>{
        return usersDAO.getUserIdByEmail(userEmail)
    }

    fun getAllUsers(): Flow<List<User>>{
        return usersDAO.getAllUsers()
    }

    suspend fun getUserIdfromMail(userEmail: String): Int{
        return usersDAO.getIdFromEmail(userEmail).firstOrNull()?:0
    }
}