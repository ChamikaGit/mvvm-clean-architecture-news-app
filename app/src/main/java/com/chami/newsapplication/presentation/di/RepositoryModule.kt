package com.chami.newsapplication.presentation.di

import com.chami.newsapplication.data.repository.NewsRepositoryImpl
import com.chami.newsapplication.data.repository.dataSource.NewsLocalDataSource
import com.chami.newsapplication.data.repository.dataSource.NewsRemoteDataSource
import com.chami.newsapplication.domian.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {


    @Singleton
    @Provides
    fun provideNewsRepository(remoteDataSource: NewsRemoteDataSource,localDataSource: NewsLocalDataSource) : NewsRepository{
        return NewsRepositoryImpl(remoteDataSource,localDataSource)
    }


}