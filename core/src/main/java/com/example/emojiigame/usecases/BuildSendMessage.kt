package com.example.emojiigame.usecases

import com.example.emojiigame.data.MessageRepository

class BuildSendMessage(private val messageRepository: MessageRepository) {
    operator fun invoke(msg: String) =
        messageRepository.buildSentMessage(msg)
}