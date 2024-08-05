package com.harshit.lokalassignment.utils.network

sealed class NetworkState {
    object Connected : NetworkState()
    object Disconnected : NetworkState()
}