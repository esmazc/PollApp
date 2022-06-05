package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Odgovor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.floor
import kotlin.math.round

object OdgovorRepository {  //2
    private val hash = AccountRepository.acHash

    suspend fun getOdgovoriAnketa(idAnkete: Int): List<Odgovor> {
        return withContext(Dispatchers.IO) {
            val odgovori = arrayListOf<Odgovor>()
            val anketaTaken = TakeAnketaRepository.getAnketaTaken(idAnkete) ?: return@withContext listOf()
            //val idAnketaTaken = TakeAnketaRepository.getPoceteAnkete()!!.find { anketaTaken -> anketaTaken.idAnketa == idAnkete }!!.id
            //val response = ApiAdapter.retrofit.getPollAnswers(hash, idAnketaTaken)
            val response = ApiAdapter.retrofit.getPollAnswers(hash, anketaTaken.id)
            //val response = ApiAdapter.retrofit.getPollAnswers(hash, idAnkete)
            if(response.body().isNullOrEmpty()) return@withContext listOf()
            for(or in response.body()!!)
                odgovori.add(Odgovor(or.pitanjeId, or.odgovoreno))
            return@withContext odgovori
        }
    }

    suspend fun getOdgovoriAnketa1(idAnkete: Int): List<OdgovorResponse> {
        return withContext(Dispatchers.IO) {
            val anketaTaken = TakeAnketaRepository.getAnketaTaken(idAnkete) ?: return@withContext listOf()
            //val idAnketaTaken = TakeAnketaRepository.getPoceteAnkete()!!.find { anketaTaken -> anketaTaken.idAnketa == idAnkete }!!.id
            //val response = ApiAdapter.retrofit.getPollAnswers(hash, idAnketaTaken)
            val response = ApiAdapter.retrofit.getPollAnswers(hash, anketaTaken.id)
            //val response = ApiAdapter.retrofit.getPollAnswers(hash, idAnkete)
            if(response.body().isNullOrEmpty()) return@withContext listOf()
            else return@withContext response.body()!!
        }
    }

    suspend fun postaviOdgovorAnketa(idAnketaTaken: Int, idPitanje: Int, odgovor: Int): Int {
        return withContext(Dispatchers.IO) {
            val anketaTaken = TakeAnketaRepository.getAnketaTaken1(idAnketaTaken) ?: return@withContext -1 //za progres
            val pitanja = PitanjeAnketaRepository.getPitanja(anketaTaken.AnketumId)
            val odgovori = getOdgovoriAnketa1(anketaTaken.AnketumId)
            //val progres = anketaTaken.progres
            val pr = if(!odgovori.map { odgovor -> odgovor.pitanjeId }.contains(idPitanje))
                (odgovori.size + 1) / pitanja!!.size.toFloat()
            else odgovori.size / pitanja!!.size.toFloat()
            var progres: Float = round(pr * 10) * 10
            //var progres: Float = floor(pr * 10) * 10
            //val progres: Float = pr * 100;
            //if((progres / 2) % 2 != 0F)
               // progres += 10

            //if(progres > 100) progres = 100f
            val response = ApiAdapter.retrofit.setAnswer(hash, idAnketaTaken, SetAnswerBody(odgovor, idPitanje, progres.toInt()))
            //if(response.message() != "OK") return@withContext -1
//            if(response.isSuccessful) {
//                println(1)
                return@withContext progres.toInt()
//            }
//            else {
//                println(2)
//                return@withContext -1
//            }
        }
    }
}