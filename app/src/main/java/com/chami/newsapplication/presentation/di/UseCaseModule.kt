package com.chami.newsapplication.presentation.di

import com.chami.newsapplication.domian.repository.NewsRepository
import com.chami.newsapplication.domian.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetNewsHeadlinesUseCase(newsRepository: NewsRepository) : GetNewsHeadlineUseCase{
        return GetNewsHeadlineUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideSearchUseCase(newsRepository: NewsRepository) : SearchedNewsUseCase {
        return SearchedNewsUseCase(newsRepository)
    }


    @Singleton
    @Provides
    fun provideSaveNewsUseCase(newsRepository: NewsRepository) : SaveNewsUseCase {
        return SaveNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideGetSavedNewsUseCase(newsRepository: NewsRepository) : GetSavedNewsUseCase {
        return GetSavedNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteNewsUseCase(newsRepository: NewsRepository) :  DeleteNewsUseCase{
        return DeleteNewsUseCase(newsRepository)
    }

}