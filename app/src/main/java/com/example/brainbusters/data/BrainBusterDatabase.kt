package com.example.brainbusters.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.brainbusters.data.daos.BadgesDao
import com.example.brainbusters.data.daos.CareersDao
import com.example.brainbusters.data.daos.QuizzesDao
import com.example.brainbusters.data.daos.UsersDao
import com.example.brainbusters.data.entities.Badge
import com.example.brainbusters.data.entities.Career
import com.example.brainbusters.data.entities.Quiz
import com.example.brainbusters.data.entities.User

@Database(
    entities = [User::class,
               Career::class,
               Badge::class,
               Quiz::class],
    version = 1)
abstract class BrainBusterDatabase: RoomDatabase() {
    abstract fun usersDAO(): UsersDao
    abstract fun careersDAO(): CareersDao
    abstract fun badgesDAO(): BadgesDao
    abstract fun quizzesDAO(): QuizzesDao
}

