package com.example.emojiigame.usecases

import com.example.emojiigame.data.MessageRepository

class BuildRecieveMessage(private val messageRepository: MessageRepository) {
    operator fun invoke(msg: String) =
        messageRepository.buildRecievedMessage(msg)
}