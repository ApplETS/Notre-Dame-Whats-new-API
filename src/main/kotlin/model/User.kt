package ca.etsmtl.applets.notre_dame.whatsnew.model

import ca.etsmtl.applets.notre_dame.whatsnew.ApiExceptions.BadUserFormat
import ca.etsmtl.applets.notre_dame.whatsnew.utils.BcryptHasher
import ca.etsmtl.applets.notre_dame.whatsnew.utils.Roles
import io.ktor.auth.Principal
import io.ktor.features.BadRequestException
import io.ktor.util.KtorExperimentalAPI
import kotlin.reflect.full.memberProperties

@UseExperimental(
    KtorExperimentalAPI::class
)
data class User(
    val _id: String,
    var userName: String,
    var role: Roles,
    var password: String,
    var token: String
) : Principal {
    constructor(_id: String, data: Map<String, Any>) : this(
        _id,
        data["userName"].toString(),
        Roles.valueOf(data["role"].toString()),
        data["password"].toString(),
        data["token"].toString()
    )

    init {
        for (prop in User::class.memberProperties) {
            var propName = prop.name
            var propVal = prop.get(this)
            if (propVal is String && propName != "token") {
                if (propVal.toString().isBlank())
                    throw BadUserFormat
            } else if (propName == "role" && propVal == null)
                throw BadUserFormat
        }
    }

    fun convertToUserProfile(): UserProfile {
        return UserProfile(this._id.toString(), this.userName, this.role)
    }

    fun patchUser(other: UserPatch) {
        if (other.userName.isNullOrBlank() && other.role == null && other.password.isNullOrBlank())
            throw BadRequestException("Empty Patch is not allowed")
        if (!other.userName.isNullOrBlank())
            this.userName = other.userName!!
        if (other.role != null)
            this.role = other.role!!
        if (!other.password.isNullOrBlank()) {
            var pass = BcryptHasher.hashPassword(other.password!!)
            this.password = pass
        }
    }

    fun toMapUpdate(): Map<String, Any> {
        return mapOf(
            "userName" to userName,
            "role" to role.roleStr,
            "password" to password,
            "token" to token
        )
    }
}

