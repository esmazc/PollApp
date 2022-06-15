package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.util.Log
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.AnketaTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object TakeAnketaRepository {
    private val hash = AccountRepository.acHash

    suspend fun writeAnketaTaken(context: Context, anketaTaken: AnketaTaken) : String?{
        return withContext(Dispatchers.IO) {
            try{
                val db = AppDatabase.getInstance(context)
                db.anketaTakenDao().insert(anketaTaken)
                return@withContext "success"
            }
            catch(error:Exception){
                return@withContext null
            }
        }
    }

    suspend fun writeAnketaTakens(context: Context) : String?{
        return withContext(Dispatchers.IO) {
            try{
                val anketaTakens = ApiAdapter.retrofit.getActivePolls(hash).body()
                val db = AppDatabase.getInstance(context)
                db.anketaTakenDao().delete()
                if(anketaTakens != null)
                    for(anketaTaken in anketaTakens)
                        db.anketaTakenDao().insert(anketaTaken)
                return@withContext "success"
            }
            catch(error:Exception){
                return@withContext null
            }
        }
    }

    suspend fun zapocniAnketu(idAnkete: Int): AnketaTaken? {
        return withContext(Dispatchers.IO) {
            val anketaTakens = ApiAdapter.retrofit.getActivePolls(hash).body()
            /*val db = AppDatabase.getInstance(context)
            if(anketaTakens != null)
                for(anketaTaken in anketaTakens)
                    db.anketaTakenDao().insert(anketaTaken)*/
            val anketaTaken = anketaTakens!!.find { anketaTaken -> anketaTaken.AnketumId == idAnkete }
            if(anketaTaken == null) {
                val response = ApiAdapter.retrofit.startPoll(hash, idAnkete)
                if(response.message() != "OK") return@withContext null
                return@withContext response.body()
            }
            return@withContext anketaTaken
        }
    }

    suspend fun getPoceteAnkete(): List<AnketaTaken>? {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getActivePolls(hash)
            if(response.body().isNullOrEmpty() || response.message() != "OK") return@withContext null
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

    suspend fun getAnketaTaken(context: Context, idAnketa: Int): AnketaTaken? {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            val anketaTaken = db.anketaTakenDao().getForPoll(idAnketa)
            return@withContext anketaTaken
        }
    }

    suspend fun getAnketaTaken1(idAnketaTaken: Int): AnketaTaken? {
        return withContext(Dispatchers.IO) {
            val aktivne = ApiAdapter.retrofit.getActivePolls(hash).body()
            if(aktivne!!.isEmpty()) return@withContext null
            return@withContext aktivne.find { aktivna -> aktivna.id == idAnketaTaken }
        }
    }

    suspend fun updateAnketaTaken(context: Context, idAnketaTaken: Int, progres: Int) {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            db.anketaTakenDao().update(idAnketaTaken, progres)
        }
    }
}