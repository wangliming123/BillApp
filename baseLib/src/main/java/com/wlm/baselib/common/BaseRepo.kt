package com.wlm.baselib.common

open class BaseRepo {
    suspend fun <T : Any> apiCall(call: suspend () -> T): T {
        return call.invoke()
    }
}