package com.fpstudio.stretchreminder.domain.usecase

import com.fpstudio.stretchreminder.data.model.User
import com.fpstudio.stretchreminder.domain.repository.UserRepository

class GetUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(): User? = repository.getUser()
}
