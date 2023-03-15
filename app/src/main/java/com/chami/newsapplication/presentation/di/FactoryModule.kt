package com.chami.newsapplication.presentation.di

import android.app.Application
import com.chami.newsapplication.domian.usecase.*
import com.chami.newsapplication.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
        application: Application,
        getNewsHeadlineUseCase: GetNewsHeadlineUseCase,
        searchedNewsUseCase: SearchedNewsUseCase,
        saveNewsUseCase: SaveNewsUseCase,
        getSavedNewsUseCase: GetSavedNewsUseCase,
        deleteNewsUseCase: DeleteNewsUseCase
    ): NewsViewModelFactory {
        return NewsViewModelFactory(application, getNewsHeadlineUseCase, searchedNewsUseCase,saveNewsUseCase,getSavedNewsUseCase,deleteNewsUseCase)
    }

}