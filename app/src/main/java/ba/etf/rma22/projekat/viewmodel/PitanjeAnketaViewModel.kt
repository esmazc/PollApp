package ba.etf.rma22.projekat.viewmodel

import android.content.Context
import android.util.Log
import android.widget.TextView
import ba.etf.rma22.projekat.InternetConnectivity
import ba.etf.rma22.projekat.data.models.*
import ba.etf.rma22.projekat.data.repositories.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PitanjeAnketaViewModel {
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getPitanja(context: Context, idAnkete: Int, onSuccess: (questions: List<Pitanje>) -> Unit, onError: (() -> Unit)?){
        scope.launch{
            var result: List<Pitanje>? = null
            if(InternetConnectivity.isOnline(context))
                result = PitanjeAnketaRepository.getPitanja(idAnkete)
            else {
                val anketaTaken = TakeAnketaRepository.getAnketaTaken(context, idAnkete)
                if(anketaTaken != null)
                    result = PitanjeAnketaRepository.getPitanja(context, idAnkete)
            }
            when (result) {
                is List<Pitanje> -> onSuccess.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun getAnketaTaken(context: Context, idAnketa: Int, onSuccess: (context: Context, anketaTaken: AnketaTaken) -> Unit, onError: (() -> Unit)?) {
        scope.launch{
            val result: AnketaTaken?
            if(InternetConnectivity.isOnline(context))
                result = TakeAnketaRepository.getAnketaTaken(idAnketa)
            else
                result = TakeAnketaRepository.getAnketaTaken(context, idAnketa)
            when (result) {
                is AnketaTaken -> onSuccess.invoke(context, result)
                else -> onError!!.invoke()
            }
        }
    }

    fun getAnswersForPoll(context: Context, idAnketa: Int, onSuccess: (answers: List<Odgovor>, position: Int, answerText: TextView) -> Unit, onError: (() -> Unit)?, position: Int, answerText: TextView) {
        scope.launch{
            val result: List<Odgovor>
            if(InternetConnectivity.isOnline(context))
                result = OdgovorRepository.getOdgovoriAnketa(idAnketa)
            else
                result = OdgovorRepository.getOdgovoriAnketa(context, idAnketa)
            when (result) {
                is List<Odgovor> -> onSuccess.invoke(result, position, answerText)
                else -> onError?.invoke()
            }
        }
    }

    /*fun getAnswersForPoll1(idAnketa: Int, onSuccess: (answers: List<OdgovorResponse>, position: Int, answerText: TextView) -> Unit, onError: (() -> Unit)?, position: Int, answerText: TextView) {
        scope.launch{
            val result = OdgovorRepository.getOdgovoriAnketa1(idAnketa)
            when (result) {
                is List<OdgovorResponse> -> onSuccess.invoke(result, position, answerText)
                else -> onError?.invoke()
            }
        }
    }*/

    fun postAnswer(context: Context, idAnketaTaken: Int, idPitanje: Int, odgovor: Int, onSuccess: (progres: Int) -> Unit, onError: (() -> Unit)?) {
        scope.launch{
            val result = OdgovorRepository.postaviOdgovorAnketa(idAnketaTaken, idPitanje, odgovor)
            if(result != -1) {
                writeOdgovori(context, null, null)
                //AnketaRepository.writePolls(context)
                TakeAnketaRepository.updateAnketaTaken(context, idAnketaTaken, result)
                AnketaRepository.updatePoll(context, idAnketaTaken, result)
            }
            when (result) {
                is Int -> onSuccess.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun writeOdgovori(context: Context, onSuccess: ((s: String) -> Unit)?, onError: (() -> Unit)?) {
        scope.launch{
            val result = OdgovorRepository.writeOdgovori(context)
            when (result) {
                is String -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }
}