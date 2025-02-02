package com.rocklass.realmeet.features.home.data

import com.rocklass.realmeet.core.notification.PushNotificationManager
import com.rocklass.realmeet.features.home.domain.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val pushNotificationManager: PushNotificationManager,
): HomeRepository {
    override suspend fun initNotifications() {
        pushNotificationManager.createNotificationChannel()
    }

    override suspend fun sendNotification() {
        pushNotificationManager.sendNotification(
            title = "RealMeet",
            message = "Some people are nearby. Ready for a RealMeet?",
        )
    }

}