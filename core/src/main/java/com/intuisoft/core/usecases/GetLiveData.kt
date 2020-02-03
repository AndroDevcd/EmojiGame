package com.intuisoft.core.usecases

import com.intuisoft.core.data.MessageRepository

class GetLiveData(private val messageRepository: MessageRepository) {
    operator fun invoke() =
        messageRepository.getLiveData()
}