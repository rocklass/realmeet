package com.rocklass.realmeet.features.share.data

import com.rocklass.realmeet.features.share.data.model.ShareInfoResponse
import retrofit2.http.GET

interface ShareEndpoint {
    @GET("/share")
    suspend fun fetchShareInfo(): ShareInfoResponse
}