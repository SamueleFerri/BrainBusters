package com.example.brainbusters.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "quizzes",
    indices = [Index(
        value = ["quiz_title"],
        unique = true
    )])
data class Quiz(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "quiz_id")
    val quizId: Int = 0,

    @ColumnInfo(name = "quiz_title")
    var title: String,

    @ColumnInfo(name = "quiz_category")
    var categoryName: String,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
)