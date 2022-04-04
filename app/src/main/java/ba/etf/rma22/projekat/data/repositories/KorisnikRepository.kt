package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Korisnik

object KorisnikRepository {
    //val korisnik : Korisnik = Korisnik(arrayListOf("Istraživanje broj 1"), arrayListOf("Grupa1", "Grupa2"))
    val korisnik : Korisnik = Korisnik(arrayListOf(Pair("Istraživanje broj 1", "Grupa1"), Pair("Istraživanje broj 1", "Grupa2"),
        Pair("Istraživanje broj 5", "Grupa1"), Pair("Istraživanje broj 5", "Grupa2")))

}