package com.chami.newsapplication.domian.usecase

import com.chami.newsapplication.data.model.APIResponse
import com.chami.newsapplication.data.util.Resource
import com.chami.newsapplication.domian.repository.NewsRepository

class GetNewsHeadlineUseCase(private val newsRepository: NewsRepository) {


    suspend fun execute(country: String, page: Int): Resource<APIResponse> {
        return newsRepository.getNewsHeadlines(country = country, page = page)
    }


}