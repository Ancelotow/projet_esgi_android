package com.esgi.yfitops.models.services

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConnection {

    fun connection(): Retrofit {
        val token = "523532"
        val uri = "https://theaudiodb.com/api/v1/json/$token/"
        return Retrofit.Builder()
            .baseUrl(uri)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

}

