package com.example.emojis.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.emojis.*
import com.example.emojis.Adapeters.DragManageAdapter
import com.example.emojis.Adapeters.EmojiAdapter
import com.example.emojis.database.EmojiDatabase
import com.example.emojis.databinding.ActivityEmojiListBinding
import com.example.emojis.network.models.Emoji

class EmojiList : AppCompatActivity() {

    private lateinit var adapter: EmojiAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityEmojiListBinding // Add this line


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Update this line to use View Binding
        binding = ActivityEmojiListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val application = requireNotNull(this).application
        val dataSource = EmojiDatabase.getInstance(application).emojiDatabaseDao
        val viewModelFactory = EmojisViewModelFactory(dataSource, application)
        val emojiViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(EmojiViewModel::class.java)
        emojiViewModel.setTheEmojiesFromDatabase()

        adapter = EmojiAdapter()
        recyclerView = findViewById(R.id.emoji_list)
        val managerGrid = GridLayoutManager(this, 5)
        //  manager
        recyclerView.layoutManager = managerGrid

        val callback = DragManageAdapter(
            adapter,
            this,
            ItemTouchHelper.UP.or(ItemTouchHelper.DOWN),
            ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)
        )


        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(recyclerView)

        emojiViewModel.emojisFromDatabase.observe(this, Observer { emojies ->

            recyclerView.adapter = adapter
            val array = arrayListOf<Emoji>()
            array.addAll(emojies)
            adapter.data = array

        })


        binding.itemsswipetorefresh.setOnRefreshListener {

            emojiViewModel.emojisFromDatabase.observe(this, Observer { emojies ->
                recyclerView.adapter = adapter
                val array = arrayListOf<Emoji>()
                array.addAll(emojies)
                adapter.data = array

            })

            binding.itemsswipetorefresh.isRefreshing = false
        }
    }

}
