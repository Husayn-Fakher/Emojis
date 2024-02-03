package com.example.emojis.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.emojis.EmojiViewModel
import com.example.emojis.EmojisViewModelFactory
import com.example.emojis.R
import com.example.emojis.database.EmojiDatabase
import com.example.emojis.network.models.Emoji
import com.example.emojis.databinding.ActivityMainBinding // Update import here

class MainActivity : AppCompatActivity() {

    lateinit var avatarName: EditText
    var imgUrl: String? = null
    lateinit var LoadedEmojies: Array<Emoji>
    private lateinit var binding: ActivityMainBinding // Add this line


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.VISIBLE

        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE) ?: return
        val networkCalled = sharedPref.getBoolean("firstTime", false)
        val application = requireNotNull(this).application
        val dataSource = EmojiDatabase.getInstance(application).emojiDatabaseDao
        val viewModelFactory =
            EmojisViewModelFactory(dataSource, application)
        val emojiViewModel = ViewModelProviders.of(this, viewModelFactory).get(EmojiViewModel::class.java)

        setUpTheUI(emojiViewModel)

        if (!networkCalled) {
            callNetwork(emojiViewModel)
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


    private fun setUpTheUI(emojiViewModel: EmojiViewModel) {

        avatarName = findViewById(R.id.avatarName) as EditText
// get reference to buttons
        val btn_click_me = findViewById(R.id.button) as Button
        val emojiList = findViewById(R.id.button2) as Button
        val reposList = findViewById(R.id.repos) as Button
        val avatarList = findViewById(R.id.avatars) as Button
        val searchIcon = findViewById(R.id.search) as ImageView
// set on-click listener
        btn_click_me.setOnClickListener {

            getRandomEmoji()

        }

        emojiList.setOnClickListener() {

            showEmojiList()

        }

        reposList.setOnClickListener() {

            showRepoList()

        }

        avatarList.setOnClickListener() {

            showAvatarList()

        }

        searchIcon.setOnClickListener {

            setAvatarImage(avatarName.getText().toString(), emojiViewModel)

        }
    }

    private fun setAvatarImage(userName: String, emojiViewModel: EmojiViewModel) {

        emojiViewModel.getAvatar(userName)

        emojiViewModel.theAvatar.observe(this, Observer { avatar ->
            // update UI

            Log.e("Emoticons","Avatar url is "+avatar.avatar_url)

            imgUrl = avatar.avatar_url

            imgUrl?.let {
                val imgUri = imgUrl!!.toUri().buildUpon().scheme("https").build()
                Glide.with(binding.imageView.context)
                    .load(imgUri).apply(RequestOptions())
                    .into(binding.imageView)
            }

            emojiViewModel.insertAvatar(avatar)

        })

    }

    private fun callNetwork(emojiViewModel: EmojiViewModel) {

        emojiViewModel.getEmojies()

        emojiViewModel.emojies.observe(this, Observer { emojies ->

            LoadedEmojies = emojies

            val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)
            if (sharedPref != null) {
                with(sharedPref.edit()) {
                    putBoolean("firstTime", true)
                    commit()
                }
            }


            val index = (0..emojies.size - 1).random()

            imgUrl = emojies[index].url


            for (e in emojies) {

                emojiViewModel.insertEmoji(e)
            }

            getRandomEmoji()
        })
    }

    fun getRandomEmoji() {

        binding.progressBar.visibility = View.VISIBLE

        val application = requireNotNull(this).application

        val dataSource = EmojiDatabase.getInstance(application).emojiDatabaseDao

        val viewModelFactory =
            EmojisViewModelFactory(dataSource, application)

        val emojiViewModel = ViewModelProviders.of(this, viewModelFactory).get(EmojiViewModel::class.java)

        emojiViewModel.setTheEmojiesFromDatabase()

        emojiViewModel.emojisFromDatabase.observe(this, Observer { emojies ->

            val index = (0..emojies.size - 1).random()

            imgUrl = emojies[index].url

            imgUrl?.let {
                val imgUri = imgUrl!!.toUri().buildUpon().scheme("https").build()
                Glide.with(binding.imageView.context)
                    .load(imgUri).apply(RequestOptions())
                    .into(binding.imageView)
            }

            binding.progressBar.visibility = View.GONE

        })

    }

    fun showEmojiList() {
        val intent = Intent(this, EmojiList::class.java)
        startActivity(intent)
    }

    fun showRepoList() {
        val intent = Intent(this, RepoList::class.java)
        startActivity(intent)
    }

    fun showAvatarList() {
        val intent = Intent(this, Avatars::class.java)
        startActivity(intent)
    }
}
