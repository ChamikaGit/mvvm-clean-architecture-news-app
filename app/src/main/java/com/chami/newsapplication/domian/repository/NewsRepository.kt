package com.chami.newsapplication.domian.repository

import com.chami.newsapplication.data.model.APIResponse
import com.chami.newsapplication.data.model.Article
import com.chami.newsapplication.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNewsHeadlines(country : String,page : Int) : Resource<APIResponse>
    suspend fun searchedNews(country : String,page : Int,searchQuery : String) : Resource<APIResponse>
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)

    fun getSavedArticles() : Flow<List<Article>>

}