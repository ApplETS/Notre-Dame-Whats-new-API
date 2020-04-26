package ca.etsmtl.applets.notre_dame.repository

import ca.etsmtl.applets.notre_dame.model.WhatsNew
import ca.etsmtl.applets.notre_dame.utils.Property
import com.mongodb.MongoClient
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI
import org.litote.kmongo.*

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
class WhatsNewRepo (private val client: MongoClient){
      private val databaseName  = Property["db.name"]
      private val whatsNewCollectionEn = client.getDatabase(databaseName).getCollection<WhatsNew>(Property["db.whatsNewCollectionEn"])
      private val whatsNewCollectionFr = client.getDatabase(databaseName).getCollection<WhatsNew>(Property["db.whatsNewCollectionFr"])

    fun getByVersionEn( version : String) : MutableList<WhatsNew>
    {
        return whatsNewCollectionEn.find(WhatsNew::version eq version).toMutableList()
    }

    fun addWhatNewEn ( whatnewOb : WhatsNew) :Unit
    {
        return whatsNewCollectionEn.insertOne(whatnewOb)
    }

    fun getAllWhatsNewEn() : MutableList<WhatsNew>
    {
        return whatsNewCollectionEn.find().toMutableList();
    }

    fun getByVersionFr( version : String) : MutableList<WhatsNew>
    {
        return whatsNewCollectionFr.find(WhatsNew::version eq version).toMutableList()
    }

    fun addWhatNewFr( whatnewOb : WhatsNew) :Unit
    {
        return whatsNewCollectionFr.insertOne(whatnewOb)
    }

    fun getAllWhatsNewFr() : MutableList<WhatsNew>
    {
        return whatsNewCollectionFr.find().toMutableList();
    }

    fun findByIdEn ( id : Id<WhatsNew>) : WhatsNew?
    {
        return whatsNewCollectionEn.findOne(WhatsNew::_id eq id)
    }
    fun updateWhatsNewEn ( updatedWhatsNew : WhatsNew) : UpdateResult
    {
        return whatsNewCollectionEn.updateOne(updatedWhatsNew)
    }

    fun findByIdFr ( id : Id<WhatsNew>) : WhatsNew?
    {
        return whatsNewCollectionFr.findOne(WhatsNew::_id eq id)
    }

    fun updateWhatsNewFr ( updatedWhatsNew : WhatsNew) : UpdateResult
    {
        return whatsNewCollectionFr.updateOne(updatedWhatsNew)
    }

    fun deleteWhatsNewEn ( id : Id<WhatsNew>) : DeleteResult
    {
        return whatsNewCollectionEn.deleteOne(WhatsNew ::_id eq id)
    }

    fun deleteWhatsNewFr( id : Id<WhatsNew>) : DeleteResult
    {
        return whatsNewCollectionFr.deleteOne(WhatsNew ::_id eq id)
    }

    fun getRangeEn(from: Double, to: Double): List<WhatsNew> {
     return whatsNewCollectionEn.find("{paddedVersion: {${MongoOperator.gt}:$from, ${MongoOperator.lt}:$to}}").toMutableList().filter { whatsNew ->
         whatsNew.paddedVersion.rem(10.0) ==0.0
     }
    }

    fun getRangeFr(from: Double, to: Double): List<WhatsNew> {
        return whatsNewCollectionFr.find("paddedVersion: {${MongoOperator.gt}:$from, ${MongoOperator.lt}:$to}").toMutableList().filter { whatsNew ->
            whatsNew.paddedVersion.rem(10.0) ==0.0
        }
    }

}