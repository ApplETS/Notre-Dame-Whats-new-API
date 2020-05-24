package ca.etsmtl.applets.notre_dame.whatsnew.service

import ca.etsmtl.applets.notre_dame.whatsnew.ApiExceptions.UserAlreadyExists
import ca.etsmtl.applets.notre_dame.whatsnew.ApiExceptions.UserNotFound
import ca.etsmtl.applets.notre_dame.whatsnew.model.User
import ca.etsmtl.applets.notre_dame.whatsnew.model.UserPatch
import ca.etsmtl.applets.notre_dame.whatsnew.model.UserProfile
import ca.etsmtl.applets.notre_dame.whatsnew.model.UserRegistration
import ca.etsmtl.applets.notre_dame.whatsnew.repository.UsersRepo
import ca.etsmtl.applets.notre_dame.whatsnew.utils.BcryptHasher
import com.google.cloud.firestore.WriteResult

class UsersService(val repo: UsersRepo) {

    fun addNewUser(user: UserRegistration): Unit {
        val checkUserExists = findByUsername(user.userName) != null
        if (checkUserExists) {
            throw UserAlreadyExists;
        }
        val hashedPass = BcryptHasher.hashPassword(user.password)
        val userToAdd = User(_id = "", userName = user.userName, password = hashedPass, role = user.role, token = "")
        return repo.addUser(userToAdd)
    }

    fun getAllUsers(): MutableList<UserProfile> {
        return repo.getAllUsers()
    }

    fun getUser(id: String): User? {
        return repo.findById(id)
    }

    fun findByUsername(userName: String): User? {
        return repo.findByUserName(userName)
    }

    fun updateUserToken(user: User): WriteResult {
        return repo.updateUserToken(user)
    }

    fun deleteUserById(id: String): WriteResult {
        return repo.deleteUserById(id)
    }

    fun updateUser(userPatch: UserPatch, id: String): WriteResult {
        var originalUser = this.getUser(id)
        originalUser ?: throw UserNotFound
        originalUser.patchUser(userPatch)
        return repo.updateUser(originalUser)
    }
}
