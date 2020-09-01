package com.example.emojis.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emojis.Adapeters.AvatarAdapter
import com.example.emojis.EmojiViewModel
import com.example.emojis.EmojisViewModelFactory
import com.example.emojis.R
import com.example.emojis.database.EmojiDatabase
import com.example.emojis.network.models.Avatar

class Avatars : AppCompatActivity() {

    private lateinit var adapter: AvatarAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avatars)

        val application = requireNotNull(this).application
        val dataSource = EmojiDatabase.getInstance(application).emojiDatabaseDao
        val viewModelFactory = EmojisViewModelFactory(dataSource, application)
        val emojiViewModel = ViewModelProviders.of(this, viewModelFactory).get(EmojiViewModel::class.java)

        emojiViewModel.setTheAvatarsFromDatabase()

        adapter = AvatarAdapter(emojiViewModel)
        recyclerView = findViewById(R.id.avatar_list)

        val managerGrid = GridLayoutManager(this, 5)

        recyclerView.layoutManager = managerGrid

        emojiViewModel.avatarFromDatabase.observe(this, Observer { avatars ->
            recyclerView.adapter = adapter
            val array = arrayListOf<Avatar>()
            array.addAll(avatars)
            adapter.data = array


        })

    }
}
