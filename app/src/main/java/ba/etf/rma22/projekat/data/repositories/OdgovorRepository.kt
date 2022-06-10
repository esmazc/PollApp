package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Odgovor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.round

object OdgovorRepository {
    private val hash = AccountRepository.acHash

    suspend fun getOdgovoriAnketa(idAnkete: Int): List<Odgovor> {
        return withContext(Dispatchers.IO) {
            val odgovori = arrayListOf<Odgovor>()
            val anketaTaken = TakeAnketaRepository.getAnketaTaken(idAnkete) ?: return@withContext listOf()
            val response = ApiAdapter.retrofit.getPollAnswers(hash, anketaTaken.id)
            if(response.body().isNullOrEmpty()) return@withContext listOf()
            for(or in response.body()!!)
                odgovori.add(Odgovor(or.pitanjeId, or.odgovoreno))
            return@withContext odgovori
        }
    }

    suspend fun getOdgovoriAnketa1(idAnkete: Int): List<OdgovorResponse> {
        return withContext(Dispatchers.IO) {
            val anketaTaken = TakeAnketaRepository.getAnketaTaken(idAnkete) ?: return@withContext listOf()
            val response = ApiAdapter.retrofit.getPollAnswers(hash, anketaTaken.id)
            if(response.body().isNullOrEmpty()) return@withContext listOf()
            else return@withContext response.body()!!
        }
    }

    suspend fun postaviOdgovorAnketa(idAnketaTaken: Int, idPitanje: Int, odgovor: Int): Int {
        return withContext(Dispatchers.IO) {
            val anketaTaken = TakeAnketaRepository.getAnketaTaken1(idAnketaTaken) ?: return@withContext -1
            val pitanja = PitanjeAnketaRepository.getPitanja(anketaTaken.AnketumId)
            val odgovori = getOdgovoriAnketa1(anketaTaken.AnketumId)
            val pr = if(!odgovori.map { odgovor -> odgovor.pitanjeId }.contains(idPitanje))
                (odgovori.size + 1) / pitanja.size.toFloat()
            else odgovori.size / pitanja.size.toFloat()
            var progres: Float = round(pr * 10) * 10
            if((progres / 2) % 2 != 0F)
                progres += 10
            ApiAdapter.retrofit.setAnswer(hash, idAnketaTaken, SetAnswerBody(odgovor, idPitanje, progres.toInt()))
            return@withContext progres.toInt()
        }
    }
}