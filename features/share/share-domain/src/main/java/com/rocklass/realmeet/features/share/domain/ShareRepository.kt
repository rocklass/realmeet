package com.rocklass.realmeet.features.share.domain

import com.rocklass.realmeet.features.share.domain.model.ShareInfo

interface ShareRepository {
    suspend fun fetchShareInfo(): ShareInfo
}