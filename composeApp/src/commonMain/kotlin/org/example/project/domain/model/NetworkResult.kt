package org.example.project.domain.model

sealed class NetworkResult<out T> {
    data class Success<T>(val status: Int, val message: String, val data: T? = null) :
        NetworkResult<T>()

    data class NetworkError(val statusCode: Int, val message: String) : NetworkResult<Nothing>()
    data class Exception(val message: String) : NetworkResult<Nothing>()
}
