package com.intuisoft.emojiigame.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(value = [MessageTypeConverter::class])
@Database(entities = arrayOf(Message::class, Question::class), version = 1)
abstract class EmojiiGameDatabase : RoomDatabase() {
    companion object {

        private const val DATABASE_NAME = "message.db"

        private var instance: EmojiiGameDatabase? = null

        private fun create(context: Context): EmojiiGameDatabase =
            Room.databaseBuilder(context, EmojiiGameDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()


        fun getInstance(context: Context): EmojiiGameDatabase =
            (instance ?: create(context)).also { instance = it }
    }

    public fun toDb(message : com.intuisoft.core.domain.Message) =
        Message(message.message, message.type)

    public fun toDb(ques : com.intuisoft.core.domain.Question) =
        Question(ques.question, ques.answer)


    abstract fun messageDao(): MessageDao

    abstract fun questionDao(): QuestionDao
}