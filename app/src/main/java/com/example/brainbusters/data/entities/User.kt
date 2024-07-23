package com.example.brainbusters.data.entities

<<<<<<< HEAD
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users", indices = [Index(value = ["user_name", "user_email"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userId: Int = 0,

    @ColumnInfo(name = "user_name")
    var userName: String,

    @ColumnInfo(name = "user_surname")
    var userSurname: String,

    @ColumnInfo(name = "user_username")
    var userUsername: String,

    @ColumnInfo(name = "user_email")
    var userEmail: String,

    @ColumnInfo(name = "user_password")
    var userPassword: String,

    @ColumnInfo(name = "user_image")
    var userImage: String,

    @ColumnInfo(name = "user_position")
    var userPosition: String,
)
=======
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
>>>>>>> f0c589755a7f82f7fa92bb46625664d64c36bca9
