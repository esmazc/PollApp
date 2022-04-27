package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Korisnik
import ba.etf.rma22.projekat.data.repositories.KorisnikRepository

class KorisnikViewModel {

    fun getUser(): Korisnik {
        return KorisnikRepository.getUser()
    }
}