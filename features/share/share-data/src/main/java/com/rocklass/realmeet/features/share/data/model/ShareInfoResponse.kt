package com.rocklass.realmeet.features.share.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ShareInfoResponse(
    val imageUrl: String,
    val shareText: String,
)