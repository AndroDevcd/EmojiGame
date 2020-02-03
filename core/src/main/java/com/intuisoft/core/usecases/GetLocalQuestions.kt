package com.intuisoft.core.usecases

import com.intuisoft.core.data.MessageRepository

class GetLocalQuestions(private val messageRepository: MessageRepository) {
    suspend operator fun invoke() =
        messageRepository.getLocalQuestions()
}