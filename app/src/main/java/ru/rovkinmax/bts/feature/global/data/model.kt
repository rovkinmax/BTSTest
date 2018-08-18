package ru.rovkinmax.bts.feature.global.data

abstract class BaseResponse {
    companion object {
        const val STATUS_OK = "ok"
    }

    var stat: String = ""
    var message: String? = null
    val code: String? = null
}