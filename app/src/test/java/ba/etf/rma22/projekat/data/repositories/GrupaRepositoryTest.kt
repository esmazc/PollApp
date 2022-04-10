package ba.etf.rma22.projekat.data.repositories

import org.junit.Assert.assertEquals
import org.junit.Test

class GrupaRepositoryTest {

    @Test
    fun getGroupsByIstrazivanje() {
        val groups1 = GrupaRepository.getGroupsByIstrazivanje("Istraživanje broj 1")
        assertEquals(groups1.size, 2)
        val groups2 = GrupaRepository.getGroupsByIstrazivanje("Istraživanje broj 2")
        assertEquals(groups2.size, 2)
        val groups3 = GrupaRepository.getGroupsByIstrazivanje("Istraživanje broj 3")
        assertEquals(groups3.size, 2)
        val groups4 = GrupaRepository.getGroupsByIstrazivanje("Istraživanje broj 4")
        assertEquals(groups4.size, 3)
        val groups5 = GrupaRepository.getGroupsByIstrazivanje("Istraživanje broj 5")
        assertEquals(groups5.size, 2)
    }
}