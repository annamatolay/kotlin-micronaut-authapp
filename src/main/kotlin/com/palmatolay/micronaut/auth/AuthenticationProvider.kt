package com.palmatolay.micronaut.auth

import com.palmatolay.micronaut.service.JPAUserService
import io.micronaut.security.authentication.AuthenticationFailed
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.UserDetails
import io.reactivex.Flowable
import org.reactivestreams.Publisher
import java.util.ArrayList
import javax.inject.Singleton

@Singleton
class AuthenticationProviderUserPassword(private val userService: JPAUserService,
                                         private val passwordEncoder: PasswordEncoder): AuthenticationProvider{

    override fun authenticate(authenticationRequest: AuthenticationRequest<*, *>): Publisher<AuthenticationResponse> {
        val user = userService.findByName(authenticationRequest.identity as String)
        return (if (user != null && passwordEncoder.matches(authenticationRequest.secret as String, user.password)) {
            Flowable.just(UserDetails(user.name, ArrayList()))
        } else {
            Flowable.just(AuthenticationFailed())
        })
    }

}