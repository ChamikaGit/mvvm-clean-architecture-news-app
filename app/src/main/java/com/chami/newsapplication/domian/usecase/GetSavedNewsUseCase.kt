package com.chami.newsapplication.domian.usecase

import com.chami.newsapplication.data.model.Article
import com.chami.newsapplication.domian.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {

    fun execute(): Flow<List<Article>> {
        return newsRepository.getSavedArticles()
    }
}