package ca.etsmtl.applets.notre_dame.whatsnew

import ca.etsmtl.applets.notre_dame.whatsnew.server.Server
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance


fun main() {
    val kodein = Kodein {
        bind<Server>() with eagerSingleton { Server(kodein) }
    }
    var app: Server = kodein.direct.instance()
    app.startServer()
}
