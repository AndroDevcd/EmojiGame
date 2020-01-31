package com.example.emojiigame.framework

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.emojiigame.R
import com.example.emojiigame.data.MessageDataSource
import com.example.emojiigame.domain.Message
import com.example.emojiigame.domain.MessageType
import com.example.emojiigame.domain.Question
import com.example.emojiigame.framework.db.EmojiiGameDatabase

class RoomMessageDataSource(val context: Context) : MessageDataSource {

    private val messageDao = EmojiiGameDatabase.getInstance(context).messageDao()

    override fun getWelcomeMessage(): Message =
        buildRecievedMessage(context.getString(R.string.welcome_message))

    override fun buildSentMessage(msg: String) : Message =
        Message(msg, MessageType.MESAGE_SENT)

    override fun buildRecievedMessage(msg: String) : Message =
        Message(msg, MessageType.MESSAGE_RECIEVED)

    override fun getLiveData()  =
        LivePagedListBuilder(messageDao.getDataSourceFactory().map { m -> Message(m.message, m.type) }, /* page size */ 20).build()

    override suspend fun send(message: Message) {
        messageDao.insert(EmojiiGameDatabase.getInstance(context).toDb(message))
    }

}