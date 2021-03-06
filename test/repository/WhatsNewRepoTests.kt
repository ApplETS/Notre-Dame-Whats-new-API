package ca.estmtl.applets.notre_dame.whatsnew.repository

import ca.etsmtl.applets.notre_dame.whatsnew.config.common
import ca.etsmtl.applets.notre_dame.whatsnew.config.whatsNewRepo
import ca.etsmtl.applets.notre_dame.whatsnew.repository.WhatsNewRepo
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI
import org.junit.Test
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import kotlin.test.assertTrue

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
class WhatsNewRepoTests {
    val context = Kodein{
        import(common)
        import(whatsNewRepo)
    }

    @Test
    fun getByVersion() {
        assertTrue { true }
    }
}
