package com.example.jack.infintescrolltest

class Resource<T> constructor(val status: Status, val data: T? = null) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING)
        }

        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }

        fun <T> failure(): Resource<T> {
            return Resource(Status.ERROR)
        }
    }
}