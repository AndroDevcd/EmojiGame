package com.example.emojiigame.data

import androidx.paging.PagedList
import androidx.lifecycle.LiveData
import com.example.emojiigame.domain.Message

interface MessageDataSource {

    fun getWelcomeMessage() : Message

    fun buildSentMessage(msg : String) : Message

    fun buildRecievedMessage(msg : String) : Message

    fun getLiveData() : LiveData<PagedList<Message>>?

    suspend fun send(message : Message)
}