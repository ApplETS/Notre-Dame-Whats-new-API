package ca.etsmtl.applets.notre_dame.model

import ca.etsmtl.applets.notre_dame.utils.Roles

data class UserProfile (
    val id : String,
    var userName : String,
    var role : Roles)