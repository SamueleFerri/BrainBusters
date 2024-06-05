package com.example.brainbusters.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.brainbusters.data.daos.UsersDAO
import com.example.brainbusters.data.entities.User

@Database(
    entities = [User::class],
    version = 1)
abstract class BrainBusterDatabase: RoomDatabase() {
    abstract fun usersDAO(): UsersDAO

}

