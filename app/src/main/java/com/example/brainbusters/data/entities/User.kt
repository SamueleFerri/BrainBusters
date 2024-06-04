package com.example.brainbusters.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val position: String,
    val profilePictureUri: String?
)