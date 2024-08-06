package com.harshit.lokalassignment.utils

sealed class SnackarEvent(val message: String) {
    class Success(message: String) : SnackarEvent(message)
    class Error(message: String) : SnackarEvent(message)
    class Loading(message: String) : SnackarEvent(message)
}

class Event<out T>(private val content: T) {
    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}
