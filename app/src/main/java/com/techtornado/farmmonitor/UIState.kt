package com.techtornado.farmmonitor

sealed interface UIState<in T> {
    data object Loading: UIState<Any>
    data class Succes<T>(val result: T): UIState<T>
    data class Error(val error: String): UIState<Any>
}