package com.palmatolay.micronaut.db

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@Table(name = "user")
data class UserEntity(

        @Id
        var name: String = "",

        var email: String = "",

        @JsonIgnore
        @NotEmpty
        @NotNull
        var password: String = ""

//        @Id
//        @GeneratedValue(strategy = GenerationType.AUTO)
//        @JsonIgnore
//        var id: Long? = null
)