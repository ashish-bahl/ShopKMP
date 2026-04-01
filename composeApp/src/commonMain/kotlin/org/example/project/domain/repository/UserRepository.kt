package org.example.project.domain.repository

import org.example.project.domain.model.NetworkResult
import org.example.project.domain.model.User

interface UserRepository {
    suspend fun getUserDetails(): NetworkResult<User>
}
