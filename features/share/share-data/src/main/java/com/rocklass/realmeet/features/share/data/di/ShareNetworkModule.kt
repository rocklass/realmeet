package com.rocklass.realmeet.features.share.data.di

import com.rocklass.realmeet.features.share.data.ShareEndpoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ShareNetworkModule {

    @Provides
    fun provideShareEndpoint(retrofit: Retrofit): ShareEndpoint {
        return retrofit.create(ShareEndpoint::class.java)
    }
}