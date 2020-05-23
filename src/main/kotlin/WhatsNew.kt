package ca.etsmtl.applets.notre_dame.whatsnew

import ca.etsmtl.applets.notre_dame.whatsnew.config.*
import ca.etsmtl.applets.notre_dame.whatsnew.repository.UsersRepo
import ca.etsmtl.applets.notre_dame.whatsnew.server.Server
import io.ktor.application.Application
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.litote.kmongo.KMongo


fun main() {
    val kodein = Kodein {
        bind("mongoClient") from singleton { KMongo.createClient() }
        bind<Server>() with eagerSingleton { Server(kodein) }
        bind(tag = "usersRepo") from singleton { UsersRepo(instance("mongoClient")) }
    }
    var app: Server = kodein.direct.instance()
    app.startServer()
}
