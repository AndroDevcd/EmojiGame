package com.example.emojiigame.service.model

enum class MessageType {
    MESAGE_SENT,
    MESSAGE_RECIEVED
}

data class Message(val message : String, val type : MessageType) {

}