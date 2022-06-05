package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.GrupaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeIGrupaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeRepository
import kotlinx.coroutines.*

class UpisIstrazivanjeViewModel {
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun getIstrazivanjeByGodina(godina: Int, onSuccess: (researches: List<Istrazivanje>) -> Unit, onError: (() -> Unit)?){
        scope.launch{
            val result = IstrazivanjeRepository.getIstrazivanjeByGodina(godina)
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
            val result = IstrazivanjeRepository.getUpisani()
            when (result) {
                is List<Istrazivanje> -> onSuccess.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun getGroupsByIstrazivanje(idIstrazivanja: Int, onSuccess: (groups: List<Grupa>) -> Unit, onError: (() -> Unit)?){ //??? ili nazivistr
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.getGrupeZaIstrazivanje(idIstrazivanja)
            when (result) {
                is List<Grupa> -> onSuccess.invoke(result)
                else -> onError?.invoke()
            }
        }
    }

    fun getGroupsByIstrazivanje(nazivIstrazivanja: String, onSuccess: (groups: List<Grupa>) -> Unit, onError: () -> Unit){ //mozda ne treba
        scope.launch{
            val result = GrupaRepository.getGroupsByIstrazivanje(nazivIstrazivanja)
            when (result) {
                is List<Grupa> -> onSuccess.invoke(result)
                else -> onError.invoke()
            }
        }
    }

    fun upisiUGrupu(idGrupa: Int, onSuccess: () -> Unit, onError: (() -> Unit)?){ //??? ili nazivgrupa, nazivistr, godina
        scope.launch{
            val result = IstrazivanjeIGrupaRepository.upisiUGrupu(idGrupa)
            if(result) onSuccess.invoke()
            else onError?.invoke()
        }
    }

    fun upisiUGrupu(nazivGrupe: String, nazivIstrazivanja: String/*, godina: Int*/, onSuccess: () -> Unit, onError: () -> Unit){ //mozda ne treba
        scope.launch{
            val grupa = IstrazivanjeIGrupaRepository.getGrupe()?.find { grupa -> grupa.naziv == nazivGrupe && grupa.nazivIstrazivanja == nazivIstrazivanja }
            //val grupa = GrupaRepository.getGroupsByIstrazivanje(nazivIstrazivanja)!!.find { grupa -> grupa.naziv == nazivGrupe }
            val result = IstrazivanjeIGrupaRepository.upisiUGrupu(grupa!!.id)
            if(result) onSuccess.invoke()
            else onError.invoke()
        }
    }

    /*fun getIstrazivanjeByGodina(godina: Int) : List<Istrazivanje> {
        return IstrazivanjeRepository.getIstrazivanjeByGodina(godina)
    }

    fun getAll() : List<Istrazivanje> {
        return IstrazivanjeRepository.getAll()
    }

    fun getUpisani() : List<Istrazivanje> {
        return IstrazivanjeRepository.getUpisani()
    }

    fun getGroupsByIstrazivanje(nazivIstrazivanja: String): List<Grupa> {
        return GrupaRepository.getGroupsByIstrazivanje(nazivIstrazivanja)
    }*/
}