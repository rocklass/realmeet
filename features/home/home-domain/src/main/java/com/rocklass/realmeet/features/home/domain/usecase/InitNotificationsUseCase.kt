package com.rocklass.realmeet.features.home.domain.usecase

import com.rocklass.realmeet.features.home.domain.HomeRepository
import javax.inject.Inject

class InitNotificationsUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
): suspend () -> Result<Unit> {
    override suspend fun invoke(): Result<Unit> {
        return try {
            Result.success(homeRepository.initNotifications())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}