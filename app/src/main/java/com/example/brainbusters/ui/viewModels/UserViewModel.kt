package com.example.brainbusters.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbusters.data.entities.User
import com.example.brainbusters.data.repositories.UsersRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class UsersState(val users: List<User>)

interface UsersActions {
    fun addUser(user: User): Job
    fun removeUser(user: User): Job
    fun updateUser(user: User): Job
}

class UserViewModel(
    private val userRepository: UsersRepository
) : ViewModel() {
    val state = userRepository.users.map { UsersState(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UsersState(emptyList())
    )

    val actions = object : UsersActions {
        override fun addUser(user: User) = viewModelScope.launch {
            userRepository.insertNewUser(user)
        }

        override fun removeUser(user: User) = viewModelScope.launch {
            userRepository.deleteUser(user)
        }

        override fun updateUser(user: User) = viewModelScope.launch {
            userRepository.updateUser(user)
        }
    }


}