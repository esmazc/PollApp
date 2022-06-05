package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


object IstrazivanjeIGrupaRepository {  //1
    private val hash = AccountRepository.acHash

    suspend fun getIstrazivanja(): List<Istrazivanje>? {
        val istrazivanja = arrayListOf<Istrazivanje>()
        var i = 0
        return withContext(Dispatchers.IO) {
            while(true) {
                i++
                val response = ApiAdapter.retrofit.getResearches(i)
                val researches = response.body()
                if(researches!!.isEmpty() && i == 0) return@withContext null
                if(researches.isEmpty() && i != 0) break
                istrazivanja.addAll(researches)
            }
            return@withContext istrazivanja
        }
    }

    suspend fun getIstrazivanja(offset: Int): List<Istrazivanje>? {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getResearches(offset)
            //return@withContext response.body()
            if(response.body().isNullOrEmpty()) return@withContext null
            else return@withContext response.body()
        }
    }

    suspend fun getGrupe(): List<Grupa>? {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getGroups()
            //return@withContext response.body()
            if(response.body().isNullOrEmpty()) return@withContext null
            else return@withContext response.body()
        }
    }

    suspend fun getGrupeZaIstrazivanje(idIstrazivanja: Int): List<Grupa>? {  //???
        return withContext(Dispatchers.IO) {
            //val response = ApiAdapter.retrofit.getResearcheById(idIstrazivanja)
            //val istrazivanje = response.body()
            //return@withContext response.body()
            //if(response.body().isNullOrEmpty()) return@withContext null
            //else return@withContext response.body()
            val grupe = getGrupe()
            val grupeZaIstrazivanje = arrayListOf<Grupa>()
            for(i in grupe!!) {
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
            return@withContext !response!!.message.contains("Grupa not found")
            /*val upisanaIstrazivanja = IstrazivanjeRepository.getUpisani()
            for(i in upisanaIstrazivanja) {
                val grupeZaIstrazivanje = getGrupeZaIstrazivanje(i.id)
                if(grupeZaIstrazivanje!!.contains(Grupa(idGrupa, "")))
                    return@withContext false
            }
            val response = ApiAdapter.retrofit.enrollGroup(idGrupa, hash)
            return@withContext !response.body()!!.contains("Grupa not found")*/
        }
    }

    suspend fun getUpisaneGrupe(): List<Grupa> {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getEnrolledGroups(hash)
            //return@withContext response.body()
            if(response.body().isNullOrEmpty()) return@withContext listOf()
            else return@withContext response.body()!!
        }
    }

    suspend fun getIstrazivanjeZaGrupu(idGrupa: Int): Istrazivanje {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getResearcheByGroup(idGrupa)
            return@withContext response.body()!!
        }
    }
}