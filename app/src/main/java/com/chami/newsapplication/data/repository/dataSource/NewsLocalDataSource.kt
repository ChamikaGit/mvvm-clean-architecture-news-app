package com.chami.newsapplication.data.repository.dataSource

import com.chami.newsapplication.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    suspend fun saveArticleToDB(article: Article)
    fun getSavedArticles() : Flow<List<Article>>
    suspend fun deleteArticleFromDatabase(article: Article)
}