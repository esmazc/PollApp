package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.AnketaTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

object TakeAnketaRepository {  //1
    private val hash = AccountRepository.acHash

    suspend fun zapocniAnketu(idAnkete: Int): AnketaTaken? {
        return withContext(Dispatchers.IO) {
            /*val account = AccountRepository.getAccount(hash)
            val anketaTaken = AnketaTaken(idAnkete, account.email, 0f, Date())
            val response = ApiAdapter.retrofit.startPoll(hash, idAnkete, anketaTaken)
            if (response.isSuccessful) return@withContext response.body()
            return@withContext null*/
            val anketaTakens = ApiAdapter.retrofit.getActivePolls(hash).body()
            val anketaTaken = anketaTakens!!.find { anketaTaken -> anketaTaken.AnketumId == idAnkete }
            val account = AccountRepository.getAccount(hash)
            if(anketaTaken == null) {
                val response = ApiAdapter.retrofit.startPoll(hash, idAnkete/*, AnketaTaken(0, "Khm", 0f, Date(), idAnkete)*/)
                return@withContext response.body()
            }
            return@withContext anketaTaken
            //else {
                //anketaTaken.datumRada = Date()
                //return@withContext anketaTaken
                //val response = ApiAdapter.retrofit.startPoll(hash, idAnkete, AnketaTaken(6, account.email, anketaTaken.progres, Date(), idAnkete))
                //println(response.message() + "fksop")
                //return@withContext response.body()
            //}
//            val anketaTakens = ApiAdapter.retrofit.getActivePolls(hash).body()
//            val anketaTaken = anketaTakens!!.find { anketaTaken -> anketaTaken.id == idAnkete }
//            if(anketaTaken == null) {
//                val account = AccountRepository.getAccount(hash)
//                val response = ApiAdapter.retrofit.startPoll(hash, idAnkete, AnketaTaken(idAnkete, account.email, 0f, Date()))
//                return@withContext response.body()
//            }
//            else {
//                anketaTaken.datumRada = Date()
//                return@withContext anketaTaken
//            }
        }
//        return withContext(Dispatchers.IO) {
//            /*val response = ApiAdapter.retrofit.startPoll(hash, idAnkete)
//            val anketaTaken = response.body()
//            //anketaTaken!!.idAnketa = idAnkete
//            if (response.isSuccessful) return@withContext anketaTaken
//            return@withContext null
//            */
//            val anketaTakens = ApiAdapter.retrofit.getActivePolls(hash).body()
//            val anketaTaken = anketaTakens!!.find { anketaTaken -> anketaTaken.id == idAnkete }
//            if(anketaTaken == null) {
//                val response = ApiAdapter.retrofit.startPoll(hash, idAnkete)
//                return@withContext response.body()
//            }
//            else {
//                anketaTaken.datumRada = Date()
//                return@withContext anketaTaken
//            }
//        }
    }

    suspend fun getPoceteAnkete(): List<AnketaTaken>? {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getActivePolls(hash)
            //return@withContext response.body()
            if(response.body().isNullOrEmpty()) return@withContext null
            else return@withContext response.body()
        }
    }

    suspend fun getAnketaTaken(idAnketa: Int): AnketaTaken? {
        return withContext(Dispatchers.IO) {
            val aktivne = ApiAdapter.retrofit.getActivePolls(hash).body()
            //println(aktivne?.size ?: -1)
            //aktivne!!.forEach { aktivna -> println(aktivna.id) }
            if(aktivne!!.isEmpty()) return@withContext null
            return@withContext aktivne.find { aktivna -> aktivna.AnketumId == idAnketa }
        }
    }

    suspend fun getAnketaTaken1(idAnketaTaken: Int): AnketaTaken? {
        return withContext(Dispatchers.IO) {
            val aktivne = ApiAdapter.retrofit.getActivePolls(hash).body()
            //println(aktivne?.size ?: -1)
            //aktivne!!.forEach { aktivna -> println(aktivna.id) }
            if(aktivne!!.isEmpty()) return@withContext null
            return@withContext aktivne.find { aktivna -> aktivna.id == idAnketaTaken }
        }
    }
}