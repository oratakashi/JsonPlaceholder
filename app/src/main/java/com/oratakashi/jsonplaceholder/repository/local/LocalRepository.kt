package com.oratakashi.jsonplaceholder.repository.local

import androidx.lifecycle.MutableLiveData
import com.oratakashi.jsonplaceholder.data.db.RoomDB
import com.oratakashi.jsonplaceholder.data.model.Photos
import com.oratakashi.jsonplaceholder.repository.Repository
import com.oratakashi.jsonplaceholder.ui.MainState
import io.reactivex.Single
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val db: RoomDB
) : Repository {

    override fun getCached(): Single<List<Photos>> {
        return db.photos().getAll()
    }

    override fun addPhotos(data: List<Photos>): Single<List<Long>> {
        return db.photos().addAll(data)
    }

    override fun searchCached(keyword: String): Single<List<Photos>> {
        return db.photos().search(keyword)
    }

    override fun getPhotos(keyword: String, state: MutableLiveData<MainState>) {
        throw UnsupportedOperationException()
    }

    override fun getPhotos(state: MutableLiveData<MainState>) {
        throw UnsupportedOperationException()
    }

    override fun getRemote(): Single<List<Photos>> {
        throw UnsupportedOperationException()
    }
}