package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.util.Log
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.PitanjeAnketa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object PitanjeAnketaRepository {

    suspend fun getPitanja(idAnkete: Int): List<Pitanje> {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getPollQuestions(idAnkete)
            if(response.body() == null) return@withContext listOf()
            return@withContext response.body()!!
        }
    }

    suspend fun getPitanja(context: Context, idAnkete: Int): List<Pitanje> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
//            val pitanjeAnketa = db.pitanjeAnketaDao().getAll()
//            pitanjeAnketa = pitanjeAnketa.filter { pa -> pa.AnketumId == idAnkete }
            val pitanjeAnketa = db.pitanjeAnketaDao().getForPoll(idAnkete).map { pa -> pa.PitanjeId }
            //pitanjeAnketa.map { pa -> pa.PitanjeId }
            val pitanja = db.pitanjeDao().getAll().filter { p -> pitanjeAnketa.contains(p.id) }
            //pitanja = pitanja.filter { p -> pitanjeAnketa.contains(p.id) }
            return@withContext pitanja
        }
    }

    suspend fun writePitanjaIPitanjaAnketa(context: Context): String? {
        return withContext(Dispatchers.IO) {
            try {
                var i = 1
                val db = AppDatabase.getInstance(context)
                db.pitanjeDao().delete()
                db.pitanjeAnketaDao().delete()
                val anketeId = db.anketaDao().getAll().map { a -> a.id }
                var pitanja: List<Pitanje>
                for(id in anketeId) {
                    pitanja = ApiAdapter.retrofit.getPollQuestions(id).body()!!
                    for(p in pitanja) {
                        db.pitanjeDao().insert(Pitanje(i, p.naziv, p.tekstPitanja, p.opcije))
                        db.pitanjeAnketaDao().insert(PitanjeAnketa(id, p.id))
                        i++
                    }
                }
                return@withContext "success"
            }
            catch(error:Exception){
                return@withContext null
            }
        }
    }

    /*suspend fun writePitanje(context: Context, pitanje: Pitanje): String? {
        return withContext(Dispatchers.IO) {
            try {
                val db = AppDatabase.getInstance(context)
                db.pitanjeDao().insert(pitanje)
                return@withContext "success"
            }
            catch(error:Exception){
                return@withContext null
            }
        }
    }*/
}