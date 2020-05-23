package ca.etsmtl.applets.notre_dame.whatsnew.utils

import ca.etsmtl.applets.notre_dame.whatsnew.model.User
import com.github.salomonbrys.kotson.jsonArray
import com.github.salomonbrys.kotson.jsonObject
import io.ktor.application.ApplicationCall
import io.ktor.auth.authentication

val ApplicationCall.user: User? get() = authentication.principal()

fun Throwable.toJson() = jsonObject(
    "errors" to jsonObject(
        "body" to jsonArray(message)
    )
).toString()
