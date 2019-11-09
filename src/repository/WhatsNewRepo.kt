package ca.etsmtl.applets.notre_dame.repository

import ca.etsmtl.applets.notre_dame.model.WhatsNew
import ca.etsmtl.applets.notre_dame.utils.Property
import com.mongodb.MongoClient
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
}