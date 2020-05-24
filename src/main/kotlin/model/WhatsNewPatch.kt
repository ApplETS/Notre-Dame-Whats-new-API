package ca.etsmtl.applets.notre_dame.whatsnew.model

import io.ktor.locations.KtorExperimentalLocationsAPI

@UseExperimental(KtorExperimentalLocationsAPI::class)
data class WhatsNewPatch(
    val _id : String,
    var title : String?,
    var description : String?)
