package com.esgi.yfitops.models.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.esgi.yfitops.models.entities.Track
import org.json.JSONException
import java.util.concurrent.CompletableFuture


class TrackService : ApiService() {

    fun getRanksTrack(context: Context): CompletableFuture<MutableList<Track>> {
        val queue = Volley.newRequestQueue(context)
        val listTracks = mutableListOf<Track>()
        val completableFuture = CompletableFuture<MutableList<Track>>()
        val request =
            JsonObjectRequest(Request.Method.GET, getApiUri("mostloved.php?format=track"), null,
                { response ->
                    try {
                        val jsonArray = response.getJSONArray("loved")
                        for (i in 0 until jsonArray.length()) {
                            val jsonTrack = jsonArray.getJSONObject(i)
                            listTracks.add(Track(jsonTrack))
                        }
                        completableFuture.complete(listTracks)
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