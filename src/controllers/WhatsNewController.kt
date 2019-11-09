package ca.etsmtl.applets.notre_dame.controllers

import ca.etsmtl.applets.notre_dame.model.WhatsNew
import ca.etsmtl.applets.notre_dame.service.WhatsNewService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.routing
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import io.ktor.routing.get
import io.ktor.routing.post
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

@KtorExperimentalLocationsAPI
@io.ktor.util.KtorExperimentalAPI
class WhatsNewController (override val kodein: Kodein) : KodeinAware{
    private val app : Application by instance()
    private val service: WhatsNewService by instance("whatsNewService")
    val logger: Logger = LoggerFactory.getLogger("WhatsNewController")
    init {
        app.routing {
            authenticate {
                get("/whatsNew/en") {
                    call.respond(service.getAllWhatsNewEn())
                }

                post("/whatsNew/en") {
                    val newWhatsNew = call.receive<WhatsNew>()
                    call.respond(service.addNewWhatsNewEn(newWhatsNew))
                }
                get("/whatsNew/fr") {
                    call.respond(service.getAllWhatsNewFr())
                }

                post("/whatsNew/fr") {
                    val newWhatsNew = call.receive<WhatsNew>()
                    call.respond(service.addNewWhatsNewFr(newWhatsNew))
                }
            }
            get("/whatsNew/en/{version}")  {
                val version : Float = call.parameters["version"]?.toFloat() ?: 0.0f
                logger.info("method = Get")
                call.respond(service.getByVersionEn(version))
            }

            get("/whatsNew/fr/{version}")  {
                val version : Float = call.parameters["version"]?.toFloat() ?: 0.0f
                call.respond(service.getByVersionFr(version))
            }
        }
    }
}