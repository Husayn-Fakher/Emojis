package com.example.emojis

import androidx.paging.PageKeyedDataSource
import com.example.emojis.network.APIService
import com.example.emojis.network.models.Repo
import kotlinx.coroutines.*

/**
 * Created By Fakher_Husayn on 30-Aug-20
 **/
class RepoDataSource(private val scope: CoroutineScope) : PageKeyedDataSource<Int, Repo>() {

    private var page = 1


    override fun loadInitial(
        params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Repo>
    ) {

        scope.launch {
            var getRepoDeferred = APIService.EmojiApi.retrofitService.getGoogleRepos(page, 50)
            try {

                var repo = getRepoDeferred.await()

                callback.onResult(repo, null, page + 1)
            } catch (e: Exception) {

            }
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {


        var viewModelJob = Job()
        val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


        coroutineScope.launch {
            var getRepoDeferred = APIService.EmojiApi.retrofitService.getGoogleRepos(params.key, 50)
            try {

                var repo = getRepoDeferred.await()

                callback.onResult(repo, params.key + 1)

            } catch (e: Exception) {

            }
        }


    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}