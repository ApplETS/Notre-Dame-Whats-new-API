package ca.etsmtl.applets.notre_dame.whatsnew.utils

import ca.etsmtl.applets.notre_dame.whatsnew.model.User

fun checkIfAdmin (principal  : User?) : Boolean
{
    return principal?.role == Roles.ADMIN
}
