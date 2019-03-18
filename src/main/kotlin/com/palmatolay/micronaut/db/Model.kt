package com.palmatolay.micronaut.db

data class UserDAO (
        var name: String? = null,
        var email : String? = null,
        var password : String? = null
)