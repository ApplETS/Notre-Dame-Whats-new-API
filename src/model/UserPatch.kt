package ca.etsmtl.applets.notre_dame.model

import ca.etsmtl.applets.notre_dame.utils.Roles
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

data class UserPatch(
    @BsonId val _id: Id<User> = newId(),
    var userName: String?,
    var role: Roles?,
    var password: String?)