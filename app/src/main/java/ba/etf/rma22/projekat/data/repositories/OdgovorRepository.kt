package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.util.Log
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.PitanjeAnketa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.round

object OdgovorRepository {
    private val hash = AccountRepository.acHash

    suspend fun getOdgovoriAnketa(idAnkete: Int): List<Odgovor> {
        return withContext(Dispatchers.IO) {
            //val odgovori = arrayListOf<Odgovor>()
            val anketaTaken = TakeAnketaRepository.getAnketaTaken(idAnkete) ?: return@withContext listOf()
            val response = ApiAdapter.retrofit.getPollAnswers(hash, anketaTaken.id)
            if(response.body().isNullOrEmpty()) return@withContext listOf()
            //for(or in response.body()!!)
              //  odgovori.add(Odgovor(or.pitanjeId, or.odgovoreno))
            //return@withContext odgovori
            else return@withContext response.body()!!
        }
    }

    suspend fun getOdgovoriAnketa(context: Context, idAnkete: Int): List<Odgovor> {
        return withContext(Dispatchers.IO) {
            val anketaTaken = TakeAnketaRepository.getAnketaTaken(context, idAnkete) ?: return@withContext listOf()
            val db = AppDatabase.getInstance(context)
            val odgovori = db.odgovorDao().getAll().filter { o -> o.anketaTakenId == anketaTaken.id }
            return@withContext odgovori
        }
    }

    suspend fun writeOdgovori(context: Context): String? {
        return withContext(Dispatchers.IO) {
            try {
                var i = 1
                val db = AppDatabase.getInstance(context)
                db.odgovorDao().delete()
                val anketeId = db.anketaDao().getAll().map { a -> a.id }
                for(id in anketeId) {
                    val anketaTaken = TakeAnketaRepository.getAnketaTaken(context, id)
                    if(anketaTaken != null) {
                        val odgovori = ApiAdapter.retrofit.getPollAnswers(hash, anketaTaken.id).body()
                        if (odgovori != null) {
                            for(odg in odgovori) {
                                db.odgovorDao().insert(Odgovor(i, odg.odgovoreno, odg.anketaTakenId, odg.pitanjeId))
                                i++
                            }
                        }
                    }
                }
                return@withContext "success"
            }
            catch(error:Exception){
                return@withContext null
            }
        }
    }

    /*suspend fun getOdgovoriAnketa1(idAnkete: Int): List<OdgovorResponse> {
        return withContext(Dispatchers.IO) {
            val anketaTaken = TakeAnketaRepository.getAnketaTaken(idAnkete) ?: return@withContext listOf()
            val response = ApiAdapter.retrofit.getPollAnswers(hash, anketaTaken.id)
            if(response.body().isNullOrEmpty()) return@withContext listOf()
            else return@withContext response.body()!!
        }
    }*/

    suspend fun postaviOdgovorAnketa(idAnketaTaken: Int, idPitanje: Int, odgovor: Int): Int {
        return withContext(Dispatchers.IO) {
            val anketaTaken = TakeAnketaRepository.getAnketaTaken1(idAnketaTaken) ?: return@withContext -1
            val pitanja = PitanjeAnketaRepository.getPitanja(anketaTaken.AnketumId)
            val odgovori = getOdgovoriAnketa(anketaTaken.AnketumId)
            val pr = if(!odgovori.map { odgovor -> odgovor.pitanjeId }.contains(idPitanje))
                (odgovori.size + 1) / pitanja.size.toFloat()
            else odgovori.size / pitanja.size.toFloat()
            var progres: Float = round(pr * 10) * 10
            if((progres / 2) % 2 != 0F)
                progres += 10
            val response = ApiAdapter.retrofit.setAnswer(hash, idAnketaTaken, SetAnswerBody(odgovor, idPitanje, progres.toInt()))
            if(!response.isSuccessful) return@withContext -1
            return@withContext progres.toInt()
        }
    }
}