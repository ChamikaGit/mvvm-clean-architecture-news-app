package com.chami.newsapplication.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chami.newsapplication.domian.usecase.*

class NewsViewModelFactory(
    private val app: Application, private val newsHeadlineUseCase: GetNewsHeadlineUseCase,
    private val searchedNewsUseCase: SearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteNewsUseCase: DeleteNewsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(
            app = app,
            newsHeadlineUseCase = newsHeadlineUseCase,
            newsSearchedNewsUseCase = searchedNewsUseCase,
            saveNewsUseCase = saveNewsUseCase,
            getSavedNewsUseCase = getSavedNewsUseCase,
            deleteNewsUseCase = deleteNewsUseCase
        ) as T
    }
}