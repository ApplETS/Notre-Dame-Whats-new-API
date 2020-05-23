package ca.etsmtl.applets.notre_dame.whatsnew.utils

import ca.etsmtl.applets.notre_dame.whatsnew.model.User
import org.mindrot.jbcrypt.BCrypt

object BcryptHasher {

    /**
     * Check if the password matches the User's password
     */
    fun checkPassword(attempt: String, user: User) = if (BCrypt.checkpw(attempt, user.password)) Unit
    else throw Exception("Wrong Password")

    /**
     * Returns the hashed version of the supplied password
     */
    fun hashPassword(password: String): String = BCrypt.hashpw(password, BCrypt.gensalt())

}
