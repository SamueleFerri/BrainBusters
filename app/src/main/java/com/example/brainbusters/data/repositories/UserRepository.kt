package com.example.brainbusters.data.repositories

import com.example.brainbusters.data.daos.UsersDAO
import com.example.brainbusters.data.entities.User
import kotlinx.coroutines.flow.Flow

class UsersRepository(private val usersDAO: UsersDAO) {

    val users: Flow<List<User>> = usersDAO.getUsers()

    suspend fun insertNewUser(user: User) {
        usersDAO.insert(user)
    }

    suspend fun updateUser(user: User) {
        usersDAO.update(user)
    }

    suspend fun deleteUser(user: User) {
        usersDAO.delete(user.userId)
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

    fun getUserIdByEmail(userEmail: String): Flow<Int>{
        return usersDAO.getUserIdByEmail(userEmail)
    }

    fun getAllUsers(): Flow<List<User>>{
        return usersDAO.getAllUsers()
    }

    fun getUserIdfromMail(userEmail: String): Flow<Int>{
        return usersDAO.getIdFromEmail(userEmail)
    }
}