package com.palmatolay.micronaut.web

import com.palmatolay.micronaut.db.UserDAO
import com.palmatolay.micronaut.service.JPAUserService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import java.security.Principal
/**
 * Responsible for present data towards client. Provide communication between the client and service.
 *
 * Produce and consume json by default because of the controller annotation (except where procedures set differently).
 *
 */
@Controller("/")
class UserController(private val userService: JPAUserService) {

    @Get(produces = [MediaType.TEXT_PLAIN])
    fun index() = "OK"

    @Get("/secured", produces = [MediaType.TEXT_PLAIN])
    @Secured("isAuthenticated()")
    fun authTest(principal: Principal) = "Logged in as: ${principal.name}"

    @Post("/users")
    fun register(@Body user: UserDAO): MutableHttpResponse<String>? {
        println("$user")
        return HttpResponse.created("${userService.save(user)}")
    }

    @Get("/users/{name}")
    fun getOneUser(name: String) = HttpResponse.ok("${userService.findByName(name)}")

    @Put("/users/{name}")
    fun updateOneUser(name: String, @Body user: UserDAO) =
            HttpResponse.ok(userService.updateByName(name, user))

    @Delete("/users/{name}")
    fun deleteOneUser(name: String) = HttpResponse.ok("${userService.deleteByName(name)}")
}

