package com.esgi.yfitops.models.services

abstract class ApiService {

    companion object {
        const val API_URI: String = "https://theaudiodb.com/api/v1/json/"
        const val API_TOKEN: String = "523532"
    }

    protected fun getApiUri(endpoint: String): String {
        val uri: String = String.format("%s%s/%s", API_URI, API_TOKEN, endpoint);
        return uri;
    }

}

