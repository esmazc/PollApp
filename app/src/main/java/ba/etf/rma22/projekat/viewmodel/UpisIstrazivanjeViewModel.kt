package ba.etf.rma22.projekat.viewmodel

import android.content.Context
import ba.etf.rma22.projekat.InternetConnectivity
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeIGrupaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeRepository
import kotlinx.coroutines.*

class UpisIstrazivanjeViewModel {
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    /*fun writeResearches(context: Context, researches: List<Istrazivanje>, onSuccess: ((s: String) -> Unit)?, onError: (() -> Unit)?) {
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.writeResearches(context, researches)
            when (result) {
                is String -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }

    fun writeGroups(context: Context, groups: List<Grupa>, onSuccess: ((s: String) -> Unit)?, onError: (() -> Unit)?) {
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.writeGroups(context, groups)
            when (result) {
                is String -> onSuccess?.invoke(result)
                else-> onError?.invoke()
            }
        }
    }*/

    fun getIstrazivanjeByGodina(context: Context, godina: Int, onSuccess: (context: Context, researches: List<Istrazivanje>) -> Unit, onError: (() -> Unit)?){
        scope.launch{
            val result: List<Istrazivanje>
            if(InternetConnectivity.isOnline(context)) {
                result = IstrazivanjeRepository.getIstrazivanjeByGodina(godina)
                //val allResearches = IstrazivanjeIGrupaRepository.getIstrazivanja()
                //IstrazivanjeIGrupaRepository.writeResearches(context, allResearches)
            }
            else
                result = IstrazivanjeRepository.getIstrazivanjeByGodina(context, godina)
            when (result) {
                is List<Istrazivanje> -> onSuccess.invoke(context, result)
                else -> onError?.invoke()
            }
        }
    }

    fun getAll(context: Context, onSuccess: (researches: List<Istrazivanje>) -> Unit, onError: () -> Unit){
        scope.launch{
            val result: List<Istrazivanje>
            if(InternetConnectivity.isOnline(context))
                result = IstrazivanjeIGrupaRepository.getIstrazivanja()
            else
                result = IstrazivanjeIGrupaRepository.getIstrazivanja(context)
            when (result) {
                is List<Istrazivanje> -> onSuccess.invoke(result)
                else -> onError.invoke()
            }
        }
    }

    fun getUpisani(context: Context, onSuccess: (researches: List<Istrazivanje>) -> Unit, onError: (() -> Unit)?){
        scope.launch{
            val result: List<Istrazivanje>
            if(InternetConnectivity.isOnline(context))
                result = IstrazivanjeRepository.getUpisani()
            else
                result = IstrazivanjeRepository.getUpisani(context)
            when (result) {
                is List<Istrazivanje> -> onSuccess.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun getGroupsByIstrazivanje(context: Context, idIstrazivanja: Int, onSuccess: (context: Context, groups: List<Grupa>) -> Unit, onError: (() -> Unit)?){
        scope.launch{
            val result: List<Grupa>
            if(InternetConnectivity.isOnline(context)) {
                result = IstrazivanjeIGrupaRepository.getGrupeZaIstrazivanje(idIstrazivanja)
                //val allGroups = IstrazivanjeIGrupaRepository.getGrupe()
                //IstrazivanjeIGrupaRepository.writeGroups(context, allGroups)
            }
            else
                result = IstrazivanjeIGrupaRepository.getGrupeZaIstrazivanje(context, idIstrazivanja)
            when (result) {
                is List<Grupa> -> onSuccess.invoke(context, result)
                else -> onError?.invoke()
            }
        }
    }

    fun upisiUGrupu(context: Context, idGrupa: Int, onSuccess: () -> Unit, onError: (() -> Unit)?){
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.upisiUGrupu(idGrupa)
            IstrazivanjeIGrupaRepository.writeResearchesAndGroups(context)
            //AnketaRepository.writePolls(context)
            if(result) onSuccess.invoke()
            else onError?.invoke()
        }
    }
}