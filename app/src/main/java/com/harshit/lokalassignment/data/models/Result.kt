package com.harshit.lokalassignment.data.models

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String?) : Result<Nothing>()
}