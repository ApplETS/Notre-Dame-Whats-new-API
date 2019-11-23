package ca.etsmtl.applets.notre_dame.model

import ca.etsmtl.applets.notre_dame.utils.Roles

data class LoginReturnedObj (
    val token : String,
    var userName : String,
    var role : Roles
)