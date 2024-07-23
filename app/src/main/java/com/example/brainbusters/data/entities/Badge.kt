package com.example.brainbusters.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "badges", indices = [Index(
    value = ["badge_title"],
    unique = true
)])
data class Badge(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "badge_id")
    val badgeId: Int = 0,

    @ColumnInfo(name = "badge_title")
    var title: String,

    @ColumnInfo(name = "badge_color")
    var color: String,

    @ColumnInfo(name = "required_quizzes")
    var requiredQuizes: Int
)
