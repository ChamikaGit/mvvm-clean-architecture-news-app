package com.chami.newsapplication.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.chami.newsapplication.data.model.APIResponse
import com.chami.newsapplication.data.model.Article
import com.chami.newsapplication.data.util.InternetConnectivity
import com.chami.newsapplication.data.util.Resource
import com.chami.newsapplication.domian.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel(
    private val app: Application,
    private val newsHeadlineUseCase: GetNewsHeadlineUseCase,
    private val newsSearchedNewsUseCase: SearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase : GetSavedNewsUseCase,
    private val deleteNewsUseCase: DeleteNewsUseCase
) :
    AndroidViewModel(app) {

    private val _newsHeadlineLiveData: MutableLiveData<Resource<APIResponse>> = MutableLiveData()
    var newsHeadlineLiveData: LiveData<Resource<APIResponse>> = _newsHeadlineLiveData

    private val _searchedNewsHeadlineLiveData: MutableLiveData<Resource<APIResponse>> =
        MutableLiveData()
    var searchedNewsUseCase: LiveData<Resource<APIResponse>> = _searchedNewsHeadlineLiveData

    fun getNewsHeadLines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        _newsHeadlineLiveData.postValue(Resource.Loading())
        try {
            if (InternetConnectivity.isInternetAvailable(app)) {
                val apiResult = newsHeadlineUseCase.execute(country = country, page = page)
                _newsHeadlineLiveData.postValue(apiResult)
            } else {
                _newsHeadlineLiveData.postValue(Resource.Error(message = "Internet not available"))
            }
        } catch (e: Exception) {
            _newsHeadlineLiveData.postValue(Resource.Error(e.message.toString()))
        }


    }

    fun getSearchedNewsHeadlines(country: String, page: Int, query: String) =
        viewModelScope.launch(Dispatchers.IO) {
            _searchedNewsHeadlineLiveData.postValue(Resource.Loading())
            try {
                if (InternetConnectivity.isInternetAvailable(context = app)) {
                    val apiResult = newsSearchedNewsUseCase.execute(
                        country = country,
                        page = page,
                        searchQuery = query
                    )
                    _searchedNewsHeadlineLiveData.postValue(apiResult)
                } else {
                    _searchedNewsHeadlineLiveData.postValue(Resource.Error(message = "Internet not available"))
                }
            } catch (e: Exception) {
                _searchedNewsHeadlineLiveData.postValue(Resource.Error(message = e.message.toString()))
            }

        }

    fun saveArticles(article: Article) = viewModelScope.launch {
        saveNewsUseCase.execute(article = article)
    }

    fun getAllSavedArticles() = liveData {
        getSavedNewsUseCase.execute().collect{
            emit(it)
        }
    }

    fun deleteArticles(article: Article) = viewModelScope.launch {
        deleteNewsUseCase.execute(article)
    }




}