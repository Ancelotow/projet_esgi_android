package com.esgi.yfitops.models.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.esgi.yfitops.models.entities.Artist
import org.json.JSONException
import java.util.concurrent.CompletableFuture

class ArtistService: ApiService() {

    fun getArtistId1(id: Int,context:Context) {
        val queue = Volley.newRequestQueue(context)
        val request =
            JsonObjectRequest(Request.Method.GET, getApiUri("artist.php?i=$id"), null,
                { response ->
                    try {
                        val jsonArray = response.getJSONArray("loved")
                        val jsonArtist = jsonArray.getJSONObject(jsonArray.length())
                        Artist(jsonArtist)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                { error ->
                    error.printStackTrace()
                })
        queue.add(request)
    }

    fun getArtistId3(id: Int, context: Context) {
        val queue = Volley.newRequestQueue(context)
        val request =
            JsonObjectRequest(
                Request.Method.GET, getApiUri("artist.php?i=$id"), null,
                { response ->
                    try {
                        val jsonArray = response.getJSONArray("loved")
                        val jsonArtist= jsonArray.getJSONObject(0)
                        Artist(jsonArtist)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                { error ->
                    error.printStackTrace()
                })
        queue.add(request)
    }

    fun getArtistId(id: Int,context: Context): CompletableFuture<MutableList<Artist>> {
        val queue = Volley.newRequestQueue(context)
        val listArtist= mutableListOf<Artist>()
        val completableFuture = CompletableFuture<MutableList<Artist>>()
        val request =
            JsonObjectRequest(
                Request.Method.GET, getApiUri("artist.php?i=$id"), null,
                { response ->
                    try {
                        val jsonArray = response.getJSONArray("loved")
                        for (i in 0 until jsonArray.length()) {
                            val jsonArtist = jsonArray.getJSONObject(i)
                            listArtist.add(Artist(jsonArtist))
                        }
                        completableFuture.complete(listArtist)
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