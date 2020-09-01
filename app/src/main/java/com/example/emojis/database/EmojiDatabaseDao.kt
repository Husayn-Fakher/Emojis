package com.example.emojis.database

import androidx.room.*
import com.example.emojis.network.models.Avatar
import com.example.emojis.network.models.Emoji

/**
 * Created By Fakher_Husayn on 07-Aug-20
 **/
@Dao
interface EmojiDatabaseDao {

    @Insert
    fun insert(emoji: Emoji)

    @Insert
    fun insertAvatar(avatar: Avatar)

    @Delete
    fun deleteAvatar(avatar: Avatar)

    @Update
    fun update(emoji: Emoji)


    @Query("SELECT * from emoji_table")
    fun get(): List<Emoji>

    @Query("SELECT * from avatar_table")
    fun getAvatars(): List<Avatar>

    @Query("SELECT * FROM emoji_table ORDER BY emojiId DESC LIMIT 1")
    fun getMostRecentEmoji(): Emoji?
}