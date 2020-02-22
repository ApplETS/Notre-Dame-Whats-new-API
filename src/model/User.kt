package ca.etsmtl.applets.notre_dame.model

import ca.etsmtl.applets.notre_dame.ApiExceptions.BadUserFormat
import ca.etsmtl.applets.notre_dame.utils.BcryptHasher
import ca.etsmtl.applets.notre_dame.utils.Roles
import io.ktor.auth.Principal
import io.ktor.features.BadRequestException
import io.ktor.util.KtorExperimentalAPI
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import kotlin.reflect.full.memberProperties

@UseExperimental(
    KtorExperimentalAPI::class
)
data class User(
    @BsonId val _id: Id<User> = newId(),
    var userName: String,
    var role: Roles,
    var password: String,
    var token: String
) : Principal {

    init{
        for (prop in User::class.memberProperties){
            var propName = prop.name
            var propVal = prop.get(this)
            if ( propVal is String && propName != "token" )
            {
                if (propVal.toString().isBlank())
                    throw BadUserFormat
            }
            else if (propName == "role" && propVal==null)
                throw BadUserFormat
        }
    }

    fun convertToUserProfile(): UserProfile {
        return UserProfile(this._id.toString(), this.userName, this.role)
    }

    fun patchUser(other: UserPatch) {
        if( other.userName.isNullOrBlank() && other.role==null &&other.password.isNullOrBlank() )
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
}

