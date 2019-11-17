package ca.etsmtl.applets.notre_dame.utils

import ca.etsmtl.applets.notre_dame.model.User

fun checkIfAdmin (principal  : User?) : Boolean
{
    return principal?.role == Roles.ADMIN
}