package ca.etsmtl.applets.notre_dame.model

import ca.etsmtl.applets.notre_dame.utils.Roles
import io.ktor.auth.Principal
import io.ktor.features.BadRequestException
import io.ktor.util.KtorExperimentalAPI
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

@UseExperimental(
    KtorExperimentalAPI::class)
data class User(
    @BsonId val _id: Id<User> = newId(),
    var userName: String?,
    var role: Roles?,
    var password: String?,
    var token: String?
) : Principal {
    fun convertToUserProfile(): UserProfile {
       if (this.userName == null || this.role == null) {
           throw BadRequestException(" bad request")
       }
        else
       {
           return UserProfile(this._id.toString(), this.userName!!, this.role!!)
       }

    }
}

