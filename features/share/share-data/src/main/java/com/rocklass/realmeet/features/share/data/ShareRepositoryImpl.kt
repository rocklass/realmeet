package com.rocklass.realmeet.features.share.data

import com.rocklass.realmeet.features.share.data.mapper.ShareInfoResponseToDomainMapper
import com.rocklass.realmeet.features.share.domain.ShareRepository
import com.rocklass.realmeet.features.share.domain.model.ShareInfo
import javax.inject.Inject

class ShareRepositoryImpl @Inject constructor(
    private val shareDataSource: ShareDataSource,
    private val shareInfoResponseToDomainMapper: ShareInfoResponseToDomainMapper,
): ShareRepository {
    override suspend fun fetchShareInfo(): ShareInfo {
        return shareInfoResponseToDomainMapper(shareDataSource.fetchShareInfo())
    }
}