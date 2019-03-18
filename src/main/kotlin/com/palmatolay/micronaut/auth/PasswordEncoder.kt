package com.palmatolay.micronaut.auth

import io.micronaut.security.authentication.providers.PasswordEncoder
import java.security.MessageDigest
import javax.inject.Singleton

@Singleton
class PasswordEncoder: PasswordEncoder {
    private val md = MessageDigest.getInstance("SHA-256")

    override fun matches(rawPassword: String?, encodedPassword: String?): Boolean {
        return encodedPassword == encode(rawPassword)
    }

    override fun encode(rawPassword: String?): String {
        return String(md.digest((rawPassword?:"").toByteArray()))
    }
}