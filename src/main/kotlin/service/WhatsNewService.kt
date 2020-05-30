package ca.etsmtl.applets.notre_dame.whatsnew.service

import ca.etsmtl.applets.notre_dame.whatsnew.model.WhatsNew
import ca.etsmtl.applets.notre_dame.whatsnew.model.WhatsNewPatch
import ca.etsmtl.applets.notre_dame.whatsnew.model.WhatsNewToReturn
import ca.etsmtl.applets.notre_dame.whatsnew.repository.WhatsNewRepo
import com.google.cloud.firestore.WriteResult
import io.ktor.features.NotFoundException
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
class WhatsNewService(val repo: WhatsNewRepo) {

    fun getByVersionEn(version: String): MutableList<WhatsNewToReturn> {
        val foundObjs = repo.getByVersionEn(version)
        var objectsToReturn: MutableList<WhatsNewToReturn> = ArrayList<WhatsNewToReturn>()
        foundObjs.forEach {
            objectsToReturn.add(it.toWhatsNewToReturn())
        }
        return objectsToReturn
    }

    fun addNewWhatsNewEn(whatsNewOb: WhatsNew): Unit {
        return repo.addWhatNewEn(whatsNewOb)
    }

    fun getAllWhatsNewEn(): MutableList<WhatsNew> {
        val foundObjs = repo.getAllWhatsNew("en")
        var objectsToReturn: MutableList<WhatsNewToReturn> = ArrayList<WhatsNewToReturn>()
        foundObjs.forEach {
            objectsToReturn.add(it.toWhatsNewToReturn())
        }
        return foundObjs
    }

    fun getByVersionFr(version: String): MutableList<WhatsNewToReturn> {
        val foundObjs = repo.getByVersionFr(version)
        var objectsToReturn: MutableList<WhatsNewToReturn> = ArrayList<WhatsNewToReturn>()
        foundObjs.forEach {
            objectsToReturn.add(it.toWhatsNewToReturn())
        }
        return objectsToReturn
    }

    fun addNewWhatsNewFr(whatsNewOb: WhatsNew): Unit {
        return repo.addWhatNewFr(whatsNewOb)
    }

    fun getAllWhatsNewFr(): MutableList<WhatsNewToReturn> {
        val foundObjs = repo.getAllWhatsNew("fr")
        var objectsToReturn: MutableList<WhatsNewToReturn> = ArrayList<WhatsNewToReturn>()
        foundObjs.forEach {
            objectsToReturn.add(it.toWhatsNewToReturn())
        }
        return objectsToReturn
    }

    private fun getWhatsNewEn(id: String): WhatsNew? {
        return repo.findByIdEn(id)
    }

    fun updateWhatsNewEn(patchedWhatsNewEn: WhatsNewPatch, id: String): WriteResult {
        var whatsNewToUpdate = this.getWhatsNewEn(id)
        whatsNewToUpdate ?: throw NotFoundException("Bad id")
        whatsNewToUpdate.patchWhatsNew(patchedWhatsNewEn)
        return repo.updateWhatsNewEn(whatsNewToUpdate)
    }

    private fun getWhatsNewFr(id: String): WhatsNew? {
        return repo.findByIdFr(id)
    }

    fun updateWhatsNewFr(patchedWhatsNewFr: WhatsNewPatch, id: String): WriteResult {
        var whatsNewToUpdate = this.getWhatsNewFr(id)
        whatsNewToUpdate ?: throw NotFoundException("Bad id")
        whatsNewToUpdate.patchWhatsNew(patchedWhatsNewFr)
        return repo.updateWhatsNewFr(whatsNewToUpdate)
    }

    fun deleteWhatNewEn(id: String): WriteResult {
        return repo.deleteWhatsNewEn(id)
    }

    fun deleteWhatNewFr(id: String): WriteResult {
        return repo.deleteWhatsNewFr(id)
    }

    fun getByVersionRangeEn(paddedVersionFrom: Long, paddedVersionTo: Long): List<WhatsNew> {
        val foundObjs = repo.getRange("en",paddedVersionFrom, paddedVersionTo)
        var objectsToReturn: MutableList<WhatsNewToReturn> = ArrayList<WhatsNewToReturn>()
        foundObjs.forEach {
            objectsToReturn.add(it.toWhatsNewToReturn())
        }
        return foundObjs
    }

    fun getByVersionRangeFr(paddedVersionFrom: Long, paddedVersionTo: Long): MutableList<WhatsNewToReturn> {
        val foundObjs = repo.getRange("fr",paddedVersionFrom, paddedVersionTo)
        var objectsToReturn: MutableList<WhatsNewToReturn> = ArrayList<WhatsNewToReturn>()
        foundObjs.forEach {
            objectsToReturn.add(it.toWhatsNewToReturn())
        }
        return objectsToReturn
    }
}
