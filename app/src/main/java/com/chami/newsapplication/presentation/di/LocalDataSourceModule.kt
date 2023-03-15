package com.chami.newsapplication.presentation.di

import com.chami.newsapplication.data.db.ArticleDAO
import com.chami.newsapplication.data.repository.dataSource.NewsLocalDataSource
import com.chami.newsapplication.data.repository.dataSourceImpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(articleDAO: ArticleDAO) : NewsLocalDataSource{
        return NewsLocalDataSourceImpl(articleDAO)
    }
}