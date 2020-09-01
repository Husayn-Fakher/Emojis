package com.example.emojis.network

import com.example.emojis.network.models.Avatar
import com.example.emojis.network.models.Emoji
import com.example.emojis.network.models.Repo
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created By Fakher_Husayn on 14-Aug-20
 **/

private const val BASE_URL = "https://api.github.com/"


private val customGson =
    GsonBuilder().registerTypeAdapter(Array<Emoji>::class.java, EmojiConverterFactory()).create()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(GsonConverterFactory.create(customGson))
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl("https://api.github.com/")
    .build()

interface APIService {


    @GET("emojis")
    fun getEmojies():
            Deferred<Array<Emoji>>


    @GET("users/{username}")
    fun getAvatar(@Path("username") userName: String):
            Deferred<Avatar>

    @GET("users/google/repos")
    fun getGoogleRepos(
        @Query("page") page: Int,
        @Query("size") size: Int
    ):
            Deferred<List<Repo>>

    object EmojiApi {
        val retrofitService: APIService by lazy {
            retrofit.create(APIService::class.java)
        }
    }
}