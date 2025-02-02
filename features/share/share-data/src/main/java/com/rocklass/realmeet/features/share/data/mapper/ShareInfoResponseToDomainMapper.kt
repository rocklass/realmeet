package com.rocklass.realmeet.features.share.data.mapper

import com.rocklass.realmeet.features.share.data.model.ShareInfoResponse
import com.rocklass.realmeet.features.share.domain.model.ShareInfo
import javax.inject.Inject

class ShareInfoResponseToDomainMapper @Inject constructor() : (ShareInfoResponse) -> ShareInfo {
    override fun invoke(response: ShareInfoResponse): ShareInfo {
        return ShareInfo(
            imageUrl = response.imageUrl,
            shareText = response.shareText,
        )
    }
}