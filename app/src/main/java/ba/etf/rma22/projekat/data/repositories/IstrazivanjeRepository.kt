package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.researches

object IstrazivanjeRepository {
    private val korisnik = KorisnikRepository.korisnik

    fun getIstrazivanjeByGodina(godina:Int) : List<Istrazivanje> {
        val istrazivanjaByGodina: ArrayList<Istrazivanje> = arrayListOf()
        val researches: List<Istrazivanje> = researches()
        for(istrazivanje: Istrazivanje in researches) {
            if(istrazivanje.godina == godina)
                istrazivanjaByGodina.add(istrazivanje)
        }
        return istrazivanjaByGodina
    }

    fun getAll() : List<Istrazivanje> {
        return researches()
    }

    fun getUpisani() : List<Istrazivanje> {
        val upisani: ArrayList<Istrazivanje> = arrayListOf()
        val researches: List<Istrazivanje> = researches()
        val istrazivanja: ArrayList<String> = arrayListOf()
        for(par: Pair<String, String> in korisnik.parovi) {
            istrazivanja.add(par.first)
        }
        for(istrazivanje: Istrazivanje in researches) {
            if(istrazivanja.contains(istrazivanje.naziv))
                upisani.add(istrazivanje)
        }
        return upisani
    }
}