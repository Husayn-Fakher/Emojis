package com.example.emojis.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emojis.EmojiViewModel
import com.example.emojis.EmojisViewModelFactory
import com.example.emojis.R
import com.example.emojis.Adapeters.RepoListAdapter
import com.example.emojis.database.EmojiDatabase

class RepoList : AppCompatActivity() {

    private lateinit var adapter: RepoListAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)

        val application = requireNotNull(this).application
        val dataSource = EmojiDatabase.getInstance(application).emojiDatabaseDao
        val viewModelFactory =
            EmojisViewModelFactory(dataSource, application)
        val emojiViewModel = ViewModelProviders.of(
            this, viewModelFactory).get(EmojiViewModel::class.java)

        adapter = RepoListAdapter(this)
        recyclerView =findViewById(R.id.repos)

        emojiViewModel.getRepos().observe(this, Observer {
            Log.wtf("Emoticons","getting the repos")
            adapter.submitList(it)
        })


        val manager = LinearLayoutManager(this)

        recyclerView.layoutManager = manager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

    }
}
