package com.chami.newsapplication.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsAPIServiceTest {
    private lateinit var newsAPIService: NewsAPIService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        //keep this empty "" for get url for the mockWebServer.
        mockWebServer = MockWebServer()
        newsAPIService = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPIService::class.java)
    }

    @Test
    fun getTopHeadlines_sendRequest_receivedExpected() {

        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = newsAPIService.getTopHeadlines(country = "us", page = 1).body()
            val request = mockWebServer.takeRequest()

            assertThat(responseBody).isNotNull()
            //In here check the newsAPIService class url path is same as this provided url path
            assertThat(request.path).isEqualTo("/top-headlines?country=us&page=1&apiKey=d7a0f7aebe514e68930dc022bd52d058")

        }
    }

    @Test
    fun getTopHeadLines_receivedResponse_correctPageSize() {

        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = newsAPIService.getTopHeadlines(country = "us", page = 1).body()
            val articlesList = responseBody!!.articles
            assertThat(articlesList.size).isEqualTo(20)

        }
    }

    @Test
    fun getTopHeadLines_receivedResponse_correctContent() {

        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = newsAPIService.getTopHeadlines(country = "us", page = 1).body()
            val articlesList = responseBody!!.articles
            //get the first articles of the list and check it's some values
            val article = articlesList[1]
            assertThat(article.author).isEqualTo("Elizabeth Wolfe, Tavleen Tarrant")
            assertThat(article.url).isEqualTo("https://www.cnn.com/2022/12/18/world/condo-shooting-vaughan-canada/index.html")
            assertThat(article.publishedAt).isEqualTo("2022-12-19T07:15:00Z")
            assertThat(article.title).isEqualTo("5 people killed in a 'horrendous' condo shooting in Canada, police say - CNN")


        }
    }

    private fun enqueueMockResponse(fileName: String) {

        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        mockWebServer.enqueue(mockResponse)
    }


    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}