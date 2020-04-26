package ca.etsmtl.applets.notre_dame.service

import ca.etsmtl.applets.notre_dame.model.WhatsNew
import ca.etsmtl.applets.notre_dame.model.WhatsNewPatch
import ca.etsmtl.applets.notre_dame.model.WhatsNewToReturn
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

    fun getByVersionEn ( version : String) : MutableList<WhatsNewToReturn>
    {
        val foundObjs = repo.getByVersionEn(version)
        var objectsToReturn :MutableList<WhatsNewToReturn> = ArrayList<WhatsNewToReturn>()
        foundObjs.forEach {
            objectsToReturn.add(it.toWhatsNewToReturn())
        }
        return objectsToReturn
    }

    fun addNewWhatsNewEn ( whatsNewOb : WhatsNew) : Unit{
        return repo.addWhatNewEn(whatsNewOb)
    }

    fun getAllWhatsNewEn () :MutableList<WhatsNewToReturn>
    {
        val foundObjs = repo.getAllWhatsNewEn()
        var objectsToReturn :MutableList<WhatsNewToReturn> = ArrayList<WhatsNewToReturn>()
        foundObjs.forEach {
            objectsToReturn.add(it.toWhatsNewToReturn())
        }
        return objectsToReturn
    }

    fun getByVersionFr ( version : String) : MutableList<WhatsNewToReturn>
    {
        val foundObjs = repo.getByVersionFr(version)
        var objectsToReturn :MutableList<WhatsNewToReturn> = ArrayList<WhatsNewToReturn>()
        foundObjs.forEach {
            objectsToReturn.add(it.toWhatsNewToReturn())
        }
        return objectsToReturn
    }

    fun addNewWhatsNewFr ( whatsNewOb : WhatsNew) : Unit{
        return repo.addWhatNewFr(whatsNewOb)
    }

    fun getAllWhatsNewFr () :MutableList<WhatsNewToReturn>
    {
        val foundObjs = repo.getAllWhatsNewFr()
        var objectsToReturn :MutableList<WhatsNewToReturn> = ArrayList<WhatsNewToReturn>()
        foundObjs.forEach {
            objectsToReturn.add(it.toWhatsNewToReturn())
        }
        return objectsToReturn
    }

    fun getWhatsNewEn (id :String) : WhatsNew?
    {
        return repo.findByIdEn(ObjectId(id).toId())
    }

    fun updateWhatsNewEn (patchedWhatsNewEn : WhatsNewPatch, id : String) : UpdateResult
    {
        var whatsNewToUpdate = this.getWhatsNewEn(id)
        whatsNewToUpdate ?:throw NotFoundException("Bad id")
        whatsNewToUpdate.patchWhatsNew(patchedWhatsNewEn)
        return repo.updateWhatsNewEn(whatsNewToUpdate)
    }

    fun getWhatsNewFr (id :String) : WhatsNew?
    {
        return repo.findByIdFr(ObjectId(id).toId())
    }


    fun updateWhatsNewFr (patchedWhatsNewFr : WhatsNewPatch, id : String) : UpdateResult
    {
        var whatsNewToUpdate = this.getWhatsNewFr(id)
        whatsNewToUpdate ?: throw NotFoundException("Bad id")
        whatsNewToUpdate.patchWhatsNew(patchedWhatsNewFr)
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

    fun getByVersionRangeEn(paddedVersionFrom: Double,paddedVersionTo: Double ): MutableList<WhatsNewToReturn> {
        val foundObjs = repo.getRangeEn(paddedVersionFrom,paddedVersionTo)
        var objectsToReturn :MutableList<WhatsNewToReturn> = ArrayList<WhatsNewToReturn>()
        foundObjs.forEach {
            objectsToReturn.add(it.toWhatsNewToReturn())
        }
        return objectsToReturn
    }

    fun getByVersionRangeFr(paddedVersionFrom: Double , paddedVersionTo: Double): MutableList<WhatsNewToReturn> {
        val foundObjs = repo.getRangeFr(paddedVersionFrom,paddedVersionTo)
        var objectsToReturn :MutableList<WhatsNewToReturn> = ArrayList<WhatsNewToReturn>()
        foundObjs.forEach {
            objectsToReturn.add(it.toWhatsNewToReturn())
        }
        return objectsToReturn
    }
}