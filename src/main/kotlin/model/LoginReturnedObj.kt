package ca.etsmtl.applets.notre_dame.whatsnew.model

import ca.etsmtl.applets.notre_dame.whatsnew.utils.Roles

data class LoginReturnedObj (
    val token : String,
    var userName : String,
    var role : Roles
)
