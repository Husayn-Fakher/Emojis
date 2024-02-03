package com.example.emojis

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.emojis.database.EmojiDatabaseDao

/**
 * Created By Fakher_Husayn on 07-Aug-20
 **/
class EmojisViewModelFactory
    (
    private val dataSource: EmojiDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmojiViewModel::class.java)) {
            return EmojiViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}