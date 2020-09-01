package com.example.emojis.network.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created By Fakher_Husayn on 14-Aug-20
 **/
@Entity(tableName = "emoji_table")
data class Emoji(
    @PrimaryKey(autoGenerate = true)
    var emojiId: Long = 0L,
    @ColumnInfo(name = "name")
    var name: String = "not assigned",
    @ColumnInfo(name = "url")
    var url: String = "not assigned"
)