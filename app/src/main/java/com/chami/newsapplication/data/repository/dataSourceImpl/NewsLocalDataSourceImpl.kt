package com.chami.newsapplication.data.repository.dataSourceImpl

import com.chami.newsapplication.data.db.ArticleDAO
import com.chami.newsapplication.data.model.Article
import com.chami.newsapplication.data.repository.dataSource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(private val articleDAO: ArticleDAO) : NewsLocalDataSource {
    override suspend fun saveArticleToDB(article: Article) {
        return articleDAO.insert(article = article)
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        return articleDAO.getAllSavedNews()
    }

    override suspend fun deleteArticleFromDatabase(article: Article) {
        return articleDAO.deleteArticle(article)
    }
}