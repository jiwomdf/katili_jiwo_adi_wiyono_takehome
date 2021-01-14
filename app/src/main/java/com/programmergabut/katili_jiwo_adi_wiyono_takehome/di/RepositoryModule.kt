package com.programmergabut.katili_jiwo_adi_wiyono_takehome.di

/*
   Created by Katili Jiwo A.W. 11 January 2021
*/

import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.Repository
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.RepositoryImpl
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.api.GithubUsersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        githubUsersService: GithubUsersService
    ) = RepositoryImpl(githubUsersService) as Repository

}