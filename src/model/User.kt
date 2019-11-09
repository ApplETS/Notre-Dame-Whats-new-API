package ca.etsmtl.applets.notre_dame.model

import ca.etsmtl.applets.notre_dame.utils.Roles
import io.ktor.auth.Principal
import org.litote.kmongo.Id
import org.litote.kmongo.newId

data class User (
    val id : Id<User> = newId(),
    var userName : String,
    var role : Roles,
    var password : String,
    var token : String): Principal
{
    public fun convertToUserProfile (): UserProfile
    {
        return UserProfile(this.id.toString(), this.userName, this.role)
    }
}

