package com.oratakashi.jsonplaceholder.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oratakashi.jsonplaceholder.repository.Repository
import com.oratakashi.viewbinding.core.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val state: MutableLiveData<MainState> by liveData()

    fun getPhotos() = repository.getPhotos(state)
    fun getPhotos(keyword: String) = repository.getPhotos(keyword, state)
}