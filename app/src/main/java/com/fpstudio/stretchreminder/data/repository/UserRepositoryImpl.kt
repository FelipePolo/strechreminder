package com.fpstudio.stretchreminder.data.repository

import com.fpstudio.stretchreminder.data.datasource.UserLocalDataSource
import com.fpstudio.stretchreminder.data.model.User
import com.fpstudio.stretchreminder.domain.repository.UserRepository

class UserRepositoryImpl(
    private val localDataSource: UserLocalDataSource
) : UserRepository {
    override suspend fun saveUser(user: User) {
        localDataSource.saveUser(user)
    }

    override suspend fun getUser(): User? = localDataSource.getUser()
}
