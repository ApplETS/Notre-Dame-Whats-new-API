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
class WhatsNewRepo (val client: MongoClient){
      val databaseName  = Property["db.name"]
      val whatsNewCollNameEn =Property["db.whatsNewCollectionEn"]
      val whatsNewCollNameFr =Property["db.whatsNewCollectionFr"]
      val whatsNewCollectionEn = client.getDatabase(databaseName).getCollection<WhatsNew>(whatsNewCollNameEn)
      val whatsNewCollectionFr = client.getDatabase(databaseName).getCollection<WhatsNew>(whatsNewCollNameFr)

    fun getByVersionEn( version : Float) : MutableList<WhatsNew>
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

    fun getByVersionFr( version : Float) : MutableList<WhatsNew>
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
}