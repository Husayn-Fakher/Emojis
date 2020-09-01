package com.example.emojis.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.emojis.network.models.Avatar
import com.example.emojis.network.models.Emoji

/**
 * Created By Fakher_Husayn on 07-Aug-20
 **/
@Database(entities = [Emoji::class, Avatar::class], version = 2, exportSchema = false)
abstract class EmojiDatabase : RoomDatabase() {

    abstract val emojiDatabaseDao: EmojiDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: EmojiDatabase? = null

        fun getInstance(context: Context): EmojiDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EmojiDatabase::class.java,
                        "emoji_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }

                return instance
            }

        }
    }

}