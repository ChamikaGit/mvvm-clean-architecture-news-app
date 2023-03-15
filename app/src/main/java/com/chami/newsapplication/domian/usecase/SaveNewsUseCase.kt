package com.chami.newsapplication.domian.usecase

import com.chami.newsapplication.data.model.APIResponse
import com.chami.newsapplication.data.model.Article
import com.chami.newsapplication.data.util.Resource
import com.chami.newsapplication.domian.repository.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article) {
        return newsRepository.saveNews(article = article)
    }
}