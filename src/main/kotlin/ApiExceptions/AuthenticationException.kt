package ca.etsmtl.applets.notre_dame.whatsnew.ApiExceptions

import io.ktor.http.HttpStatusCode

open class AuthenticationException(val status: HttpStatusCode, override val message: String) : Exception()

val UserNotFound = AuthenticationException(HttpStatusCode.NotFound, "The specified user could not be found")
val WrongPassword = AuthenticationException(HttpStatusCode.Unauthorized, "Wrong credentials")

val UserAlreadyExists = AuthenticationException(HttpStatusCode.Conflict, "The specified user already exists")

val BadUserFormat = AuthenticationException(HttpStatusCode.BadRequest, "User's fields can not have empty strings")
val BadWhatsNewFormat = AuthenticationException(HttpStatusCode.BadRequest, "Whats new fields can not have empty strings")
