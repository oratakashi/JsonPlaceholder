package com.oratakashi.jsonplaceholder.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kennyc.view.MultiStateView
import com.kennyc.view.MultiStateView.*
import com.oratakashi.jsonplaceholder.databinding.ActivityMainBinding
import com.oratakashi.viewbinding.core.tools.toast
import com.oratakashi.viewbinding.core.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            viewModel.state.observe(this@MainActivity){
                when(it){
                    is MainState.Loading    -> root.viewState = ViewState.LOADING
                    is MainState.Result     -> {
                        root.viewState = ViewState.CONTENT
                        Log.e("debug", "debug: ${it.data}")
                    }
                    is MainState.Error      -> {
                        root.viewState = ViewState.ERROR
                        it.error.printStackTrace()
                        toast("${it.error.message}")
                    }
                }
            }
            viewModel.getPhotos()
        }
    }

    private val viewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by viewBinding()
}