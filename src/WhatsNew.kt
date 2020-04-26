package ca.etsmtl.applets.notre_dame

import ca.etsmtl.applets.notre_dame.repository.UsersRepo
import ca.etsmtl.applets.notre_dame.server.Server
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.litote.kmongo.KMongo


fun main(args: Array<String>) {

    val kodein = Kodein {
        bind("mongoClient") from singleton { KMongo.createClient() }
        bind<Server>() with eagerSingleton { Server(kodein) }
        bind(tag = "usersRepo") from singleton { UsersRepo(instance("mongoClient")) }
    }
    var app: Server = kodein.direct.instance()
    app.startServer()
}
