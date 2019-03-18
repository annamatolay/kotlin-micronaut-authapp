package com.palmatolay.micronaut.service.imp

import com.palmatolay.micronaut.db.JPAUserRepository
import com.palmatolay.micronaut.db.UserDAO
import com.palmatolay.micronaut.db.MongoUserRepository
import com.palmatolay.micronaut.db.UserEntity
import com.palmatolay.micronaut.service.JPAUserService
import com.palmatolay.micronaut.service.MongoUserService
import java.lang.RuntimeException
import javax.inject.Singleton

//@Singleton
class MongoUserServiceImpl(private val userRepository: MongoUserRepository): MongoUserService{
    override fun save(user: UserDAO) =
            if (user.name.isNullOrEmpty() && user.email.isNullOrEmpty())
                throw RuntimeException("Missing name or password!")
            else userRepository.create(user).blockingGet() as UserDAO

    override fun findByName(name: String) = userRepository.readByName(name).blockingFirst() ?: null

    override fun updateById(id: Long, user: UserDAO): UserDAO? = TODO("not implemented")

    override fun deleteById(id: Long): Boolean = TODO("not implemented")
}

@Singleton
class JPAUserServiceImpl(private val userRepository: JPAUserRepository): JPAUserService {

    override fun save(user: UserDAO) =
            if(user.name != null && user.password != null)
                userRepository.create(UserEntity(user.name!!, user.email ?: "", user.password!!))
            else throw RuntimeException("Missing name or password!")

    override fun findByName(name: String) = userRepository.readByName(name)

    override fun updateByName(name: String, user: UserDAO): UserEntity = userRepository.updateByName(name, user)

    override fun deleteByName(name: String): Boolean = userRepository.deleteByName(name)
}