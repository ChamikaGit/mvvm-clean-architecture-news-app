package com.chami.newsapplication.presentation.di

import com.chami.newsapplication.data.api.NewsAPIService
import com.chami.newsapplication.data.repository.dataSource.NewsRemoteDataSource
import com.chami.newsapplication.data.repository.dataSourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {


    @Singleton
    @Provides
    fun provideRemoteDataSource(newsAPIService: NewsAPIService) : NewsRemoteDataSource{
        return NewsRemoteDataSourceImpl(newsAPIService)
    }

}