package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository

class PitanjeAnketaViewModel {

    fun getPitanja(nazivAnkete: String, nazivIstrazivanja: String): List<Pitanje> {
        return PitanjeAnketaRepository.getPitanja(nazivAnkete, nazivIstrazivanja)
    }
}