package ca.etsmtl.applets.notre_dame.service

import ca.etsmtl.applets.notre_dame.ApiExceptions.UserAlreadyExists
import ca.etsmtl.applets.notre_dame.model.User
import ca.etsmtl.applets.notre_dame.model.UserProfile
import ca.etsmtl.applets.notre_dame.model.UserRegistration
import ca.etsmtl.applets.notre_dame.repository.UsersRepo
import ca.etsmtl.applets.notre_dame.utils.BcryptHasher
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.MongoId
import org.litote.kmongo.id.toId
import org.litote.kmongo.newId
import java.util.*

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

    fun getUser (id :String) : User?
    {
        return repo.findById(ObjectId(id).toId())
    }

    fun findByUsername( userName : String) : User?
    {
        return repo.findByUserName(userName)
    }

    fun updateUserToken ( user : User) : UpdateResult
    {
        return repo.updateUserToken(user)
    }

    fun deleteUserById ( id : String) : DeleteResult
    {
        return repo.deleteUserById(ObjectId(id).toId())
    }

    fun updateUser(userPatch : User, id : String) : UpdateResult
    {
        userPatch.copy(_id =ObjectId(id).toId())
        return repo.updateUser(userPatch)
    }
}