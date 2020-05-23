package ca.etsmtl.applets.notre_dame.whatsnew.server

import ca.etsmtl.applets.notre_dame.whatsnew.config.*
import ca.etsmtl.applets.notre_dame.whatsnew.repository.UsersRepo
import ca.etsmtl.applets.notre_dame.whatsnew.utils.JwtConfig
import com.fasterxml.jackson.databind.SerializationFeature
import com.typesafe.config.ConfigFactory
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.config.HoconApplicationConfig
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.jackson.jackson
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.event.Level

val serverLogger: Logger = LoggerFactory.getLogger("Server")

@UseExperimental(KtorExperimentalLocationsAPI::class, io.ktor.util.KtorExperimentalAPI::class)
class Server (override val kodein: Kodein) : KodeinAware {
    private val config = HoconApplicationConfig(ConfigFactory.load())
    private val port = config.property("ktor.deployment.port").getString().toInt()

    public fun startServer() = embeddedServer(Netty, port = port) {
        kodeinApplication {
            module()
            import(common)
            import(whatsNewRepo)
            import(whatsNewService)
            import(whatsNewSController)
            import(usersRepo)
            import(usersService)
            import(usersController)
        }
    }.start(wait = true)

    fun Application.kodeinApplication(kodeinMapper: Kodein.MainBuilder.(Application) -> Unit = {}) {
        val app = this
        Kodein {
            bind<Application>() with singleton { app }
            kodeinMapper(this, app)
        }
    }

    fun Application.module() {
        val repo: UsersRepo by instance<UsersRepo>("usersRepo")

        install(CallLogging) {
            level = Level.INFO
        }
        install(DefaultHeaders)
        install(ContentNegotiation) {
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
            }
        }
        install(StatusPages) {
            setup()
        }
        install(Authentication) {
            jwt {
                verifier(JwtConfig.verifier)
                validate {
                    it.payload.claims.forEach(::println)
                    val userName = it.payload.getClaim("userName")?.asString() ?: return@validate null
                    repo.findByUserName(userName)?.let { user ->
                        val token = JwtConfig.makeToken(user)
                        user.copy(token = token)
                    }
                }
            }
        }
    }
}
