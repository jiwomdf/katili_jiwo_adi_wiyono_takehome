package com.programmergabut.katili_jiwo_adi_wiyono_takehome.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.BuildConfig
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.api.GithubUsersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAladhanEndPoint() = BuildConfig.BASE_URL_GITHUB

    @Provides
    @Singleton
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofitAladhan(BASE_URL: String, gsonConverterFactory: GsonConverterFactory,
                               okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubUserService(retrofit: Retrofit): GithubUsersService = retrofit.create(
        GithubUsersService::class.java
    ) as GithubUsersService

}