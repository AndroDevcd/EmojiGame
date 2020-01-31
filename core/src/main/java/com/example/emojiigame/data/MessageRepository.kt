package com.example.emojiigame.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.emojiigame.domain.Message
import com.example.emojiigame.domain.Question


class MessageRepository(
    private val messageDataSource: MessageDataSource,
    private val questionDataSource: QuestionDataSource) {


    suspend fun getAllQuestions() = questionDataSource.getAll()

    suspend fun getRandomQuestion() = questionDataSource.randomQuestion()

    suspend fun setupQuestions() = questionDataSource.setupQuestions()

    suspend fun getLocalQuestions() = questionDataSource.getLocalQuestions()

    fun getWelcomeMessage() = messageDataSource.getWelcomeMessage()

    fun buildSentMessage(msg : String) = messageDataSource.buildSentMessage(msg)

    fun buildRecievedMessage(msg : String) = messageDataSource.buildRecievedMessage(msg)

    fun getLiveData() = messageDataSource.getLiveData()

    suspend fun send(message : Message) = messageDataSource.send(message)
}