package ca.etsmtl.applets.notre_dame.model

import io.ktor.locations.KtorExperimentalLocationsAPI
import org.litote.kmongo.Id
import org.litote.kmongo.newId

@UseExperimental(KtorExperimentalLocationsAPI::class)
data class WhatsNewPatch(
    val _id : Id<WhatsNew> = newId(),
    var title : String?,
    var description : String?)