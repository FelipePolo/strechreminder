package com.fpstudio.stretchreminder.domain.usecase

import com.fpstudio.stretchreminder.data.model.User
import com.fpstudio.stretchreminder.domain.repository.UserRepository

class SaveUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(user: User) {
        repository.saveUser(user)
    }
}
