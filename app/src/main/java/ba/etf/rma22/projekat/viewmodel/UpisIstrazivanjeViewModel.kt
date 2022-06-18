package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeIGrupaRepository
import kotlinx.coroutines.*

class UpisIstrazivanjeViewModel {
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getIstrazivanjeByGodina(godina: Int, onSuccess: (researches: List<Istrazivanje>) -> Unit, onError: (() -> Unit)?){
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.getIstrazivanjeByGodina(godina)
            when (result) {
                is List<Istrazivanje> -> onSuccess.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun getAll(onSuccess: (researches: List<Istrazivanje>) -> Unit, onError: () -> Unit){
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.getIstrazivanja()
            when (result) {
                is List<Istrazivanje> -> onSuccess.invoke(result)
                else -> onError.invoke()
            }
        }
    }

    fun getUpisani(onSuccess: (researches: List<Istrazivanje>) -> Unit, onError: (() -> Unit)?){
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.getUpisani()
            when (result) {
                is List<Istrazivanje> -> onSuccess.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun getGroupsByIstrazivanje(idIstrazivanja: Int, onSuccess: (groups: List<Grupa>) -> Unit, onError: (() -> Unit)?){
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.getGrupeZaIstrazivanje(idIstrazivanja)
            when (result) {
                is List<Grupa> -> onSuccess.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun upisiUGrupu(idGrupa: Int, onSuccess: () -> Unit, onError: (() -> Unit)?){
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.upisiUGrupu(idGrupa)
            IstrazivanjeIGrupaRepository.writeResearchesAndGroups()
            if(result) onSuccess.invoke()
            else onError?.invoke()
        }
    }
}