package com.example.brainbusters.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "questions", foreignKeys = [
    ForeignKey(entity = Quiz::class,
        parentColumns = arrayOf("quiz_id"),
        childColumns = arrayOf("question_quizId"),
        onDelete = ForeignKey.CASCADE)]
    )
data class Question(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "question_id")
    val questionId: Int = 0,

    @ColumnInfo(name = "question_number")
    var number: String,

    @ColumnInfo(name = "question_question")
    var question: String,

    @ColumnInfo(name = "question_quizId")
    var quizId: Int
)


