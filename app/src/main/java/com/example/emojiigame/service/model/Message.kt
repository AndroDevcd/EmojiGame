package com.example.emojiigame.service.model

enum class MessageType(val value : Int) {
    MESAGE_SENT(0),
    MESSAGE_RECIEVED(1)
}

data class Message(val message : String, val type : MessageType) {

}