package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.data.repositories.IstrazivanjeRepository
import org.hamcrest.CoreMatchers.`is` as Is
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class IstrazivanjeRepositoryTest {

    @Test
    fun testGetIstrazivanjeByGodina() {
        val researches1 = IstrazivanjeRepository.getIstrazivanjeByGodina(1)
        assertEquals(researches1.size, 2)
        val researches2 = IstrazivanjeRepository.getIstrazivanjeByGodina(2)
        assertEquals(researches2.size, 0)
        val researches3 = IstrazivanjeRepository.getIstrazivanjeByGodina(3)
        assertEquals(researches3.size, 2)
        val researches4 = IstrazivanjeRepository.getIstrazivanjeByGodina(4)
        assertEquals(researches4.size, 1)
        val researches5 = IstrazivanjeRepository.getIstrazivanjeByGodina(5)
        assertEquals(researches5.size, 0)
    }

    @Test
    fun testGetAll() {
        val researches = IstrazivanjeRepository.getAll()
        assertEquals(researches.size, 5)
        for(i in 1..5)
            assertEquals(researches[i-1].naziv, "Istraživanje broj " + i)
    }

    @Test
    fun testGetUpisani() {
        val researches = IstrazivanjeRepository.getUpisani()
        assertEquals(researches.size, 2)
        assertTrue(researches.stream().allMatch { res -> res.naziv == "Istraživanje broj 1" || res.naziv == "Istraživanje broj 5"})
    }

}