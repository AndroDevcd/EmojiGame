package com.intuisoft.emojiigame.framework

import android.app.Application
import android.content.Context
import com.intuisoft.core.data.MessageRepository
import com.intuisoft.core.usecases.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job


class EmojiGameApplication : Application() {

    companion object {
        public lateinit var ctx: Context;
    }

    override fun onCreate() {
        super.onCreate()

        ctx = this;
        val messageRepository = MessageRepository(
            RoomMessageDataSource(this),
            RoomQuestionDataSource(this)
        )

        EmojiiViewModelFactory.inject(
            this,
            UseCases(
                BuildRecieveMessage(messageRepository),
                BuildSendMessage(messageRepository),
                GetAllQuestions(messageRepository),
                GetLiveData(messageRepository),
                GetLocalQuestions(messageRepository),
                GetRandomQuestion(messageRepository),
                GetWelcomeMessage(messageRepository),
                SendMessage(messageRepository),
                SetupQuestions(messageRepository)
            )
        )
    }
}