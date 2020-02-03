package com.intuisoft.core.usecases

import com.intuisoft.core.data.MessageRepository

class SetupQuestions(private val messageRepository: MessageRepository) {
    suspend operator fun invoke() =
        messageRepository.setupQuestions()
}