package ca.etsmtl.applets.notre_dame.whatsnew.model

import ca.etsmtl.applets.notre_dame.whatsnew.utils.Roles

data class UserProfile (
    val id : String,
    var userName : String,
    var role : Roles)
