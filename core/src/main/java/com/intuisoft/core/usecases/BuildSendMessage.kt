package com.intuisoft.core.usecases

import com.intuisoft.core.data.MessageRepository

class BuildSendMessage(private val messageRepository: MessageRepository) {
    operator fun invoke(msg: String) =
        messageRepository.buildSentMessage(msg)
}