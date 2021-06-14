package com.oratakashi.jsonplaceholder.repository.remote

import androidx.lifecycle.MutableLiveData
import com.oratakashi.jsonplaceholder.data.model.Photos
import com.oratakashi.jsonplaceholder.data.network.ApiEndpoint
import com.oratakashi.jsonplaceholder.repository.Repository
import com.oratakashi.jsonplaceholder.ui.MainState
import io.reactivex.Single
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val endpoint: ApiEndpoint
) : Repository {

    override fun getRemote(): Single<List<Photos>> {
        return endpoint.getPhotos()
    }

    override fun getPhotos(state: MutableLiveData<MainState>) {
        throw UnsupportedOperationException()
    }

    override fun getCached(): Single<List<Photos>> {
        throw UnsupportedOperationException()
    }

    override fun addPhotos(data: List<Photos>): Single<List<Long>> {
        throw UnsupportedOperationException()
    }

    override fun getPhotos(keyword: String, state: MutableLiveData<MainState>) {
        throw UnsupportedOperationException()
    }

    override fun searchCached(keyword: String): Single<List<Photos>> {
        throw UnsupportedOperationException()
    }
}