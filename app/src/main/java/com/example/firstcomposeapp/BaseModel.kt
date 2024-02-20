package com.example.firstcomposeapp

class BaseModel<T> {
    var error = true

    var message = ""

    var data: T? = null
}
