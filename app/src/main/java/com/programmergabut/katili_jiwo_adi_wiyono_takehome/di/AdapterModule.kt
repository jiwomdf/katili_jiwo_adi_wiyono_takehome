package com.programmergabut.katili_jiwo_adi_wiyono_takehome.di

/*
   Created by Katili Jiwo A.W. 11 January 2021
*/

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object AdapterModule {

    @Provides
    fun provideGlideInstance(
        @ActivityContext context: Context,
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_baseline_account__24)
            .error(R.drawable.ic_baseline_account__24)
    )

}