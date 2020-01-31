package com.example.emojiigame.usecases

import com.example.emojiigame.data.MessageRepository

class GetLiveData(private val messageRepository: MessageRepository) {
    operator fun invoke() =
        messageRepository.getLiveData()
}