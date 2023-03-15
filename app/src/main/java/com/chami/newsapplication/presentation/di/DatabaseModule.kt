package com.chami.newsapplication.presentation.di

import android.app.Application
import androidx.room.Room
import com.chami.newsapplication.data.db.ArticleDAO
import com.chami.newsapplication.data.db.ArticleDatabase
import com.chami.newsapplication.data.util.Constant.Companion.db_name
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providerNewsDatabase(app: Application): ArticleDatabase {
        return Room.databaseBuilder(app, ArticleDatabase::class.java, db_name)
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideArticleDAO(articleDatabase: ArticleDatabase): ArticleDAO {
        return articleDatabase.getArticleDAO()

    }

}