package com.hashim.weatherapplication.core.network

import com.hashim.weatherapplication.core.constant.Network
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkLayer {

    @Singleton
    @Provides
    fun provideNetworkClient(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Network.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .connectTimeout(Network.TIMEOUT_VALUE, Network.TIMEOUT_UNIT)
        .writeTimeout(Network.TIMEOUT_VALUE, Network.TIMEOUT_UNIT)
        .readTimeout(Network.TIMEOUT_VALUE, Network.TIMEOUT_UNIT)
        .apply {
            try {
                addInterceptor(loggingInterceptor())
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
        .build()
}