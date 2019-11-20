package ca.etsmtl.applets.notre_dame.model

import org.litote.kmongo.Id
import org.litote.kmongo.newId

data class WhatsNewPatch(
    val _id : Id<WhatsNew> = newId(),
    var title : String?,
    var description : String?)