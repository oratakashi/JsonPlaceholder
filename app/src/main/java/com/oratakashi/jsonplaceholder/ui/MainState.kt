package com.oratakashi.jsonplaceholder.ui

import com.oratakashi.jsonplaceholder.data.model.Photos

sealed class MainState {
    object Loading : MainState()
    data class Result(val data: List<Photos>) : MainState()
    data class Error(val error: Throwable) : MainState()
}
