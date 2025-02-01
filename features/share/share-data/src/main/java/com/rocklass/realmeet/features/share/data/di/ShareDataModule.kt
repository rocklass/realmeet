package com.rocklass.realmeet.features.share.data.di

import com.rocklass.realmeet.features.share.data.ShareRepositoryImpl
import com.rocklass.realmeet.features.share.domain.ShareRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ShareDataModule {

    @Binds
    fun bindShareRepository(shareRepositoryImpl: ShareRepositoryImpl): ShareRepository
}
