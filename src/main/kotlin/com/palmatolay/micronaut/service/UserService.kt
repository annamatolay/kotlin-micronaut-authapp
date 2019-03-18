package com.palmatolay.micronaut.service

import com.palmatolay.micronaut.db.UserDAO
import com.palmatolay.micronaut.db.UserEntity

/**
 * Responsible for communication between the web and repository layers according to the type of DB.
 */

interface MongoUserService {
    fun save(user: UserDAO): UserDAO
    fun findByName(name: String): UserDAO?
    fun updateById(id: Long, user: UserDAO): UserDAO?
    fun deleteById(id: Long): Boolean
}

interface JPAUserService {
    fun save(user: UserDAO): UserEntity
    fun findByName(name: String): UserEntity?
    fun updateByName(name: String, user: UserDAO): UserEntity
    fun deleteByName(name: String): Boolean
}