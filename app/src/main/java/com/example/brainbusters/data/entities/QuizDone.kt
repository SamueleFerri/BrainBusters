package com.example.brainbusters.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "quizzes_done", foreignKeys = [
    ForeignKey(entity = Quiz::class,
        parentColumns = arrayOf("quiz_id"),
        childColumns = arrayOf("quizDone_quizId"),
        onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = User::class,
        parentColumns = arrayOf("user_id"),
        childColumns = arrayOf("quizDone_userId"),
        onDelete = ForeignKey.CASCADE)]
)
data class QuizDone(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "quizDone_id")
    val id: Int = 0,

    @ColumnInfo(name = "quizDone_quizId")
    val quizId: Int,

    @ColumnInfo(name = "quizDone_userId")
    val userId: Int,

    @ColumnInfo(name = "quizDone_score")
    val score: Int
)