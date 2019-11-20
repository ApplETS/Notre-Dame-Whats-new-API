package ca.etsmtl.applets.notre_dame.model

import ca.etsmtl.applets.notre_dame.utils.BcryptHasher
import ca.etsmtl.applets.notre_dame.utils.Roles
import io.ktor.auth.Principal
import io.ktor.util.KtorExperimentalAPI
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

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

    fun convertToUserProfile(): UserProfile {
        return UserProfile(this._id.toString(), this.userName, this.role)
    }

    fun patchUser(other: UserPatch) {
        if (other.userName != null)
            this.userName = other.userName!!
        if (other.role != null)
            this.role = other.role!!
        if (other.password != null) {
            var pass = BcryptHasher.hashPassword(other.password!!)
            this.password = pass
        }
    }
}

