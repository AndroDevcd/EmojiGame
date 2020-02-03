package com.intuisoft.emojiigame.framework.db

import androidx.paging.DataSource
import androidx.room.*

@TypeConverters(value = [MessageTypeConverter::class])
@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: Message)

    @Query("""
        SELECT * from message
         ORDER BY id ASC
    """)
    fun getDataSourceFactory() : DataSource.Factory<Int, Message>
}