package com.rocklass.realmeet.features.share.domain.usecase

import com.rocklass.realmeet.features.share.domain.ShareRepository
import com.rocklass.realmeet.features.share.domain.model.ShareInfo
import javax.inject.Inject

class FetchShareInfoUseCase @Inject constructor(
    private val shareRepository: ShareRepository,
): suspend () -> Result<ShareInfo> {
    override suspend fun invoke(): Result<ShareInfo> {
        return try {
            Result.success(shareRepository.fetchShareInfo())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}