package com.oratakashi.jsonplaceholder.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.oratakashi.jsonplaceholder.BuildConfig
import com.oratakashi.jsonplaceholder.data.network.ApiEndpoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                false -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun providesChucker(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor(context)
    }

    @Provides
    @Singleton
    fun providesHttpClient(
        interceptor: HttpLoggingInterceptor,
        chucker: ChuckerInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            retryOnConnectionFailure(true)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            addInterceptor(interceptor)
            if (BuildConfig.DEBUG) addInterceptor(chucker)
        }.build()
    }

    @Provides
    @Singleton
    fun providesHttpAdapter(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().apply {
            client(client)
            baseUrl(BuildConfig.BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        }.build()
    }

    @Provides
    @Singleton
    fun providesApiEndPoint(retrofit: Retrofit): ApiEndpoint {
        return retrofit.create(ApiEndpoint::class.java)
    }

    @Provides
    @Singleton
    fun provideDisposable(): CompositeDisposable = CompositeDisposable()
}