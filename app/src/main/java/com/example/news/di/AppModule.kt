package com.example.news.di

import com.example.news.BuildConfig
import com.example.news.common.Dispatchers
import com.example.news.data.news.remote.api.NewsApi
import com.example.news.data.interceptor.AuthInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object AppModule {

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json(Json.Default) {
            ignoreUnknownKeys = true
        }
    }

    @Singleton
    @Provides
    fun provideNewsApi(json: Json): NewsApi {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor(BuildConfig.NEWS_API_KEY))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        return retrofit.create(NewsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDispatchers(): Dispatchers {
        return Dispatchers.Default
    }
}