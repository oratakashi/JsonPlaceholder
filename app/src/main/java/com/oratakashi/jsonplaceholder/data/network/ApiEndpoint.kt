package com.oratakashi.jsonplaceholder.data.network

import com.oratakashi.jsonplaceholder.data.model.Photos
import io.reactivex.Single
import retrofit2.http.GET

interface ApiEndpoint {
    @GET("photos")
    fun getPhotos() : Single<List<Photos>>
}