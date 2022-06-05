//package ba.etf.rma22.projekat.data.repositories
//
//
//import ba.etf.rma22.projekat.data.models.Anketa
//import org.junit.Assert.assertEquals
//import org.junit.Assert.assertTrue
//import org.junit.Test
//
//class AnketaRepositoryTest {
//
//    @Test
//    fun getAll() {
//        val polls = AnketaRepository.getAll()
//        assertEquals(polls.size,15)
//    }
//
//    @Test
//    fun getMyAnkete() {
//        val polls = AnketaRepository.getMyAnkete()
//        assertEquals(polls.size,5)
//        assertTrue(polls.stream().allMatch { poll -> poll.nazivIstrazivanja == "Istraživanje broj 1" || poll.nazivIstrazivanja == "Istraživanje broj 4" })
//        assertEquals(polls[0].nazivGrupe, "Grupa2")
//        assertEquals(polls[1].nazivGrupe, "Grupa2")
//        assertEquals(polls[2].nazivGrupe, "Grupa1")
//        assertEquals(polls[3].nazivGrupe, "Grupa1")
//        assertEquals(polls[4].nazivGrupe, "Grupa1")
//    }
//
//    @Test
//    fun getDone() {
//        val polls = AnketaRepository.getDone()
//        val polls1 = AnketaRepository.getMyAnkete()
//        assertEquals(polls.size,1)
//        assertTrue(polls.stream().allMatch { poll -> poll.stanje == Anketa.Stanje.DONE})
//        assertTrue(polls1.containsAll(polls))
//    }
//
//    @Test
//    fun getFuture() {
//        val polls = AnketaRepository.getFuture()
//        val polls1 = AnketaRepository.getMyAnkete()
//        assertEquals(polls.size,3)
//        assertTrue(polls.stream().allMatch { poll -> poll.stanje == Anketa.Stanje.NOTSTARTEDYET || poll.stanje == Anketa.Stanje.ACTIVE})
//        assertTrue(polls1.containsAll(polls))
//    }
//
//    @Test
//    fun getNotTaken() {
//        val polls = AnketaRepository.getNotTaken()
//        val polls1 = AnketaRepository.getMyAnkete()
//        assertEquals(polls.size,1)
//        assertTrue(polls.stream().allMatch { poll -> poll.stanje == Anketa.Stanje.ENDED})
//        assertTrue(polls1.containsAll(polls))
//    }
//}