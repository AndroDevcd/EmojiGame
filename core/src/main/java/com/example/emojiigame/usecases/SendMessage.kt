package com.example.emojiigame.usecases

import com.example.emojiigame.data.MessageRepository
import com.example.emojiigame.domain.Message

class SendMessage(private val messageRepository: MessageRepository) {
    suspend operator fun invoke(msg: Message) =
        messageRepository.send(msg)
}