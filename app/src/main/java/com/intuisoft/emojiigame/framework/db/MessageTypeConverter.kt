package com.intuisoft.emojiigame.framework.db

import androidx.room.TypeConverter
import com.intuisoft.core.domain.MessageType


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