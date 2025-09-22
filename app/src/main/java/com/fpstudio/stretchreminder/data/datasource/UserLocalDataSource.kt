package com.fpstudio.stretchreminder.data.datasource

import com.fpstudio.stretchreminder.data.local.UserDao
import com.fpstudio.stretchreminder.data.model.User

class UserLocalDataSource(private val userDao: UserDao) {
    suspend fun saveUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getLastUser(): User? = userDao.getLastUser()
}
