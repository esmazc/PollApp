package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Korisnik
import ba.etf.rma22.projekat.data.user

object KorisnikRepository {

    fun getUser(): Korisnik {
        return user()
    }
}