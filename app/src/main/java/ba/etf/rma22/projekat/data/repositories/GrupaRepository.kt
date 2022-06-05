package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Grupa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GrupaRepository { //1

    /*fun getGroupsByIstrazivanje(nazivIstrazivanja: String): List<Grupa> {
        val groupsByIstrazivanje: ArrayList<Grupa> = arrayListOf()
        val groups: List<Grupa> = groups()
        for(grupa: Grupa in groups) {
            if(grupa.nazivIstrazivanja == nazivIstrazivanja)
                groupsByIstrazivanje.add(grupa)
        }
        return groupsByIstrazivanje
    }*/

    suspend fun getGroupsByIstrazivanje(nazivIstrazivanja: String): List<Grupa>? {  //mozda ne treba
        return withContext(Dispatchers.IO) {
            val istrazivanje = IstrazivanjeIGrupaRepository.getIstrazivanja()!!.find { istrazivanje -> istrazivanje.naziv == nazivIstrazivanja }
            val response = ApiAdapter.retrofit.getResearcheById(istrazivanje!!.id)
            val istrazivanje1 = response.body()
            //return@withContext response.body()
            //if(response.body().isNullOrEmpty()) return@withContext null
            //else return@withContext response.body()
            val grupe = IstrazivanjeIGrupaRepository.getGrupe()
            grupe!!.filter { grupa -> grupa.nazivIstrazivanja == istrazivanje1!!.naziv }
            return@withContext grupe
        }
    }
}