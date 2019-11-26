package com.homedev.weather.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Alexandr Zheleznyakov on 2019-11-08.
 */
class ApiWeather {
    companion object {
        val instance = ApiWeather()
    }

    val api: IApiWeather

    init {
        api = Retrofit.Builder()
            .baseUrl(IApiWeather.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createHttpClient())
            .build()
            .create(IApiWeather::class.java)
    }

    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Content-Type", "application/json")

                requestBuilder.header(IApiWeather.WEATHER_HEADER_ACCESS, IApiWeather.OPEN_WEATHER_API_KEY)

                val request = requestBuilder
                    .method(original.method(), original.body())
                    .build()

                chain.proceed(request)
            }
            .connectTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .build()
    }
}