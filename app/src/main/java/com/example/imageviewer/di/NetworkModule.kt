package com.example.imageviewer.di

import com.example.imageviewer.BuildConfig
import com.example.imageviewer.data.network.ImageViewerService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.jvm.java

interface NetworkModule {

  fun provideFormNestService(): ImageViewerService

  class NetworkModuleImpl : NetworkModule {

    override fun provideFormNestService(): ImageViewerService =
      provideRetrofitClient(okHttpClient).create(ImageViewerService::class.java)

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(
      HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
      }).callTimeout(15, TimeUnit.SECONDS)
      .writeTimeout(15, TimeUnit.SECONDS)
      .readTimeout(15, TimeUnit.SECONDS)
      .connectTimeout(15, TimeUnit.SECONDS)
      .retryOnConnectionFailure(true).build()

    private fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
      val moshiConverterFactory = MoshiConverterFactory.create(
        Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
      )
      return Retrofit.Builder().baseUrl(BuildConfig.IMAGE_VIEWER_BASE_URL)
        .addConverterFactory(moshiConverterFactory)
        .client(okHttpClient)
        .build()
    }
  }
}
