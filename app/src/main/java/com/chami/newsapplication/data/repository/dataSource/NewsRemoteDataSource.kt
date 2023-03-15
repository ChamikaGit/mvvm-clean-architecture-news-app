package com.chami.newsapplication.data.repository.dataSource

import com.chami.newsapplication.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {

    suspend fun getTopHeadlines(country : String,page : Int) : Response<APIResponse>
    suspend fun getSearchedTopHeadlines(country : String,page : Int,query : String) : Response<APIResponse>

}