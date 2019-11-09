package ca.etsmtl.applets.notre_dame.utils

import ca.etsmtl.applets.notre_dame.model.User
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JwtConfig {

    private val secret = Property["jwt.secret"]
    private val validityInMs  = Property["jwt.validityInMs"].toLong()
    private val realm = Property["jwt.realm"]
    private val algorithm = Algorithm.HMAC256(secret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(realm)
        .build()

    /**
     * Produce a token for this combination of User and Account
     */
    fun makeToken(user: User): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(realm)
        .withClaim("id", user.id.toString())
        .withClaim("userName", user.userName)
        .withClaim("role", user.role.name)
        .withExpiresAt(getExpiration())
        .sign(algorithm)

    /**
     * Calculate the expiration Date based on current time + the given validity
     */
    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)

}