package com.palmatolay.micronaut.db

import com.mongodb.client.model.Filters
import com.mongodb.reactivestreams.client.MongoClient
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession
import io.micronaut.spring.tx.annotation.Transactional
import io.reactivex.Flowable
import io.reactivex.Single
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.validation.constraints.NotNull

/**
 * Responsible for communication between the database and service layers according to the type of DB.
 */

//NoSQL, async, reactive
//@Singleton
class MongoUserRepository{

    //@Inject @Named("mongodb")
    lateinit var client: MongoClient

    fun create(user: UserDAO) = Single.fromPublisher(getCollection()
            .insertOne(user)).map<Any> { success -> user }

    fun readByName(name: String) = Flowable.fromPublisher(getCollection()
            .find(Filters.eq("name", name)))

    fun updateById(id: Long, user: UserDAO): UserDAO = TODO("not implemented")

    fun deleteById(id: Long): Boolean = TODO("not implemented")

    private fun getCollection() = client.getDatabase("mongodb")
            .getCollection("user", UserDAO::class.java)
}

//Relational, sync, mostly ACID
@Singleton
open class JPAUserRepository(@PersistenceContext
                             @CurrentSession
                             var entityManager: EntityManager) {

    @Transactional
    open fun create(user: UserEntity): UserEntity {
        entityManager.persist(user)
        return readByName(user.name) ?: throw RuntimeException("User creation failed!")
    }

    @Transactional
    open fun readByName(name: String): UserEntity? = entityManager.find(UserEntity::class.java, name)

    @Transactional
    fun updateByName(name: String, userData: UserDAO): UserEntity {
        if (userData.name != null) update("name", userData.name!!, name)
        if (userData.email != null) update("email", userData.email!!, name)
        if (userData.password != null) update("password", userData.password!!, name)
        return entityManager.find(UserEntity::class.java, name)
    }

    private fun update(column: String, value: String, id: String) {
        entityManager.createQuery("UPDATE UserEntity u SET $column = $value where name = $id")
//        entityManager.createQuery("UPDATE UserEntity u SET $column = :$column where name = :id")
//                .setParameter(column, value)
//                .setParameter("id", id)
                .executeUpdate()
    }

    @Transactional
    fun deleteByName(name: String): Boolean {
        val user = readByName(name)
        if (user != null) entityManager.remove(user)
        return entityManager.contains(user)
    }
}