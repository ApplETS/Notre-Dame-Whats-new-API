package ca.etsmtl.applets.notre_dame.controllers

import ca.etsmtl.applets.notre_dame.ApiExceptions.UserNotFound
import ca.etsmtl.applets.notre_dame.model.LoginCredentials
import ca.etsmtl.applets.notre_dame.model.User
import ca.etsmtl.applets.notre_dame.model.UserRegistration
import ca.etsmtl.applets.notre_dame.service.UsersService
import ca.etsmtl.applets.notre_dame.utils.BcryptHasher
import ca.etsmtl.applets.notre_dame.utils.JwtConfig
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.routing
import org.kodein.di.generic.instance
import io.ktor.routing.get
import io.ktor.routing.post

class UsersController (override val kodein: Kodein) : KodeinAware {
    private val app : Application by instance()
    private val service: UsersService by instance("usersService")

    init {
        app.routing {
            authenticate{
            get("/users"){
                call.respond(service.getAllUsers())
            }

            post ("/users"){
                call.respond(service.addNewUser(call.receive<UserRegistration>()))
            }}

            post("/users/login"){
                val credentials = call .receive<LoginCredentials>()
                val user = service.findByUsername(credentials.userName) ?: throw UserNotFound
                BcryptHasher.checkPassword(credentials.password,user)
                val token = JwtConfig.makeToken(user)
                service.updateUserToken(user.copy(token = token))
                call.respond(token + " " + service.updateUserToken(user.copy(token = token)))
            }
        }
    }
}