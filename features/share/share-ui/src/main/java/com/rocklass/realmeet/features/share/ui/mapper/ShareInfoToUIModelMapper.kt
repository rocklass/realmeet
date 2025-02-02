package com.rocklass.realmeet.features.share.ui.mapper

import com.rocklass.realmeet.features.share.ui.model.ShareUIModel
import com.rocklass.realmeet.features.share.domain.model.ShareInfo
import javax.inject.Inject

class ShareInfoToUIModelMapper @Inject constructor() : (ShareInfo) -> ShareUIModel {
    override fun invoke(shareInfo: ShareInfo): ShareUIModel {
        return ShareUIModel(
            imageUrl = shareInfo.imageUrl,
            shareText = shareInfo.shareText,
        )
    }
}