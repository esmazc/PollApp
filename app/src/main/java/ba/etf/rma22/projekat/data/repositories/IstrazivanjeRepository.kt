package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Istrazivanje

object IstrazivanjeRepository {  //1
    //private val korisnik = KorisnikRepository.getUser()

    suspend fun getIstrazivanjeByGodina(godina: Int) : List<Istrazivanje> {
        /*val istrazivanjaByGodina: ArrayList<Istrazivanje> = arrayListOf()
        val researches: List<Istrazivanje> = researches()
        for(istrazivanje: Istrazivanje in researches) {
            if(istrazivanje.godina == godina)
                istrazivanjaByGodina.add(istrazivanje)
        }
        return istrazivanjaByGodina*/
        val istrazivanja = IstrazivanjeIGrupaRepository.getIstrazivanja()
        return istrazivanja!!.filter { istrazivanje -> istrazivanje.godina == godina }
    }

    /*fun getAll() : List<Istrazivanje> {
        return researches()
    }*/

    suspend fun getUpisani() : List<Istrazivanje> {
        /*val upisani: ArrayList<Istrazivanje> = arrayListOf()
        val researches: List<Istrazivanje> = researches()
        val istrazivanja: ArrayList<String> = arrayListOf()
        for(par: Pair<String, String> in korisnik.parovi) {
            istrazivanja.add(par.first)
        }
        for(istrazivanje: Istrazivanje in researches) {
            if(istrazivanja.contains(istrazivanje.naziv))
                upisani.add(istrazivanje)
        }
        return upisani*/
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