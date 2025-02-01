package com.rocklass.realmeet.features.share.data

import com.rocklass.realmeet.features.share.data.model.ShareInfoResponse
import javax.inject.Inject

class ShareDataSource @Inject constructor(
    private val shareEndpoint: ShareEndpoint,
) {
    suspend fun fetchShareInfo(): ShareInfoResponse {
        return shareEndpoint.fetchShareInfo()
    }
}