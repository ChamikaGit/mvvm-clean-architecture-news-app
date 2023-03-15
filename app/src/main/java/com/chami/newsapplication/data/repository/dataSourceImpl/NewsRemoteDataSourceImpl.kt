package com.chami.newsapplication.data.repository.dataSourceImpl

import com.chami.newsapplication.data.api.NewsAPIService
import com.chami.newsapplication.data.model.APIResponse
import com.chami.newsapplication.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(private val newsAPIService: NewsAPIService) :
    NewsRemoteDataSource {
    override suspend fun getTopHeadlines(country: String, page: Int): Response<APIResponse> {

        return newsAPIService.getTopHeadlines(
            country = country,
            page = page
        )
    }

    override suspend fun getSearchedTopHeadlines(
        country: String,
        page: Int,
        query: String
    ): Response<APIResponse> {

        return newsAPIService.getSearchedTopHeadlines(country = country, page = page, query = query)
    }
}