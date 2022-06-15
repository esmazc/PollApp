package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Grupa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GrupaRepository {

    /*suspend fun getGroupsByIstrazivanje(nazivIstrazivanja: String): List<Grupa> {
        return withContext(Dispatchers.IO) {
            val istrazivanje = IstrazivanjeIGrupaRepository.getIstrazivanja()!!.find { istrazivanje -> istrazivanje.naziv == nazivIstrazivanja }
            val response = ApiAdapter.retrofit.getResearcheById(istrazivanje!!.id)
            val istrazivanje1 = response.body()
            val grupe = IstrazivanjeIGrupaRepository.getGrupe()
            grupe!!.filter { grupa -> grupa.nazivIstrazivanja == istrazivanje1!!.naziv }
            return@withContext grupe
        }
    }*/
}