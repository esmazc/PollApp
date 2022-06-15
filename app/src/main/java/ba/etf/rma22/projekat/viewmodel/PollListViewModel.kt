package ba.etf.rma22.projekat.viewmodel

import android.content.Context
import ba.etf.rma22.projekat.InternetConnectivity
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeIGrupaRepository
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository
import ba.etf.rma22.projekat.data.repositories.TakeAnketaRepository
import ba.etf.rma22.projekat.view.FragmentAnkete
import kotlinx.coroutines.*

class PollListViewModel {
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun writePolls(context: Context, ankete: List<Anketa>, onSuccess: ((s: String) -> Unit)?, onError: (() -> Unit)?) {
        scope.launch{
            val result = AnketaRepository.writePolls(context, ankete)
            when (result) {
                is String -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }

    fun getAll(context: Context, onSuccess: (context: Context, polls: List<Anketa>) -> Unit, onError: (() -> Unit)?){
        scope.launch{
            val result: List<Anketa>
            if(InternetConnectivity.isOnline(context)) {
                result = AnketaRepository.getAll()
                writePolls(context, result, null, null)
            }
            else
                result = AnketaRepository.getAll(context)
            when (result) {
                is List<Anketa> -> onSuccess.invoke(context, result)
                else -> onError?.invoke()
            }
        }
    }

    fun getMyAnkete(context: Context, onSuccess: (context: Context, polls: List<Anketa>) -> Unit, onError: (() -> Unit)?){
        scope.launch{
            val result: List<Anketa>
            if(InternetConnectivity.isOnline(context))
                result = AnketaRepository.getUpisane()
            else
                result = AnketaRepository.getUpisane(context)
            when (result) {
                is List<Anketa> -> onSuccess.invoke(context, result)
                else -> onError?.invoke()
            }
        }
    }

    fun getDone(context: Context, onSuccess: (context: Context, polls: List<Anketa>) -> Unit, onError: (() -> Unit)?){
        scope.launch{
            val result: List<Anketa>
            if(InternetConnectivity.isOnline(context))
                result = AnketaRepository.getDone()
            else
                result = AnketaRepository.getDone(context)
            when (result) {
                is List<Anketa> -> onSuccess.invoke(context, result)
                else -> onError?.invoke()
            }
        }
    }

    fun getFuture(context: Context, onSuccess: (context: Context, polls: List<Anketa>) -> Unit, onError: (() -> Unit)?){
        scope.launch{
            val result: List<Anketa>
            if(InternetConnectivity.isOnline(context))
                result = AnketaRepository.getFuture()
            else
                result = AnketaRepository.getFuture(context)
            when (result) {
                is List<Anketa> -> onSuccess.invoke(context, result)
                else -> onError?.invoke()
            }
        }
    }

    fun getNotTaken(context: Context, onSuccess: (context: Context, polls: List<Anketa>) -> Unit, onError: (() -> Unit)?){
        scope.launch{
            val result: List<Anketa>
            if(InternetConnectivity.isOnline(context))
                result = AnketaRepository.getNotTaken()
            else
                result = AnketaRepository.getNotTaken(context)
            when (result) {
                is List<Anketa> -> onSuccess.invoke(context, result)
                else -> onError?.invoke()
            }
        }
    }

    fun startPoll(context: Context, idAnketa: Int, onSuccess: ((anketaTaken: AnketaTaken) -> Unit)?, onError: (() -> Unit)?){
        scope.launch{
            val result = TakeAnketaRepository.zapocniAnketu(idAnketa)
            if(result != null)
                TakeAnketaRepository.writeAnketaTaken(context, result)
            when (result) {
                is AnketaTaken -> {
                    //TakeAnketaRepository.writeAnketaTaken(context, result)
                    onSuccess?.invoke(result)
                }
                else -> onError?.invoke()
            }
        }
    }

    /*fun writeAnketaTaken(context: Context, anketaTaken: AnketaTaken, onSuccess: ((s: String) -> Unit)?, onError: (() -> Unit)?){
        scope.launch{
            val result = TakeAnketaRepository.writeAnketaTaken(context, anketaTaken)
            when (result) {
                is String -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }*/

    fun writeResearchesAndGroups(context: Context, onSuccess: ((s: String) -> Unit)?, onError: (() -> Unit)?) {
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.writeResearchesAndGroups(context)
            when (result) {
                is String -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun writeAnketaTakens(context: Context, onSuccess: ((s: String) -> Unit)?, onError: (() -> Unit)?) {
        scope.launch{
            val result = TakeAnketaRepository.writeAnketaTakens(context)
            when (result) {
                is String -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun writeAnketaGrupa(context: Context, onSuccess: ((s: String) -> Unit)?, onError: (() -> Unit)?) {
        scope.launch{
            val result = AnketaRepository.writeAnketaGrupa(context)
            when (result) {
                is String -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }

    fun writePitanjaIPitanjaAnketa(context: Context, onSuccess: ((s: String) -> Unit)?, onError: (() -> Unit)?) {
        scope.launch{
            val result = PitanjeAnketaRepository.writePitanjaIPitanjaAnketa(context)
            when (result) {
                is String -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }
}