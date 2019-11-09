package ca.etsmtl.applets.notre_dame.service

import ca.etsmtl.applets.notre_dame.ApiExceptions.UserAlreadyExists
import ca.etsmtl.applets.notre_dame.model.User
import ca.etsmtl.applets.notre_dame.model.UserProfile
import ca.etsmtl.applets.notre_dame.model.UserRegistration
import ca.etsmtl.applets.notre_dame.repository.UsersRepo
import ca.etsmtl.applets.notre_dame.utils.BcryptHasher
import com.mongodb.client.result.UpdateResult

class UsersService ( val repo : UsersRepo) {

    fun addNewUser ( user : UserRegistration) : Unit{
        val checkUserExists=findByUsername(user.userName) != null
        if (checkUserExists)
        {
            throw UserAlreadyExists;
        }
        val hashedPass = BcryptHasher.hashPassword(user.password)
        val userToAdd = User( userName = user.userName, password = hashedPass, role = user.role, token = "")
        return repo.addUser(userToAdd)
    }

    fun getAllUsers () :MutableList<UserProfile>
    {
        return repo.getAllUsers()
    }

    fun findByUsername( userName : String) : User?
    {
        return repo.findByUserName(userName)
    }

    fun updateUserToken ( user : User) : UpdateResult
    {
        return repo.updateUserToken(user)
    }
}