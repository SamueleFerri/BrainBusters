package com.example.brainbusters.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "careers", foreignKeys = [
    ForeignKey(entity = User::class,
        parentColumns = arrayOf("user_id"),
        childColumns = arrayOf("career_user_id"),
        onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = Badge::class,
        parentColumns = arrayOf("badge_id"),
        childColumns = arrayOf("career_badge_id"),
        onDelete = ForeignKey.CASCADE)
])
data class Career(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "career_id")
    val careerId: Int = 0,

    @ColumnInfo(name = "career_score")
    var score: Int,

    @ColumnInfo(name = "career_user_id")
    var userId: Int,

    @ColumnInfo(name = "career_badge_id")
    var badgeId: Int
)
