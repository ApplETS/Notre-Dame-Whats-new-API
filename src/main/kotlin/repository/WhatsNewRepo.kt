package ca.etsmtl.applets.notre_dame.whatsnew.repository

import ca.etsmtl.applets.notre_dame.whatsnew.model.WhatsNew
import ca.etsmtl.applets.notre_dame.whatsnew.utils.Property
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.WriteResult
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
class WhatsNewRepo(private val client: Firestore) {
    private val whatsNewCollectionEn = client.collection(Property["db.whatsNewCollectionEn"])
    private val whatsNewCollectionFr = client.collection(Property["db.whatsNewCollectionFr"])

    fun getByVersionEn(version: String): MutableList<WhatsNew> {
        val future = whatsNewCollectionEn.whereEqualTo("version", version).get()

        return future.get().documents.map { doc ->
            WhatsNew(doc.id, doc.data)
        }.toMutableList();
    }

    fun addWhatNewEn(whatsNewOb: WhatsNew): Unit {
        whatsNewCollectionEn.add(whatsNewOb)
    }

    fun getAllWhatsNew(lang: String):MutableList<WhatsNew>{
        var whatsNewCollection = if( lang =="en"){
            whatsNewCollectionEn
        }
        else {
            whatsNewCollectionFr
        }
        return whatsNewCollection.listDocuments().map { doc ->
            val future = doc.get()
            val data = future.get().data

            WhatsNew(
                    doc.id,
                    data?.get("title").toString(),
                    data?.get("description").toString(),
                    data?.get("version").toString(),
                    data?.get("paddedVersion").toString().toLong()
            )
        }.toMutableList()
    }

    fun findByIdEn(id: String): WhatsNew? {
        val future = whatsNewCollectionEn.document(id)
        return findDoc(future)
    }

    private fun findDoc ( future : DocumentReference): WhatsNew?{
        val doc = future.get().get()

        return if (doc.exists())
            doc.data?.let {
                WhatsNew(
                        doc.id, it
                )
            }
        else
            null
    }

    fun getByVersionFr(version: String): MutableList<WhatsNew> {
        val future = whatsNewCollectionFr.whereEqualTo("version", version).get()

        return future.get().documents.map { doc ->
            WhatsNew(doc.id, doc.data)
        }.toMutableList();
    }

    fun addWhatNewFr(whatsNewOb: WhatsNew): Unit {
        whatsNewCollectionFr.add(whatsNewOb)
    }

    fun findByIdFr(id: String): WhatsNew? {
        val future = whatsNewCollectionFr.document(id)
        return findDoc(future)
    }

    fun updateWhatsNewEn(updatedWhatsNew: WhatsNew): WriteResult {
        return whatsNewCollectionEn.document(updatedWhatsNew._id).update(updatedWhatsNew.toMapUpdate()).get()
    }

    fun updateWhatsNewFr(updatedWhatsNew: WhatsNew): WriteResult {
        return whatsNewCollectionFr.document(updatedWhatsNew._id).update(updatedWhatsNew.toMapUpdate()).get()
    }

    fun deleteWhatsNewEn(id: String): WriteResult {
        return whatsNewCollectionEn.document(id).delete().get()
    }

    fun deleteWhatsNewFr(id: String): WriteResult {
        return whatsNewCollectionFr.document(id).delete().get()
    }

    fun getRange ( lang:String, from: Long, to: Long): List<WhatsNew>{
        var whatsNewCollection = if( lang =="en"){
            whatsNewCollectionEn
        }
        else {
            whatsNewCollectionFr
        }
        val future = whatsNewCollection.whereGreaterThanOrEqualTo("paddedVersion", from)
                .whereLessThanOrEqualTo("paddedVersion", to).get()

        return future.get().documents.filter { (it.data["paddedVersion"].toString().toLong()).rem(10.0) == 0.0 }.map { doc ->
            WhatsNew(doc.id, doc.data)}
    }
}
