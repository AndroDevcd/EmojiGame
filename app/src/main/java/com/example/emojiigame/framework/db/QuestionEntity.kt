package com.example.emojiigame.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

@Entity(tableName = "question")
data class Question(
    @ColumnInfo(name = "question") val question : String,
    @ColumnInfo(name = "answer") val answer : String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Long = 0
)