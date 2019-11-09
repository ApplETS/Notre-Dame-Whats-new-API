package ca.etsmtl.applets.notre_dame.service

import ca.etsmtl.applets.notre_dame.model.WhatsNew
import ca.etsmtl.applets.notre_dame.repository.WhatsNewRepo
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
class WhatsNewService (val repo : WhatsNewRepo) {

    fun getByVersionEn ( version : Float) : MutableList<WhatsNew>
    {
        return repo.getByVersionEn(version)
    }

    fun addNewWhatsNewEn ( whatsNewOb : WhatsNew) : Unit{
        return repo.addWhatNewEn(whatsNewOb)
    }

    fun getAllWhatsNewEn () :MutableList<WhatsNew>
    {
        return repo.getAllWhatsNewEn()
    }

    fun getByVersionFr ( version : Float) : MutableList<WhatsNew>
    {
        return repo.getByVersionFr(version)
    }

    fun addNewWhatsNewFr ( whatsNewOb : WhatsNew) : Unit{
        return repo.addWhatNewFr(whatsNewOb)
    }

    fun getAllWhatsNewFr () :MutableList<WhatsNew>
    {
        return repo.getAllWhatsNewFr()
    }

}