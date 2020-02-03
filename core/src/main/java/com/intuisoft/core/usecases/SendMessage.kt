package com.intuisoft.core.usecases

import com.intuisoft.core.data.MessageRepository
import com.intuisoft.core.domain.Message

class SendMessage(private val messageRepository: MessageRepository) {
    suspend operator fun invoke(msg: Message) =
        messageRepository.send(msg)
}