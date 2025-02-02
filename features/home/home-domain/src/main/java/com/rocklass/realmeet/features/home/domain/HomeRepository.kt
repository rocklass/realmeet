package com.rocklass.realmeet.features.home.domain

interface HomeRepository {
    suspend fun initNotifications()
    suspend fun sendNotification()
}