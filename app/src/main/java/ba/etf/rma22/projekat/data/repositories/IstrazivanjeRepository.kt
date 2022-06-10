package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Istrazivanje

object IstrazivanjeRepository {

    suspend fun getIstrazivanjeByGodina(godina: Int) : List<Istrazivanje> {
        val istrazivanja = IstrazivanjeIGrupaRepository.getIstrazivanja()
        return istrazivanja!!.filter { istrazivanje -> istrazivanje.godina == godina }
    }

    suspend fun getUpisani() : List<Istrazivanje> {
        val upisanaIstrazivanja = arrayListOf<Istrazivanje>()
        val upisaneGrupe = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
        if(upisaneGrupe != null) {
            for(i in upisaneGrupe) {
                val istr = IstrazivanjeIGrupaRepository.getIstrazivanjeZaGrupu(i.id)
                if(!upisanaIstrazivanja.contains(istr))
                    upisanaIstrazivanja.add(istr)
            }
        }
        if(upisanaIstrazivanja.isEmpty()) return listOf()
        return upisanaIstrazivanja
    }

}