package com.oratakashi.jsonplaceholder.repository

import androidx.lifecycle.MutableLiveData
import com.oratakashi.jsonplaceholder.data.model.Photos
import com.oratakashi.jsonplaceholder.repository.local.LocalRepository
import com.oratakashi.jsonplaceholder.repository.remote.RemoteRepository
import com.oratakashi.jsonplaceholder.ui.MainState
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository,
    private val disposable: CompositeDisposable
) : Repository {
    override fun getPhotos(state: MutableLiveData<MainState>) {
        localRepository.getCached()
            .map<MainState>(MainState::Result)
            .onErrorReturn(MainState::Error)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toFlowable()
            .startWith(MainState.Loading)
            .subscribe {
                when(it){
                    is MainState.Result -> {
                        if(it.data.isNotEmpty()){
                            state.postValue(it)
                        }else{
                            remoteRepository.getRemote()
                                .flatMap { response ->
                                    localRepository.addPhotos(response)
                                }
                                .toFlowable()
                                .subscribe { getPhotos(state) }
                                .let { return@let disposable::add }
                        }
                    }
                    else -> state.postValue(it)
                }
            }
            .let { return@let disposable::add }
    }

    override fun getCached(): Single<List<Photos>> {
        throw UnsupportedOperationException()
    }

    override fun getRemote(): Single<List<Photos>> {
        throw UnsupportedOperationException()
    }

    override fun addPhotos(data: List<Photos>): Single<List<Long>> {
        throw UnsupportedOperationException()
    }
}