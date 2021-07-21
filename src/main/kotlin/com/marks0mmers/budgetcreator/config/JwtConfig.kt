package com.marks0mmers.budgetcreator.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.marks0mmers.budgetcreator.models.dto.UserDto
import com.marks0mmers.budgetcreator.services.UserService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus
import kotlinx.datetime.toJavaInstant
import java.util.*

object JwtConfig {
    private const val secret = "zAP5MBA4B4Ijz0MZaS48"
    private const val issuer = "ktor.io"
    private const val validityInMs = 36_000_00 * 10

    private val algorithm = Algorithm.HMAC512(secret)
    private val verifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    private val expiration
        get() = Clock.System.now()
            .plus(validityInMs, DateTimeUnit.MILLISECOND)
            .toJavaInstant()

    fun UserDto.generateToken() {
        token = JWT.create()
            .withSubject("Authentication")
            .withIssuer(issuer)
            .withClaim("id", this.id)
            .withExpiresAt(Date.from(expiration))
            .sign(algorithm)
    }

    fun Application.authConfig() {
        install(Authentication) {
            jwt {
                verifier(verifier)
                realm = "ktor.io"
                validate {
                    val id = it.payload.getClaim("id").asInt()
                    UserService.getUserById(id)
                }
            }
        }
    }
}
