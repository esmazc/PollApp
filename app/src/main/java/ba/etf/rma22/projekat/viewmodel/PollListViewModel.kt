package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.TakeAnketaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PollListViewModel {
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    /*fun getAll() : List<Anketa>? {
        var result: List<Anketa>? = null
        scope.launch{
            result = AnketaRepository.getAll()
        }
        return result
    }*/

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

    /*fun getAll(): List<Anketa> {
        return AnketaRepository.getAll()
    }

    fun getMyAnkete(): List<Anketa> {
        return AnketaRepository.getMyAnkete()
    }

    fun getDone(): List<Anketa> {
        return AnketaRepository.getDone()
    }

    fun getFuture(): List<Anketa> {
        return AnketaRepository.getFuture()
    }

    fun getNotTaken(): List<Anketa> {
        return AnketaRepository.getNotTaken()
    }*/

    fun startPoll(idAnketa: Int, onSuccess: ((anketaTaken: AnketaTaken) -> Unit)?, onError: (() -> Unit)?){
        scope.launch{
            val result = TakeAnketaRepository.zapocniAnketu(idAnketa)
            when (result) {
                is AnketaTaken -> onSuccess?.invoke(result)
                else -> onError?.invoke()
            }
        }
    }


}