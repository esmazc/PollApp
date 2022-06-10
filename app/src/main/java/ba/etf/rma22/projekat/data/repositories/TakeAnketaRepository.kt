package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.AnketaTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object TakeAnketaRepository {
    private val hash = AccountRepository.acHash

    suspend fun zapocniAnketu(idAnkete: Int): AnketaTaken? {
        return withContext(Dispatchers.IO) {
            val anketaTakens = ApiAdapter.retrofit.getActivePolls(hash).body()
            val anketaTaken = anketaTakens!!.find { anketaTaken -> anketaTaken.AnketumId == idAnkete }
            if(anketaTaken == null) {
                val response = ApiAdapter.retrofit.startPoll(hash, idAnkete)
                return@withContext response.body()
            }
            return@withContext anketaTaken
        }
    }

    suspend fun getPoceteAnkete(): List<AnketaTaken>? {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getActivePolls(hash)
            if(response.body().isNullOrEmpty()) return@withContext null
            return@withContext response.body()
        }
    }

    suspend fun getAnketaTaken(idAnketa: Int): AnketaTaken? {
        return withContext(Dispatchers.IO) {
            val aktivne = ApiAdapter.retrofit.getActivePolls(hash).body()
            if(aktivne!!.isEmpty()) return@withContext null
            return@withContext aktivne.find { aktivna -> aktivna.AnketumId == idAnketa }
        }
    }

    suspend fun getAnketaTaken1(idAnketaTaken: Int): AnketaTaken? {
        return withContext(Dispatchers.IO) {
            val aktivne = ApiAdapter.retrofit.getActivePolls(hash).body()
            if(aktivne!!.isEmpty()) return@withContext null
            return@withContext aktivne.find { aktivna -> aktivna.id == idAnketaTaken }
        }
    }
}