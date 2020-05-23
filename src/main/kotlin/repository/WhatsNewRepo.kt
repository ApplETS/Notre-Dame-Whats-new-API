package ca.etsmtl.applets.notre_dame.whatsnew.repository

import ca.etsmtl.applets.notre_dame.whatsnew.model.WhatsNew
import ca.etsmtl.applets.notre_dame.whatsnew.utils.Property
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
            WhatsNew(
                doc.id, doc.data["title"].toString(),
                doc.data["description"].toString(),
                doc.data["version"].toString(),
                doc.data["paddedVersion"] as Long
            )
        }.toMutableList();
    }

    fun addWhatNewEn(whatnewOb: WhatsNew): Unit {
        whatsNewCollectionEn.add(whatnewOb)
    }

    fun getAllWhatsNewEn(): MutableList<WhatsNew> {
        return whatsNewCollectionEn.listDocuments().map { doc ->
            val future = doc.get()
            val data = future.get().data

            WhatsNew(
                doc.id,
                data?.get("title").toString(),
                data?.get("description").toString(),
                data?.get("version").toString(),
                data?.get("paddedVersion") as Long
            )
        }.toMutableList()
    }

    fun findByIdEn(id: String): WhatsNew? {
        val future = whatsNewCollectionEn.document(id)

        val doc = future.get().get()

        return if (doc.exists())
            WhatsNew(
                doc.id, doc.data?.get("title").toString(),
                doc.data?.get("description").toString(),
                doc.data?.get("version").toString(),
                doc.data?.get("paddedVersion") as Long
            )
        else
            null
    }

    fun getByVersionFr(version: String): MutableList<WhatsNew> {
        val future = whatsNewCollectionFr.whereEqualTo("version", version).get()

        return future.get().documents.map { doc ->
            WhatsNew(
                doc.id, doc.data["title"].toString(),
                doc.data["description"].toString(),
                doc.data["version"].toString(),
                doc.data["paddedVersion"] as Long
            )
        }.toMutableList();
    }

    fun addWhatNewFr(whatnewOb: WhatsNew): Unit {
        whatsNewCollectionFr.add(whatnewOb)
    }

    fun getAllWhatsNewFr(): MutableList<WhatsNew> {
        return whatsNewCollectionFr.listDocuments().map { doc ->
            val future = doc.get()
            val data = future.get().data

            WhatsNew(
                doc.id,
                data?.get("title").toString(),
                data?.get("description").toString(),
                data?.get("version").toString(),
                data?.get("paddedVersion") as Long
            )
        }.toMutableList()
    }

    fun findByIdFr(id: String): WhatsNew? {
        val future = whatsNewCollectionFr.document(id)

        val doc = future.get().get()

        return if (doc.exists())
            WhatsNew(
                doc.id, doc.data?.get("title").toString(),
                doc.data?.get("description").toString(),
                doc.data?.get("version").toString(),
                doc.data?.get("paddedVersion") as Long
            )
        else
            null
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

    fun getRangeEn(from: Long, to: Long): List<WhatsNew> {
        print("there")
        val future = whatsNewCollectionEn.whereGreaterThanOrEqualTo("paddedVersion", from)
            .whereLessThanOrEqualTo("paddedVersion", to).get()

        return future.get().documents.filter { (it.data["paddedVersion"] as Long).rem(10.0) == 0.0 }.map { doc ->
            WhatsNew(
                doc.id,
                doc.data["title"].toString(),
                doc.data["description"].toString(),
                doc.data["version"].toString(),
                doc.data["paddedVersion"] as Long
            )
        };
    }

    fun getRangeFr(from: Long, to: Long): List<WhatsNew> {
        val future = whatsNewCollectionFr.whereGreaterThanOrEqualTo("paddedVersion", from)
            .whereLessThanOrEqualTo("paddedVersion", to).get()

        return future.get().documents.filter { (it.data["paddedVersion"] as Long).rem(10.0) == 0.0 }.map { doc ->
            WhatsNew(
                doc.id, doc.data["title"].toString(),
                doc.data["description"].toString(),
                doc.data["version"].toString(),
                doc.data["paddedVersion"] as Long
            )
        };
    }

}
