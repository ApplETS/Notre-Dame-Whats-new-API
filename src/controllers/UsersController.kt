package ca.etsmtl.applets.notre_dame.controllers

import ca.etsmtl.applets.notre_dame.ApiExceptions.UserNotFound
import ca.etsmtl.applets.notre_dame.model.*
import ca.etsmtl.applets.notre_dame.service.UsersService
import ca.etsmtl.applets.notre_dame.utils.BcryptHasher
import ca.etsmtl.applets.notre_dame.utils.JwtConfig
import ca.etsmtl.applets.notre_dame.utils.checkIfAdmin
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.principal
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import org.kodein.di.generic.instance

class UsersController(override val kodein: Kodein) : KodeinAware {
    private val app: Application by instance()
    private val service: UsersService by instance("usersService")

    init {
        app.routing {
            authenticate {
                get("/users") {
                    if (checkIfAdmin(call.principal<User>()))
                        call.respond(service.getAllUsers())
                    else
                        call.respond(HttpStatusCode.Forbidden)
                }
                get("/users/{id}") {
                    if (checkIfAdmin(call.principal<User>())) {
                        val id = call.parameters["id"]?.toString()
                        if (id != null) {
                            call.respond(call.respond(service.getUser(id)!!))
                        } else
                            call.respond(HttpStatusCode.BadRequest)
                    }
                    else
                        call.respond(HttpStatusCode.Forbidden)
                }
                post("/users") {
                    if (checkIfAdmin(call.principal<User>()))
                        call.respond(service.addNewUser(call.receive<UserRegistration>()))
                    else
                        call.respond(HttpStatusCode.Forbidden)
                }
                delete("/users/{id}") {
                    if (checkIfAdmin(call.principal<User>())) {
                        val id = call.parameters["id"]?.toString()
                        if (id != null) {
                            call.respond(service.deleteUserById(id))
                        } else
                            call.respond(HttpStatusCode.BadRequest)
                    } else
                        call.respond(HttpStatusCode.Forbidden)
                }
                patch("/users/{id}")
                {
                    if (checkIfAdmin(call.principal<User>())) {
                        val id = call.parameters["id"]?.toString()
                        if (id != null) {
                            call. respond(service.updateUser(call.receive<UserPatch>(), id))
                        } else
                            call.respond(HttpStatusCode.BadRequest)
                    } else
                        call.respond(HttpStatusCode.Forbidden)
                }
            }

            post("/users/login") {
                val credentials = call.receive<LoginCredentials>()
                val user = service.findByUsername(credentials.userName) ?: throw UserNotFound
                BcryptHasher.checkPassword(credentials.password, user)
                val token = JwtConfig.makeToken(user)
                service.updateUserToken(user.copy(token = token))
                val toReturn = LoginReturnedObj (token, user.userName, user.role )
                call.respond(toReturn )
            }
        }
    }

}