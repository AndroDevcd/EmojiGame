package com.example.emojiigame.usecases

import com.example.emojiigame.data.MessageRepository

class SetupQuestions(private val messageRepository: MessageRepository) {
    suspend operator fun invoke() =
        messageRepository.setupQuestions()
}