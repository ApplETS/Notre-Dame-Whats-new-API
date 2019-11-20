package ca.etsmtl.applets.notre_dame.model

import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import org.litote.kmongo.Id
import org.litote.kmongo.newId

@KtorExperimentalLocationsAPI
@Location("/whatsNew/{version}")
data class WhatsNew(
    val _id : Id<WhatsNew> = newId(),
    var title : String,
    var description : String,
    val version : Float ){

    fun patchWhatsNew(other: WhatsNewPatch)  {
        if (other.title != null)
            this.title =other.title!!
        if (other.description  != null)
            this.description =other.description!!
    }
}