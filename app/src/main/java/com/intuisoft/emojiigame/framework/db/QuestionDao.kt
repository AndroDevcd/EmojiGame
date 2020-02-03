package com.intuisoft.emojiigame.framework.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(questions: List<Question>)

    @Query("SELECT * from question")
    suspend fun getAllQuestions(): List<Question>?
}