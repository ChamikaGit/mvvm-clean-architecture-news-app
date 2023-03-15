package com.chami.newsapplication.domian.usecase

import com.chami.newsapplication.data.model.APIResponse
import com.chami.newsapplication.data.util.Resource
import com.chami.newsapplication.domian.repository.NewsRepository

class SearchedNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(country : String,page : Int,searchQuery : String) : Resource<APIResponse>{
        return newsRepository.searchedNews(country = country, page = page, searchQuery = searchQuery)
    }
}