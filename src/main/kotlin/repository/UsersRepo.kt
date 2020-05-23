package ca.etsmtl.applets.notre_dame.whatsnew.repository

import ca.etsmtl.applets.notre_dame.whatsnew.model.User
import ca.etsmtl.applets.notre_dame.whatsnew.model.UserProfile
import ca.etsmtl.applets.notre_dame.whatsnew.utils.Property
import com.mongodb.MongoClient
import com.mongodb.client.model.Updates
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import org.litote.kmongo.*

class UsersRepo (private val client: MongoClient) {
    private val databaseName  = Property["db.name"]
    private val usersCollName = Property["db.usersCollection"]
    private val usersCollection = client.getDatabase(databaseName).getCollection<User>(usersCollName)

    fun addUser ( userOb : User) :Unit
    {
        return usersCollection.insertOne(userOb)
    }

    fun getAllUsers() : MutableList<UserProfile>
    {
      var users= usersCollection.find().toMutableList();
      var usersProfiles : MutableList<UserProfile> =  ArrayList()
      for ( user in users)
      {
          usersProfiles.add(user.convertToUserProfile())
      }
        return usersProfiles
    }

    fun findByUserName ( userName : String) : User?
    {
        return usersCollection.findOne(User::userName eq userName)
    }

    fun findById ( id : Id<User>) : User?
    {
        return usersCollection.findOne(User::_id eq id)
    }

    fun updateUserToken( user :User): UpdateResult
    {
        return usersCollection.updateOne( User::_id eq user._id, Updates.set("token", user.token))

    }

    fun deleteUserById ( id : Id<User>) : DeleteResult
    {
        return usersCollection.deleteOne(User ::_id eq id)
    }

    fun updateUser (user: User) : UpdateResult
    {
        return usersCollection.updateOne(user)
    }
}
