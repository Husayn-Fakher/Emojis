package com.example.emojis

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.emojis.database.EmojiDatabaseDao
import com.example.emojis.network.APIService
import com.example.emojis.network.models.Avatar
import com.example.emojis.network.models.Emoji
import com.example.emojis.network.models.Repo
import kotlinx.coroutines.*

/**
 * Created By Fakher_Husayn on 14-Aug-20
 **/


enum class ApiStatus { LOADING, ERROR, DONE }

class EmojiViewModel(
    val database: EmojiDatabaseDao,
    application: Application
) : AndroidViewModel(application) {


    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status String
    val status: LiveData<ApiStatus>
        get() = _status


    private val _emojies = MutableLiveData<Array<Emoji>>()

    val emojies: LiveData<Array<Emoji>>
        get() = _emojies

    var repoLiveData: LiveData<PagedList<Repo>>


    init {

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(20)
            .build()


        repoLiveData = initializedPagedListBuilder(config).build()
    }


    fun getRepos(): LiveData<PagedList<Repo>> = repoLiveData

    private val _avatar = MutableLiveData<Avatar>()

    val theAvatar: LiveData<Avatar>
        get() = _avatar


    val _emojisFromDatabase = MutableLiveData<List<Emoji>>()

    val emojisFromDatabase: LiveData<List<Emoji>>
        get() = _emojisFromDatabase


    val _avatarFromDatabase = MutableLiveData<List<Avatar>>()

    val avatarFromDatabase: LiveData<List<Avatar>>
        get() = _avatarFromDatabase

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, Repo> {

        val dataSourceFactory = object : DataSource.Factory<Int, Repo>() {
            override fun create(): DataSource<Int, Repo> {
                return RepoDataSource(viewModelScope)
            }
        }
        return LivePagedListBuilder<Int, Repo>(dataSourceFactory, config)
    }

    public fun getEmojies() {
        coroutineScope.launch {
            var getEmojisDeferred = APIService.EmojiApi.retrofitService.getEmojies()
            try {

                _status.value = ApiStatus.LOADING

                var arrayResult = getEmojisDeferred.await()

                if (arrayResult.size > 0) {
                    _emojies.value = arrayResult
                    _status.value = ApiStatus.DONE
                }
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
            }
        }
    }


    public fun getAvatar(userName: String) {

        coroutineScope.launch {
            var getAvatarDeferred = APIService.EmojiApi.retrofitService.getAvatar(userName)
            try {

                _status.value = ApiStatus.LOADING

                var result = getAvatarDeferred.await()


                _avatar.value = result
                _status.value = ApiStatus.DONE

            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR

                Toast.makeText(getApplication()," Can't find avatar with this name",Toast.LENGTH_LONG).show()

            }
        }
    }


    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun setTheEmojiesFromDatabase() {
        uiScope.launch {

            _emojisFromDatabase.value = getEmojiesFromDatabase()

        }
    }


    fun setTheAvatarsFromDatabase() {
        uiScope.launch {
            _avatarFromDatabase.value = getAvatarsFromDatabase()
        }
    }


    private suspend fun getEmojiesFromDatabase(): List<Emoji> {

        return withContext(Dispatchers.IO) {
            var theEmojis = database.get()

            theEmojis
        }
    }

    private suspend fun getAvatarsFromDatabase(): List<Avatar> {

        return withContext(Dispatchers.IO) {
            var theAvatars = database.getAvatars()

            theAvatars
        }
    }

    fun insertEmoji(emoji: Emoji) {

        uiScope.launch {

            insert(emoji)

        }
    }


    private suspend fun insert(emoji: Emoji) {
        withContext(Dispatchers.IO) {
            database.insert(emoji)
        }
    }


    fun insertAvatar(avatar: Avatar) {

        uiScope.launch {

            insertAvatarInDB(avatar)

        }
    }

    private suspend fun insertAvatarInDB(avatar: Avatar) {
        withContext(Dispatchers.IO) {

            try {
                database.insertAvatar(avatar)
            } catch (e: Exception) {
                Log.wtf("Emoticons", "we get the following error " + e.message.toString())

            }
        }
    }


    fun deleteAvatar(avatar: Avatar) {

        uiScope.launch {

            deleteAvatarFromDB(avatar)

        }
    }

    private suspend fun deleteAvatarFromDB(avatar: Avatar) {
        withContext(Dispatchers.IO) {

            try {
                database.deleteAvatar(avatar)
            } catch (e: Exception) {

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


}