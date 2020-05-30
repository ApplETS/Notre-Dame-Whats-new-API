package ca.etsmtl.applets.notre_dame.whatsnew

import ca.etsmtl.applets.notre_dame.whatsnew.repository.UsersRepo
import ca.etsmtl.applets.notre_dame.whatsnew.server.Server
import com.google.cloud.firestore.FirestoreOptions
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.singleton
import org.kodein.di.generic.instance


fun main() {
    val kodein = Kodein {
        bind("firestore") from singleton {  FirestoreOptions.getDefaultInstance().service }
        bind<Server>() with eagerSingleton { Server(kodein) }
        bind(tag = "usersRepo") from singleton { UsersRepo(instance("firestore")) }
    }
    var app: Server = kodein.direct.instance()
    app.startServer()
}
