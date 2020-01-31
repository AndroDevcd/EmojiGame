package com.example.emojiigame.usecases

import com.example.emojiigame.data.MessageRepository

class GetWelcomeMessage(private val messageRepository: MessageRepository) {
    operator fun invoke() =
        messageRepository.getWelcomeMessage()
}