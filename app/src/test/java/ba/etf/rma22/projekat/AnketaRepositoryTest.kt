package ba.etf.rma22.projekat


import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import org.hamcrest.CoreMatchers.`is` as Is
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AnketaRepositoryTest {

    @Test
    fun testGetAll() {
        val polls = AnketaRepository.getAll()
        assertEquals(polls.size,13)
        //assertThat(movies, hasItem<Anketa>(hasProperty("title", Is("Ratatouille"))))
        //assertThat(movies, not(hasItem<Anketa>(hasProperty("title", Is("Contratiempo")))))
    }

    @Test
    fun testGetMyAnkete() {
        val polls = AnketaRepository.getMyAnkete()
        assertEquals(polls.size,5)
        assertTrue(polls.stream().allMatch { poll -> poll.nazivIstrazivanja == "Istraživanje broj 1" || poll.nazivIstrazivanja == "Istraživanje broj 5" })
    }

    @Test
    fun testGetDone() {
        val polls = AnketaRepository.getDone()
        val polls1 = AnketaRepository.getMyAnkete()
        assertEquals(polls.size,2)
        assertTrue(polls.stream().allMatch { poll -> poll.stanje == Anketa.Stanje.DONE})
        assertTrue(polls1.containsAll(polls))
    }

    @Test
    fun testGetFuture() {
        val polls = AnketaRepository.getFuture()
        val polls1 = AnketaRepository.getMyAnkete()
        assertEquals(polls.size,2)
        assertTrue(polls.stream().allMatch { poll -> poll.stanje == Anketa.Stanje.NOTSTARTEDYET || poll.stanje == Anketa.Stanje.ACTIVE})
        assertTrue(polls1.containsAll(polls))
    }

    @Test
    fun testGetNotTaken() {
        val polls = AnketaRepository.getNotTaken()
        val polls1 = AnketaRepository.getMyAnkete()
        assertEquals(polls.size,1)
        assertTrue(polls.stream().allMatch { poll -> poll.stanje == Anketa.Stanje.ENDED})
        assertTrue(polls1.containsAll(polls))
    }
}