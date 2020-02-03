package com.intuisoft.core.usecases

import com.intuisoft.core.data.MessageRepository

class GetWelcomeMessage(private val messageRepository: MessageRepository) {
    operator fun invoke() =
        messageRepository.getWelcomeMessage()
}