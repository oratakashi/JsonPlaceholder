package com.oratakashi.jsonplaceholder.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent
import com.jakewharton.rxbinding3.widget.textChangeEvents
import com.kennyc.view.MultiStateView
import com.kennyc.view.MultiStateView.*
import com.oratakashi.jsonplaceholder.databinding.ActivityMainBinding
import com.oratakashi.viewbinding.core.tools.toast
import com.oratakashi.viewbinding.core.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val adapter : MainAdapter by lazy {
        MainAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            rvMain.adapter = adapter
            srMain.setOnRefreshListener {
                srMain.isRefreshing = false
                viewModel.getPhotos()
            }

            etSearch.textChangeEvents()
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<TextViewTextChangeEvent>(){
                    override fun onNext(t: TextViewTextChangeEvent) {
                        val keyword = t.text.toString()
                        if(keyword.trim{it <= ' '}.isNotEmpty() && keyword.trim{it <= ' '}.length >= 3) {
                            viewModel.getPhotos(keyword)
                        }else{
                            viewModel.getPhotos()
                        }
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }

                })
                .let { return@let disposable::add }

            viewModel.state.observe(this@MainActivity){
                when(it){
                    is MainState.Loading    -> root.viewState = ViewState.LOADING
                    is MainState.Result     -> {
                        root.viewState = ViewState.CONTENT
                        adapter.submitList(it.data)
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

    @Inject
    lateinit var disposable: CompositeDisposable

    private val viewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by viewBinding()
}