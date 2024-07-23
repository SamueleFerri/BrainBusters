package com.example.brainbusters.data.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.brainbusters.data.entities.User
import com.example.brainbusters.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class RegistrationViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName.asStateFlow()

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName.asStateFlow()

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _position = MutableStateFlow("")
    val position: StateFlow<String> = _position.asStateFlow()

    private val _profilePictureUri = MutableStateFlow<Uri?>(null)
    val profilePictureUri: StateFlow<Uri?> = _profilePictureUri.asStateFlow()

    fun setFirstName(value: String) {
        _firstName.value = value
    }

    fun setLastName(value: String) {
        _lastName.value = value
    }

    fun setUsername(value: String) {
        _username.value = value
    }

    fun setEmail(value: String) {
        _email.value = value
    }

    fun setPassword(value: String) {
        _password.value = value
    }

    fun setPosition(value: String) {
        _position.value = value
    }

    fun setProfilePictureUri(uri: Uri) {
        _profilePictureUri.value = uri
    }

    suspend fun registerUser() {
        val user = User(
            firstName = firstName.value,
            lastName = lastName.value,
            username = username.value,
            email = email.value,
            password = password.value,
            position = position.value,
            profilePictureUri = profilePictureUri.value.toString()
        )
        userRepository.addUser(user)
    }
}