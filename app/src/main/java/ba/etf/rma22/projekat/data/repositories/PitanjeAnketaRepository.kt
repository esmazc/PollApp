package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import ba.etf.rma22.projekat.InternetConnectivity
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.PitanjeAnketa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PitanjeAnketaRepository {
    companion object {
        private lateinit var context: Context

        fun setContext(_context: Context) {
            context = _context
        }

        suspend fun getPitanja(idAnkete: Int): List<Pitanje> {
            return withContext(Dispatchers.IO) {
                if(InternetConnectivity.isOnline(context)) {
                    val response = ApiAdapter.retrofit.getPollQuestions(idAnkete)
                    if (response.body() == null) return@withContext listOf()
                    return@withContext response.body()!!
                }
                else {
                    val anketaTaken = TakeAnketaRepository.getAnketaTaken(idAnkete)
                    if(anketaTaken != null) {
                        val db = AppDatabase.getInstance(context)
                        val pitanjeAnketa = db.pitanjeAnketaDao().getForPoll(idAnkete).map { pa -> pa.PitanjeId }
                        val pitanja = db.pitanjeDao().getAll().filter { p -> pitanjeAnketa.contains(p.id) }
                        return@withContext pitanja
                    }
                    else return@withContext listOf()
                }
            }
        }

        suspend fun writePitanjaIPitanjaAnketa(): String? {
            return withContext(Dispatchers.IO) {
                try {
                    var i = 1
                    val db = AppDatabase.getInstance(context)
                    db.pitanjeDao().delete()
                    db.pitanjeAnketaDao().delete()
                    val anketeId = db.anketaDao().getAll().map { a -> a.id }
                    var pitanja: List<Pitanje>
                    for (id in anketeId) {
                        pitanja = ApiAdapter.retrofit.getPollQuestions(id).body()!!
                        for (p in pitanja) {
                            db.pitanjeDao().insert(Pitanje(i, p.naziv, p.tekstPitanja, p.opcije))
                            db.pitanjeAnketaDao().insert(PitanjeAnketa(id, p.id))
                            i++
                        }
                    }
                    return@withContext "success"
                } catch (error: Exception) {
                    return@withContext null
                }
            }
        }
    }
}