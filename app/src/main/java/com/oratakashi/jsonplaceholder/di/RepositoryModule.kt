package com.oratakashi.jsonplaceholder.di

import com.oratakashi.jsonplaceholder.data.db.RoomDB
import com.oratakashi.jsonplaceholder.data.network.ApiEndpoint
import com.oratakashi.jsonplaceholder.repository.DataRepository
import com.oratakashi.jsonplaceholder.repository.local.LocalRepository
import com.oratakashi.jsonplaceholder.repository.remote.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideLocalRepository(
        db: RoomDB
    ): LocalRepository = LocalRepository(db)

    @Provides
    @Singleton
    fun provideRemoteRepository(
        endpoint: ApiEndpoint
    ): RemoteRepository = RemoteRepository(endpoint)

    @Provides
    @Singleton
    fun provideDataRepository(
        localRepository: LocalRepository,
        remoteRepository: RemoteRepository,
        disposable: CompositeDisposable
    ): DataRepository = DataRepository(localRepository, remoteRepository, disposable)
}