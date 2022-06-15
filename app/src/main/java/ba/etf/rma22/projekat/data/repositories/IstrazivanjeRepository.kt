package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import ba.etf.rma22.projekat.data.models.Istrazivanje

object IstrazivanjeRepository {

    suspend fun getIstrazivanjeByGodina(context: Context, godina: Int) : List<Istrazivanje> {
        val istrazivanja = IstrazivanjeIGrupaRepository.getIstrazivanja(context)
        return istrazivanja.filter { istrazivanje -> istrazivanje.godina == godina }
    }

    suspend fun getIstrazivanjeByGodina(godina: Int) : List<Istrazivanje> {
        var istrazivanja = IstrazivanjeIGrupaRepository.getIstrazivanja()
        istrazivanja = istrazivanja.filter { istrazivanje -> istrazivanje.godina == godina }
        return istrazivanja
    }

    suspend fun getUpisani(context: Context) : List<Istrazivanje> {
        val upisanaIstrazivanja = arrayListOf<Istrazivanje>()
        val upisaneGrupe = IstrazivanjeIGrupaRepository.getUpisaneGrupe(context)
        if(upisaneGrupe != null) {
            for(i in upisaneGrupe) {
                val istr = IstrazivanjeIGrupaRepository.getIstrazivanjeZaGrupu(context, i.id)
                if(!upisanaIstrazivanja.contains(istr))
                    upisanaIstrazivanja.add(istr)
            }
        }
        if(upisanaIstrazivanja.isEmpty()) return listOf()
        return upisanaIstrazivanja
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