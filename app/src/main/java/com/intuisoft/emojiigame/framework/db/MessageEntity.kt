package com.intuisoft.emojiigame.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.intuisoft.core.domain.MessageType


@Entity(tableName = "message")
data class Message(
    @ColumnInfo(name = "message") var message: String,
    @ColumnInfo(name = "type") var type: MessageType,
    @PrimaryKey(autoGenerate = true)  @ColumnInfo(name = "id") var id: Int = 0
)
