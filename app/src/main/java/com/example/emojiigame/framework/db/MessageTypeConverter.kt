package com.example.emojiigame.framework.db

import androidx.room.TypeConverter
import com.example.emojiigame.domain.MessageType


class MessageTypeConverter {
    @TypeConverter
    fun fromMessageType(type: Long) : MessageType {
        return MessageType.values()[type.toInt()]
    }

    @TypeConverter
    fun longToMessageType(type: MessageType): Long {
        return type.ordinal.toLong()
    }
}