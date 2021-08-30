package com.example.firenews

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


//http://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=a9de927f2b814574940ef7bbdf8f5628

const val  BASE_URL ="https://newsapi.org"
const val API_KEY ="a9de927f2b814574940ef7bbdf8f5628"

interface NewsInterface{
    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country") country:String, @Query("page")page:Int) : Call<News>

   // http://newsapi.org/v2/top-headlines?apiKey=$API_KEY&country=us&page=1
}
object NewsService{
    val newsInstance:NewsInterface
    init {
        val retrofit : Retrofit =Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance= retrofit.create(NewsInterface::class.java)
    }
}