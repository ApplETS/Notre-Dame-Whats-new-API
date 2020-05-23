package ca.etsmtl.applets.notre_dame.whatsnew.model

import ca.etsmtl.applets.notre_dame.whatsnew.utils.Roles

data class UserRegistration (
    var userName : String,
    var role : Roles,
    var password : String)
