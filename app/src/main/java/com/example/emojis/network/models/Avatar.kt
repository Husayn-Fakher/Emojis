package com.example.emojis.network.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created By Fakher_Husayn on 26-Aug-20
 **/
@Entity(tableName = "avatar_table")
data class Avatar(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "name")
    val login: String,
    @ColumnInfo(name = "url")
    val avatar_url: String
)