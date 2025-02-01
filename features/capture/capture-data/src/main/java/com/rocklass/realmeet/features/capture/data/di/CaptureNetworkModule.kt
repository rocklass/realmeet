package com.rocklass.realmeet.features.capture.data.di

import com.rocklass.realmeet.features.capture.data.CaptureEndpoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class CaptureNetworkModule {

    @Provides
    fun provideCaptureEndpoint(retrofit: Retrofit): CaptureEndpoint {
        return retrofit.create(CaptureEndpoint::class.java)
    }
}