package com.rocklass.realmeet.features.capture.data.di

import com.rocklass.realmeet.features.capture.data.CaptureRepositoryImpl
import com.rocklass.realmeet.features.capture.domain.CaptureRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CaptureDataModule {

    @Binds
    fun bindCaptureRepository(captureRepositoryImpl: CaptureRepositoryImpl): CaptureRepository
}
