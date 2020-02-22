package ca.etsmtl.applets.notre_dame

import ca.etsmtl.applets.notre_dame.server.Server
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance


fun main(args: Array<String>) {

    val kodein = Kodein {
        bind<Server>() with eagerSingleton { Server(kodein) }
    }
    var app: Server = kodein.direct.instance()
    app.startServer()
}
