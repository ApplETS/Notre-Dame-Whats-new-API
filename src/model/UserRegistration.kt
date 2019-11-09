package ca.etsmtl.applets.notre_dame.model

import ca.etsmtl.applets.notre_dame.utils.Roles

data class UserRegistration (
    var userName : String,
    var role : Roles,
    var password : String)