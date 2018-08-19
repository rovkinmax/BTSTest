package ru.rovkinmax.bts.feature.global.domain

open class CommonException(val code: String, message: String) : RuntimeException(message) {
    companion object {
        const val CODE_UNKNOWN = "UNKNOWN"
        const val CODE_NETWORK = "network"
    }
}