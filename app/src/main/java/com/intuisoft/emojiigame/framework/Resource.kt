package com.intuisoft.emojiigame.framework

enum class Status {
    LOADING,
    SUCCESS,
    FAILED
}

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?, msg : String? = null): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                msg
            )
        }

        fun <T> error(msg: String): Resource<T> {
            return Resource(
                Status.FAILED,
                null,
                msg
            )
        }

        fun <T> loading(res : Resource<T>?): Resource<T> {

            return Resource(
                Status.LOADING,
                res?.data,
                null
            )
        }
    }
}
