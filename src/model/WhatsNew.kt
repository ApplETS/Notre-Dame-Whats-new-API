package ca.etsmtl.applets.notre_dame.model

import ca.etsmtl.applets.notre_dame.ApiExceptions.BadWhatsNewFormat
import io.ktor.features.BadRequestException
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import kotlin.reflect.full.memberProperties

@KtorExperimentalLocationsAPI
@Location("/whatsNew/{version}")
data class WhatsNew(
    val _id : Id<WhatsNew> = newId(),
    var title : String,
    var description : String,
    val version : Float ){

    init{
        for (prop in WhatsNew::class.memberProperties){
            var propVal = prop.get(this)
            if ( propVal is String )
            {
                if (propVal.toString().isBlank())
                    throw BadWhatsNewFormat
            }
        }
    }

    fun patchWhatsNew(other: WhatsNewPatch)  {
        if( other.title.isNullOrBlank() && other.description.isNullOrBlank() )
            throw BadRequestException("Empty Patch is not allowed")
        if (!other.title.isNullOrBlank())
            this.title =other.title!!
        if (!other.description.isNullOrBlank())
            this.description =other.description!!
    }
}