package com.chami.newsapplication.data.repository

import com.chami.newsapplication.data.model.APIResponse
import com.chami.newsapplication.data.model.Article
import com.chami.newsapplication.data.repository.dataSource.NewsLocalDataSource
import com.chami.newsapplication.data.repository.dataSource.NewsRemoteDataSource
import com.chami.newsapplication.data.util.Resource
import com.chami.newsapplication.domian.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val remoteDataSource: NewsRemoteDataSource,
    private val localDataSource: NewsLocalDataSource
) :
    NewsRepository {

    override suspend fun getNewsHeadlines(country: String, page: Int): Resource<APIResponse> {

        return responseToResource(remoteDataSource.getTopHeadlines(country = country, page = page))
    }

    override suspend fun searchedNews(
        country: String,
        page: Int,
        searchQuery: String
    ): Resource<APIResponse> {
        return responseToResource(
            remoteDataSource.getSearchedTopHeadlines(
                country = country,
                page = page,
                query = searchQuery
            )
        )
    }

    override suspend fun saveNews(article: Article) {
        localDataSource.saveArticleToDB(article = article)
    }

    override suspend fun deleteNews(article: Article) {
        localDataSource.deleteArticleFromDatabase(article)
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        return localDataSource.getSavedArticles()
    }

    private fun responseToResource(response: Response<APIResponse>): Resource<APIResponse> {

        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}