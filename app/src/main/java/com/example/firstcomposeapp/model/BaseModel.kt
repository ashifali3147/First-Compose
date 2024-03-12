package com.example.firstcomposeapp.model

class BaseModel<T> {
    var error = true

    var message = ""

    var data: T? = null
}
