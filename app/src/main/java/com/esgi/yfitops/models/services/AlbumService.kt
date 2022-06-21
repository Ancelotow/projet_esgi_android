package com.esgi.yfitops.models.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.esgi.yfitops.models.entities.Album
import org.json.JSONException
import java.util.concurrent.CompletableFuture

class AlbumService : ApiService() {

    fun getRanksAlbum(context: Context): CompletableFuture<MutableList<Album>> {
        val queue = Volley.newRequestQueue(context)
        val listAlbums = mutableListOf<Album>()
        val completableFuture = CompletableFuture<MutableList<Album>>()
        val request =
            JsonObjectRequest(
                Request.Method.GET, getApiUri("mostloved.php?format=album"), null,
                { response ->
                    try {
                        val jsonArray = response.getJSONArray("loved")
                        for (i in 0 until jsonArray.length()) {
                            val jsonAlbum = jsonArray.getJSONObject(i)
                            listAlbums.add(Album(jsonAlbum))
                        }
                        completableFuture.complete(listAlbums)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        completableFuture.cancel(true)
                    }
                },
                { error ->
                    error.printStackTrace()
                    completableFuture.cancel(true)
                })
        queue.add(request)
        return completableFuture
    }

    fun getAlbumId(context: Context, id: Int): CompletableFuture<MutableList<Album>> {
        val queue = Volley.newRequestQueue(context)
        val listAlbums = mutableListOf<Album>()
        val completableFuture = CompletableFuture<MutableList<Album>>()
        val request =
            JsonObjectRequest(
                Request.Method.GET, getApiUri("mostloved.php?format=album"), null,
                { response ->
                    try {
                        val jsonArray = response.getJSONArray("loved")
                        for (i in 0 until jsonArray.length()) {
                            val jsonAlbum = jsonArray.getJSONObject(i)
                            listAlbums.add(Album(jsonAlbum))
                        }
                        completableFuture.complete(listAlbums)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        completableFuture.cancel(true)
                    }
                },
                { error ->
                    error.printStackTrace()
                    completableFuture.cancel(true)
                })
        queue.add(request)
        return completableFuture
    }



}