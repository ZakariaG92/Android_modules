package com.mbds.news.api.repository

import com.mbds.news.api.ArticleService
import com.mbds.news.model.Article
import com.mbds.news.model.ArticleResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArticleRpository {


    private val service: ArticleService
    init {


//        // Add API KEY to Authorization
//        val clientBulider = OkHttpClient.Builder()
//            .addInterceptor(AuthenticationInterceptor())
//
//        // Enable Logger
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        clientBulider.addInterceptor(interceptor)


        //Build Client
//        val client = clientBulider.build()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val retrofit = Retrofit.Builder().apply {
            baseUrl("https://newsapi.org/v2/")
//            client(client)
            addConverterFactory(GsonConverterFactory.create())
        }.build()

        service = retrofit.create(ArticleService::class.java)
    }


//    class AuthenticationInterceptor: Interceptor {
//        override fun intercept(chain: Interceptor.Chain): Response {
//            var request = chain.request()
//            val headers = request
//                .headers()
//                .newBuilder()
//                .add("Authorization", "Bearer fbab359f05f040b7aa7e2bcb7925d3ac")
//                .build()
//
//            request = request
//                .newBuilder()
//                .headers(headers)
//                .build()
//            return chain.proceed(request)
//        }
//
//    }

    fun list(): ArticleResponse? {
        val response = service.list().execute()
        return response.body() ?: null
    }
}




