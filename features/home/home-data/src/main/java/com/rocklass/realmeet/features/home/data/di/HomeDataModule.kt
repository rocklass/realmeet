package com.rocklass.realmeet.features.home.data.di

import com.rocklass.realmeet.features.home.data.HomeRepositoryImpl
import com.rocklass.realmeet.features.home.domain.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface HomeDataModule {

    @Binds
    fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository
}
