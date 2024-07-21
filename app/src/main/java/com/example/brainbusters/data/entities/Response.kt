package com.example.brainbusters.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "responses",
    foreignKeys = [ForeignKey(
        entity = Question::class,
        parentColumns = ["question_id"],
        childColumns = ["response_question_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["response_question_id"])]
)
data class Response(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "response_id")
    val responseId: Int = 0,

    @ColumnInfo(name = "response_text")
    var text: String,

    @ColumnInfo(name = "response_score")
    var score: Int,

    @ColumnInfo(name = "response_question_id")
    var questionId: Int
)