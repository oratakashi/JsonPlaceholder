package com.oratakashi.jsonplaceholder.repository

import androidx.lifecycle.MutableLiveData
import com.oratakashi.jsonplaceholder.data.model.Photos
import com.oratakashi.jsonplaceholder.ui.MainState
import io.reactivex.Single

interface Repository {
    fun getPhotos(state: MutableLiveData<MainState>)
    fun getCached() : Single<List<Photos>>
    fun getRemote() : Single<List<Photos>>
    fun addPhotos(data: List<Photos>): Single<List<Long>>
}