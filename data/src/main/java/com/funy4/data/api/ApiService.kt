package com.funy4.data.api

import com.funy4.data.model.PredictApiModel
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import javax.inject.Inject
import javax.inject.Singleton

interface ApiService {
    @POST("/api/predict")
    suspend fun predict(@Body predictApiModel: PredictApiModel): List<PredictApiModel.Transaction>

    @GET("api/test")
    suspend fun test(): String

    @Singleton
    class Factory @Inject constructor(
        private val moshi: Moshi
    ) {
        private var mInstance: ApiService? = null

        fun get(): ApiService {
            var instance = mInstance
            if (instance != null) return instance
            val okHttpClient = OkHttpClient.Builder()
                .also {/* We can add interceptors */ }
                .build()
            mInstance = Retrofit.Builder()
                .baseUrl("http://172.29.15.66:8083")
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(ApiService::class.java)
            return mInstance!!
        }
    }
}