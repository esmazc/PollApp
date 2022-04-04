package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.GrupaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeRepository

class UpisIstrazivanjeViewModel {

    fun getIstrazivanjeByGodina(godina: Int) : List<Istrazivanje> {
        return IstrazivanjeRepository.getIstrazivanjeByGodina(godina)
    }

    fun getAll() : List<Istrazivanje> {
        return IstrazivanjeRepository.getAll()
    }

    fun getUpisani() : List<Istrazivanje> {
        return IstrazivanjeRepository.getUpisani()
    }

    fun getGroupsByIstrazivanje(nazivIstrazivanja: String): List<Grupa> {
        return GrupaRepository.getGroupsByIstrazivanje(nazivIstrazivanja)
    }

}