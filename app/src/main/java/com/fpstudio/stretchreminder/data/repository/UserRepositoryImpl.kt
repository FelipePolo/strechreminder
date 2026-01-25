package com.fpstudio.stretchreminder.data.repository

import com.fpstudio.stretchreminder.data.datasource.UserLocalDataSource
import com.fpstudio.stretchreminder.data.model.User
import com.fpstudio.stretchreminder.domain.repository.UserRepository

import com.fpstudio.stretchreminder.util.notification.NotificationScheduler

class UserRepositoryImpl(
    private val localDataSource: UserLocalDataSource,
    private val notificationScheduler: NotificationScheduler
) : UserRepository {
    override suspend fun saveUser(user: User) {
        localDataSource.saveUser(user)
        notificationScheduler.scheduleNotifications(user)
    }

    override suspend fun getUser(): User? = localDataSource.getUser()
}
