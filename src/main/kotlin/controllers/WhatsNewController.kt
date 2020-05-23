package ca.etsmtl.applets.notre_dame.whatsnew.controllers

import ca.etsmtl.applets.notre_dame.whatsnew.model.WhatsNew
import ca.etsmtl.applets.notre_dame.whatsnew.model.WhatsNewPatch
import ca.etsmtl.applets.notre_dame.whatsnew.model.addPaddingToVersion
import ca.etsmtl.applets.notre_dame.whatsnew.service.WhatsNewService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.routing
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import io.ktor.routing.*

@KtorExperimentalLocationsAPI
@io.ktor.util.KtorExperimentalAPI
class WhatsNewController(override val kodein: Kodein) : KodeinAware {
    private val app: Application by instance<Application>()
    private val service: WhatsNewService by instance<WhatsNewService>("whatsNewService")

    init {
        app.routing {
            authenticate {
                get("/whatsNew/en") {
                    call.respond(service.getAllWhatsNewEn())
                }

                post("/whatsNew/en") {
                    val newWhatsNew = call.receive<WhatsNew>()
                    newWhatsNew.set(newWhatsNew.version)
                    call.respond(service.addNewWhatsNewEn(newWhatsNew))
                }
                patch("/whatsNew/en/{id}") {
                    val id = call.parameters["id"]?.toString()
                    if (id != null) {
                        val newWhatsNew = call.receive<WhatsNewPatch>()
                        call.respond(service.updateWhatsNewEn(newWhatsNew, id))
                    } else
                        call.respond(HttpStatusCode.BadRequest)
                }
                delete("/whatsNew/en/{id}") {
                    val id = call.parameters["id"]?.toString()
                    if (id != null) {
                        call.respond(service.deleteWhatNewEn(id))
                    } else
                        call.respond(HttpStatusCode.BadRequest)
                }
                get("/whatsNew/fr") {
                    call.respond(service.getAllWhatsNewFr())
                }

                post("/whatsNew/fr") {
                    val newWhatsNew = call.receive<WhatsNew>()
                    newWhatsNew.set(newWhatsNew.version)
                    call.respond(service.addNewWhatsNewFr(newWhatsNew))
                }
                patch("/whatsNew/fr/{id}") {
                    val id = call.parameters["id"]?.toString()
                    if (id != null) {
                        val newWhatsNew = call.receive<WhatsNewPatch>()
                        call.respond(service.updateWhatsNewFr(newWhatsNew, id))
                    } else
                        call.respond(HttpStatusCode.BadRequest)
                }
                delete("/whatsNew/fr/{id}") {
                    val id = call.parameters["id"]?.toString()
                    if (id != null) {
                        call.respond(service.deleteWhatNewFr(id))
                    } else
                        call.respond(HttpStatusCode.BadRequest)
                }
            }
            get("/whatsNew/en/{versionTo}") {
                val versionTo = call.parameters["versionTo"]
                var params = call.request.queryParameters
                print("here")
                if(versionTo != null && !params.isEmpty() && params.contains("versionFrom"))
                {
                    call.respond(service.getByVersionRangeEn(addPaddingToVersion(params["versionFrom"]!!),addPaddingToVersion(versionTo) ))
                }
                else
                    call.respond(HttpStatusCode.BadRequest)

            }

            get("/whatsNew/fr/{versionTo}") {
                val versionTo = call.parameters["versionTo"]
                var params = call.request.queryParameters
                if(versionTo != null && !params.isEmpty() && params.contains("versionFrom"))
                {
                    call.respond(service.getByVersionRangeFr(addPaddingToVersion(params["versionFrom"]!!),addPaddingToVersion(versionTo) ))
                }
                else
                    call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}
