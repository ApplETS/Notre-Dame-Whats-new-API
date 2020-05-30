package ca.etsmtl.applets.notre_dame.whatsnew.model

import ca.etsmtl.applets.notre_dame.whatsnew.ApiExceptions.BadWhatsNewFormat
import io.ktor.features.BadRequestException
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import kotlin.reflect.full.memberProperties

@KtorExperimentalLocationsAPI
@Location("/whatsNew/{version}")
data class WhatsNew(
    val _id: String,
    var title: String,
    var description: String,
    val version: String,
    var paddedVersion: Long
) {
    constructor(_id: String, data: Map<String, Any>) : this(
        _id, data["title"].toString(),
        data["description"].toString(),
        data["version"].toString(),
        data["paddedVersion"].toString().toLong()
    )

    init {
        for (prop in WhatsNew::class.memberProperties) {
            var propName = prop.name
            var propVal = prop.get(this)
            if (propVal is String && propName != "_id") {
                if (propVal.toString().isBlank())
                    throw BadWhatsNewFormat
            }
        }
    }

    fun patchWhatsNew(other: WhatsNewPatch) {
        if (other.title.isNullOrBlank() && other.description.isNullOrBlank())
            throw BadRequestException("Empty Patch is not allowed")
        if (!other.title.isNullOrBlank())
            this.title = other.title!!
        if (!other.description.isNullOrBlank())
            this.description = other.description!!
    }

    fun set(version: String) {
        this.paddedVersion = addPaddingToVersion(version)
    }

    fun toWhatsNewToReturn(): WhatsNewToReturn {
        return WhatsNewToReturn(this._id, this.title, this.description, this.version)
    }

    fun toMapUpdate(): Map<String, Any> {
        return mapOf(
            "title" to title,
            "description" to description,
            "version" to version,
            "paddedVersion" to paddedVersion
        );
    }
}

fun addPaddingToVersion(version: String): Long {
    var str = ""
    var splittedVersion = version.split('.')
    splittedVersion.forEach {
        for (i in 1..4 - it.length) {
            str += 0
        }
        str += it
    }
    return str.toLong()
}
