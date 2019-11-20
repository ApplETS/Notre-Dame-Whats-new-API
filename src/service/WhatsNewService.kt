package ca.etsmtl.applets.notre_dame.service

import ca.etsmtl.applets.notre_dame.model.WhatsNew
import ca.etsmtl.applets.notre_dame.model.WhatsNewPatch
import ca.etsmtl.applets.notre_dame.repository.WhatsNewRepo
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import io.ktor.features.NotFoundException
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId

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

    fun getWhatsNewEn (id :String) : WhatsNew?
    {
        return repo.findByIdEn(ObjectId(id).toId())
    }

    fun updateWhatsNewEn (patchedWhatsNewEn : WhatsNewPatch, id : String) : UpdateResult
    {
        var whatsNewToUpdate = this.getWhatsNewEn(id)
        if (whatsNewToUpdate == null)
            throw NotFoundException("Bad id")
        whatsNewToUpdate?.patchWhatsNew(patchedWhatsNewEn)
        return repo.updateWhatsNewEn(whatsNewToUpdate)
    }

    fun getWhatsNewFr (id :String) : WhatsNew?
    {
        return repo.findByIdFr(ObjectId(id).toId())
    }


    fun updateWhatsNewFr (patchedWhatsNewFr : WhatsNewPatch, id : String) : UpdateResult
    {
        var whatsNewToUpdate = this.getWhatsNewFr(id)
        if (whatsNewToUpdate == null)
            throw NotFoundException("Bad id")
        whatsNewToUpdate?.patchWhatsNew(patchedWhatsNewFr)
        return repo.updateWhatsNewFr(whatsNewToUpdate)
    }

    fun deleteWhatNewEn( id : String) : DeleteResult
    {
        return repo.deleteWhatsNewEn(ObjectId(id).toId())
    }

    fun deleteWhatNewFr( id : String) : DeleteResult
    {
        return repo.deleteWhatsNewFr(ObjectId(id).toId())
    }
}