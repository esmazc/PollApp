package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


object IstrazivanjeIGrupaRepository {
    private val hash = AccountRepository.acHash

    suspend fun getIstrazivanja(): List<Istrazivanje> {
        val istrazivanja = arrayListOf<Istrazivanje>()
        var i = 0
        return withContext(Dispatchers.IO) {
            while(true) {
                i++
                val response = ApiAdapter.retrofit.getResearches(i)
                val researches = response.body()
                if(researches!!.isEmpty() && i == 0) return@withContext listOf()
                if(researches.isEmpty() && i != 0) break
                istrazivanja.addAll(researches)
            }
            return@withContext istrazivanja
        }
    }

    suspend fun getIstrazivanja(offset: Int): List<Istrazivanje> {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getResearches(offset)
            if(response.body().isNullOrEmpty()) return@withContext listOf()
            return@withContext response.body()!!
        }
    }

    suspend fun getGrupe(): List<Grupa> {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getGroups()
            if(response.body().isNullOrEmpty()) return@withContext listOf()
            return@withContext response.body()!!
        }
    }

    suspend fun getGrupeZaIstrazivanje(idIstrazivanja: Int): List<Grupa> {
        return withContext(Dispatchers.IO) {
            val grupe = getGrupe()
            val grupeZaIstrazivanje = arrayListOf<Grupa>()
            for(i in grupe) {
                val istr = ApiAdapter.retrofit.getResearcheByGroup(i.id)
                if(istr.body()!!.id == idIstrazivanja)
                    grupeZaIstrazivanje.add(i)
            }
            return@withContext grupeZaIstrazivanje
        }
    }

    suspend fun upisiUGrupu(idGrupa: Int): Boolean {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.enrollGroup(idGrupa, hash).body()
            //if(response!!.message != "OK") return@withContext false
            //return@withContext true
            return@withContext !response!!.message.contains("Grupa not found")
        }
    }

    suspend fun getUpisaneGrupe(): List<Grupa> {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getEnrolledGroups(hash)
            if(response.body().isNullOrEmpty()) return@withContext listOf()
            return@withContext response.body()!!
        }
    }

    suspend fun getIstrazivanjeZaGrupu(idGrupa: Int): Istrazivanje {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getResearcheByGroup(idGrupa)
            return@withContext response.body()!!
        }
    }
}