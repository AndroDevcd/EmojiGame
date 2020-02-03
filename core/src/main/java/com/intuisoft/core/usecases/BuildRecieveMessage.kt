package com.intuisoft.core.usecases

import com.intuisoft.core.data.MessageRepository

class BuildRecieveMessage(private val messageRepository: MessageRepository) {
    operator fun invoke(msg: String) =
        messageRepository.buildRecievedMessage(msg)
}