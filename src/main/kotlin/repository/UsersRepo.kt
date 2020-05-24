package ca.etsmtl.applets.notre_dame.whatsnew.repository

import ca.etsmtl.applets.notre_dame.whatsnew.model.User
import ca.etsmtl.applets.notre_dame.whatsnew.model.UserProfile
import ca.etsmtl.applets.notre_dame.whatsnew.utils.Property
import ca.etsmtl.applets.notre_dame.whatsnew.utils.Roles
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.WriteResult

class UsersRepo(private val client: Firestore) {
    private val usersCollection = client.collection(Property["db.usersCollection"])

    fun addUser(userOb: User): Unit {
        usersCollection.add(userOb)
    }

    fun getAllUsers(): MutableList<UserProfile> {
        return usersCollection.listDocuments().map {
            val doc = it.get().get();

            User(
                doc.id,
                doc.data?.get("userName").toString(),
                Roles.valueOf(doc.data?.get("role").toString()),
                doc.data?.get("password").toString(),
                doc.data?.get("token").toString()
            ).convertToUserProfile()
        }.toMutableList()
    }

    fun findByUserName(userName: String): User? {
        val future = usersCollection.whereEqualTo("userName", userName).limit(1).get()
        val doc = future.get()

        return if (doc.isEmpty)
            null
        else
            User(doc.documents[0].id, doc.documents[0].data)
    }

    fun findById(id: String): User? {
        val doc = usersCollection.document(id).get().get()

        return if(doc.exists())
            doc.data?.let { User(doc.id, it) }
        else
            null
    }

    fun updateUserToken(user: User): WriteResult {
        return usersCollection.document(user._id).update("token", user.token).get()
    }

    fun deleteUserById(id: String): WriteResult {
        return usersCollection.document(id).delete().get()
    }

    fun updateUser(user: User): WriteResult {
        return usersCollection.document(user._id).update(user.toMapUpdate()).get()
    }
}
