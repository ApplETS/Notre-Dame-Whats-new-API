package ca.etsmtl.applets.notre_dame.ApiExceptions

import io.ktor.http.HttpStatusCode

class AuthorizationException : Exception() {

    val status = HttpStatusCode.Forbidden

}