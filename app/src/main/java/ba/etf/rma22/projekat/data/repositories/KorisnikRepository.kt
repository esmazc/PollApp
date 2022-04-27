package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Korisnik

object KorisnikRepository {
    private val korisnik: Korisnik = Korisnik(arrayListOf(Pair("Istraživanje broj 1", "Grupa2"), Pair("Istraživanje broj 4", "Grupa1")))

    fun getUser(): Korisnik {
        return korisnik
    }
}