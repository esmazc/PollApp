package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import ba.etf.rma22.projekat.InternetConnectivity
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.repositories.AccountRepository.Companion.acHash as hash
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.round

class OdgovorRepository {
    companion object {
        private lateinit var context: Context
        //private val hash = AccountRepository.acHash

        fun setContext(_context: Context) {
            context = _context
        }

        suspend fun getOdgovoriAnketa(idAnkete: Int): List<Odgovor> {
            return withContext(Dispatchers.IO) {
                if(InternetConnectivity.isOnline(context)) {
                    val anketaTaken = TakeAnketaRepository.getAnketaTaken(idAnkete) ?: return@withContext listOf()
                    val response = ApiAdapter.retrofit.getPollAnswers(hash, anketaTaken.id)
                    if (response.body().isNullOrEmpty()) return@withContext listOf()
                    else return@withContext response.body()!!
                }
                else {
                    val anketaTaken = TakeAnketaRepository.getAnketaTaken(idAnkete) ?: return@withContext listOf()
                    val db = AppDatabase.getInstance(context)
                    val odgovori = db.odgovorDao().getAll().filter { o -> o.anketaTakenId == anketaTaken.id }
                    return@withContext odgovori
                }
            }
        }

        suspend fun writeOdgovori(): String? {
            return withContext(Dispatchers.IO) {
                try {
                    var i = 1
                    val db = AppDatabase.getInstance(context)
                    db.odgovorDao().delete()
                    val anketeId = db.anketaDao().getAll().map { a -> a.id }
                    for (id in anketeId) {
                        val anketaTaken = TakeAnketaRepository.getAnketaTaken(id)
                        if (anketaTaken != null) {
                            val odgovori = ApiAdapter.retrofit.getPollAnswers(hash, anketaTaken.id).body()
                            if (odgovori != null) {
                                for (odg in odgovori) {
                                    db.odgovorDao().insert(Odgovor(i, odg.odgovoreno, odg.anketaTakenId, odg.pitanjeId))
                                    i++
                                }
                            }
                        }
                    }
                    return@withContext "success"
                } catch (error: Exception) {
                    return@withContext null
                }
            }
        }

        suspend fun postaviOdgovorAnketa(idAnketaTaken: Int, idPitanje: Int, odgovor: Int): Int {
            return withContext(Dispatchers.IO) {
                val anketaTaken = TakeAnketaRepository.getAnketaTaken1(idAnketaTaken) ?: return@withContext -1
                val pitanja = PitanjeAnketaRepository.getPitanja(anketaTaken.AnketumId)
                val odgovori = getOdgovoriAnketa(anketaTaken.AnketumId)
                val pr = if (!odgovori.map { odgovor -> odgovor.pitanjeId }.contains(idPitanje))
                    (odgovori.size + 1) / pitanja.size.toFloat()
                else odgovori.size / pitanja.size.toFloat()
                var progres: Float = round(pr * 10) * 10
                if ((progres / 2) % 2 != 0F)
                    progres += 10
                val response = ApiAdapter.retrofit.setAnswer(hash, idAnketaTaken, SetAnswerBody(odgovor, idPitanje, progres.toInt()))
                if (!response.isSuccessful) return@withContext -1
                val db = AppDatabase.getInstance(context)
                val size = db.odgovorDao().getAll().size
                db.odgovorDao().insert(Odgovor(size + 1, 1, idAnketaTaken, idPitanje))
                return@withContext progres.toInt()
            }
        }
    }
}