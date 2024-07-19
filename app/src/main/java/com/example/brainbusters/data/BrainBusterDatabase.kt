package com.example.brainbusters.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.brainbusters.data.daos.BadgesDao
import com.example.brainbusters.data.daos.CareersDao
import com.example.brainbusters.data.daos.QuizzesDao
import com.example.brainbusters.data.daos.UsersDao
import com.example.brainbusters.data.entities.Badge
import com.example.brainbusters.data.entities.Career
import com.example.brainbusters.data.entities.Quiz
import com.example.brainbusters.data.entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [User::class, Career::class, Badge::class, Quiz::class],
    version = 1
)
abstract class BrainBusterDatabase : RoomDatabase() {
    abstract fun usersDAO(): UsersDao
    abstract fun careersDAO(): CareersDao
    abstract fun badgesDAO(): BadgesDao
    abstract fun quizzesDAO(): QuizzesDao

    class Callback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.quizzesDAO())
                }
            }
        }

        private suspend fun populateDatabase(quizzesDao: QuizzesDao) {
            val quizzes = listOf(
                Quiz(title = "Quiz 1", categoryName = "Geography"),
                Quiz(title = "Quiz 2", categoryName = "Geography"),
                Quiz(title = "Quiz 3", categoryName = "Geography"),
                Quiz(title = "Quiz 4", categoryName = "Geography"),
                Quiz(title = "Quiz 5", categoryName = "Geography"),
                Quiz(title = "Quiz 6", categoryName = "Geography")
            )
            quizzesDao.insertAll(quizzes)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: BrainBusterDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): BrainBusterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BrainBusterDatabase::class.java,
                    "database"
                )
                    .addCallback(Callback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

