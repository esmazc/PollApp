package ba.etf.rma22.projekat.viewmodel

import android.widget.TextView
import ba.etf.rma22.projekat.data.models.*
import ba.etf.rma22.projekat.data.repositories.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PitanjeAnketaViewModel {
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getPitanja(idAnkete: Int, onSuccess: (questions: List<Pitanje>) -> Unit, onError: (() -> Unit)?){
        scope.launch{
            val result = PitanjeAnketaRepository.getPitanja(idAnkete)
            when (result) {
                is List<Pitanje> -> onSuccess.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun getAnketaTaken(idAnketa: Int, onSuccess: (anketaTaken: AnketaTaken) -> Unit, onError: (() -> Unit)?) {
        scope.launch{
            val result = TakeAnketaRepository.getAnketaTaken(idAnketa)
            when (result) {
                is AnketaTaken -> onSuccess.invoke(result)
                else -> onError!!.invoke()
            }
        }
    }

    fun getAnswersForPoll(idAnketa: Int, onSuccess: (answers: List<Odgovor>, position: Int, answerText: TextView) -> Unit, onError: (() -> Unit)?, position: Int, answerText: TextView) {
        scope.launch{
            val result = OdgovorRepository.getOdgovoriAnketa(idAnketa)
            when (result) {
                is List<Odgovor> -> onSuccess.invoke(result, position, answerText)
                else -> onError?.invoke()
            }
        }
    }

    fun getAnswersForPoll1(idAnketa: Int, onSuccess: (answers: List<OdgovorResponse>, position: Int, answerText: TextView) -> Unit, onError: (() -> Unit)?, position: Int, answerText: TextView) {
        scope.launch{
            val result = OdgovorRepository.getOdgovoriAnketa1(idAnketa)
            when (result) {
                is List<OdgovorResponse> -> onSuccess.invoke(result, position, answerText)
                else -> onError?.invoke()
            }
        }
    }

    fun postAnswer(idAnketaTaken: Int, idPitanje: Int, odgovor: Int, onSuccess: (progres: Int) -> Unit, onError: (() -> Unit)?) {
        scope.launch{
            val result = OdgovorRepository.postaviOdgovorAnketa(idAnketaTaken, idPitanje, odgovor)
            when (result) {
                is Int -> onSuccess.invoke(result)
                else -> onError?.invoke()
            }
        }
    }
}