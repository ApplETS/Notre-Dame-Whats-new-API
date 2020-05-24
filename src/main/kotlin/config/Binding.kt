package ca.etsmtl.applets.notre_dame.whatsnew.config

import ca.etsmtl.applets.notre_dame.whatsnew.controllers.UsersController
import ca.etsmtl.applets.notre_dame.whatsnew.controllers.WhatsNewController
import ca.etsmtl.applets.notre_dame.whatsnew.repository.UsersRepo
import ca.etsmtl.applets.notre_dame.whatsnew.repository.WhatsNewRepo
import ca.etsmtl.applets.notre_dame.whatsnew.service.UsersService
import ca.etsmtl.applets.notre_dame.whatsnew.service.WhatsNewService
import com.google.cloud.firestore.FirestoreOptions
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/*** MongoDb********************************/
val common = Kodein.Module(name = "common") {
    bind("firestore") from singleton {
        FirestoreOptions.getDefaultInstance().service
    }
}

/*** WhatsNew********************************/
@UseExperimental(KtorExperimentalLocationsAPI::class, KtorExperimentalAPI::class)
val whatsNewRepo = Kodein.Module(name = "whatsNewRepo") {
    bind(tag = "whatsNewRepo") from singleton { WhatsNewRepo(instance("firestore")) }
}

@UseExperimental(KtorExperimentalAPI::class, KtorExperimentalLocationsAPI::class)
val whatsNewService = Kodein.Module(name = "whatsNewService") {
    bind(tag = "whatsNewService") from singleton { WhatsNewService(instance("whatsNewRepo")) }
}

@UseExperimental(KtorExperimentalLocationsAPI::class, KtorExperimentalAPI::class)
val whatsNewSController = Kodein.Module(name = "whatsNewSController") {
    bind(tag = "whatsNewSController") from eagerSingleton { WhatsNewController(kodein) }
}

/*** Users********************************/
val usersRepo = Kodein.Module(name = "usersRepo") {
    bind(tag = "usersRepo") from singleton { UsersRepo(instance("firestore")) }
}

val usersService = Kodein.Module(name = "usersService") {
    bind(tag = "usersService") from singleton { UsersService(instance("usersRepo")) }
}

val usersController = Kodein.Module(name = "usersController") {
    bind(tag = "usersController") from eagerSingleton { UsersController(kodein) }
}


