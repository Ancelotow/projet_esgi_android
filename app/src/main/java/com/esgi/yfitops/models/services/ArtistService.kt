package com.esgi.yfitops.models.services

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.esgi.yfitops.models.entities.Artist
import org.json.JSONException

class ArtistService: ApiService() {

    fun getArtistId(id: Int,context:Context) {
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
}