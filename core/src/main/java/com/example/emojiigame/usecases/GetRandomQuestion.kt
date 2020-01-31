package com.example.emojiigame.usecases

import com.example.emojiigame.data.MessageRepository

class GetRandomQuestion(private val messageRepository: MessageRepository) {
    suspend operator fun invoke() =
        messageRepository.getRandomQuestion()
}