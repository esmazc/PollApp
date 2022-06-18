package ba.etf.rma22.projekat.viewmodel

import android.util.Log
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.repositories.*
import kotlinx.coroutines.*

class PollListViewModel {
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getAll(onSuccess: (polls: List<Anketa>) -> Unit, onError: (() -> Unit)?){
        scope.launch{
            val result = AnketaRepository.getAll()
            when (result) {
                is List<Anketa> -> onSuccess.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun getMyAnkete(onSuccess: (polls: List<Anketa>) -> Unit, onError: (() -> Unit)?){
        scope.launch{
            val result = AnketaRepository.getUpisane()
            when (result) {
                is List<Anketa> -> onSuccess.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun getDone(onSuccess: (polls: List<Anketa>) -> Unit, onError: (() -> Unit)?){
        scope.launch{
            val result = AnketaRepository.getDone()
            when (result) {
                is List<Anketa> -> onSuccess.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun getFuture(onSuccess: (polls: List<Anketa>) -> Unit, onError: (() -> Unit)?){
        scope.launch{
            val result = AnketaRepository.getFuture()
            when (result) {
                is List<Anketa> -> onSuccess.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun getNotTaken(onSuccess: (polls: List<Anketa>) -> Unit, onError: (() -> Unit)?){
        scope.launch{
            val result = AnketaRepository.getNotTaken()
            when (result) {
                is List<Anketa> -> onSuccess.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun startPoll(idAnketa: Int, onSuccess: ((anketaTaken: AnketaTaken) -> Unit)?, onError: (() -> Unit)?){
        scope.launch{
            val result = TakeAnketaRepository.zapocniAnketu(idAnketa)
            if(result != null)
                TakeAnketaRepository.writeAnketaTaken(result)
            when (result) {
                is AnketaTaken -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun writeResearchesAndGroups(onSuccess: ((s: String) -> Unit)?, onError: (() -> Unit)?) {
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.writeResearchesAndGroups()
            when (result) {
                is String -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun writeAnketaTakens(onSuccess: ((s: String) -> Unit)?, onError: (() -> Unit)?) {
        scope.launch{
            val result = TakeAnketaRepository.writeAnketaTakens()
            when (result) {
                is String -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun writeAnketaGrupa(onSuccess: ((s: String) -> Unit)?, onError: (() -> Unit)?) {
        scope.launch{
            val result = AnketaRepository.writeAnketaGrupa()
            when (result) {
                is String -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }

    fun writePitanjaIPitanjaAnketa(onSuccess: ((s: String) -> Unit)?, onError: (() -> Unit)?) {
        scope.launch{
            val result = PitanjeAnketaRepository.writePitanjaIPitanjaAnketa()
            when (result) {
                is String -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }

    fun postaviHash(acHash: String, onSuccess: (() -> Unit)?, onError: (() -> Unit)?) {
        scope.launch{
            val result = AccountRepository.postaviHash(acHash)
            when (result) {
                is Boolean -> onSuccess?.invoke()
                else-> onError?.invoke()
            }
        }
    }
}