package com.fpstudio.stretchreminder.domain.repository

import com.fpstudio.stretchreminder.data.model.User

interface UserRepository {
    suspend fun saveUser(user: User)
    suspend fun getUser(): User?
}
