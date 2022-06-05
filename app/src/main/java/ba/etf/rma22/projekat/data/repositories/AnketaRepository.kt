package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Istrazivanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

object AnketaRepository {  //2
    //private val korisnik = KorisnikRepository.getUser()

    suspend fun getAll(): List<Anketa>? {
        val ankete = arrayListOf<Anketa>()
        var i = 0
        return withContext(Dispatchers.IO) {
            while(true) {
                i++
                val response = ApiAdapter.retrofit.getPolls(i)
                val polls = response.body()
                if(polls!!.isEmpty() && i == 0) return@withContext null
                if(polls.isEmpty() && i != 0) break
                ankete.addAll(polls)
            }
            for(a in ankete) {
                var nazivIstrazivanja = ""
                var flag = true
                val grupe = ApiAdapter.retrofit.getGroupsForPoll(a.id).body()
                for(g in grupe!!) {
                    val istrazivanje = IstrazivanjeIGrupaRepository.getIstrazivanjeZaGrupu(g.id)
                    if(!nazivIstrazivanja.contains(istrazivanje.naziv)) {
                        if (!flag) nazivIstrazivanja += ", " + istrazivanje.naziv
                        else {
                            nazivIstrazivanja = istrazivanje.naziv
                            flag = false
                        }
                    }
                }
                a.nazivIstrazivanja = nazivIstrazivanja
                a.progres = -1f
                //a.stanje = Anketa.Stanje.ACTIVE
                val anketaTaken: AnketaTaken? = TakeAnketaRepository.getAnketaTaken(a.id)
                if(anketaTaken != null) {
                    //println(a.naziv)
                    a.datumRada = anketaTaken.datumRada
                    var progres = anketaTaken.progres
                    if((progres / 2) % 2 != 0F)
                        progres += 10
                    a.progres = progres / 100f
                }
                a.postaviStanje()
                if(a.progres == -1f) a.progres = 0f
            }
            //ApiAdapter.retrofit.delete(AccountRepository.acHash)
            return@withContext ankete
        }
    }

    /*suspend fun getMyAnkete(): List<Anketa> {
        /*val myAnkete: ArrayList<Anketa> = arrayListOf()
        val polls: List<Anketa> = polls
        for(anketa: Anketa in polls) {
            if(korisnik.parovi.contains(Pair(anketa.nazivIstrazivanja, anketa.nazivGrupe)))
                myAnkete.add(anketa)
        }
        return myAnkete*/
        return getUpisane()!!
    }*/

    suspend fun getDone(): List<Anketa>? {
        /*val myDone: ArrayList<Anketa> = arrayListOf()
        val myAnkete: List<Anketa> = getMyAnkete()
        for(anketa: Anketa in myAnkete) {
            if(anketa.stanje == Anketa.Stanje.DONE)
                myDone.add(anketa)
        }
        return myDone*/
        return getUpisane()!!.filter { anketa -> anketa.stanje == Anketa.Stanje.DONE }
        //return getUpisane()!!.filter { anketa -> anketa.datumKraj!!.before(Date()) && TakeAnketaRepository.getAnketaTaken(anketa.id)!!.datumRada != null/*&& anketaTaken.datumRada != null*/ }
    }

    suspend fun getFuture(): List<Anketa>? {
        /*val myFuture: ArrayList<Anketa> = arrayListOf()
        val myAnkete: List<Anketa> = getMyAnkete()
        for(anketa: Anketa in myAnkete) {
            if(anketa.stanje == Anketa.Stanje.ACTIVE || anketa.stanje == Anketa.Stanje.NOTSTARTEDYET)
                myFuture.add(anketa)
        }
        return myFuture*/
        return getUpisane()!!.filter { anketa -> anketa.stanje == Anketa.Stanje.ACTIVE || anketa.stanje == Anketa.Stanje.NOTSTARTEDYET }
        //return getUpisane()!!.filter { anketa -> anketa.datumPocetak.after(Date()) || anketa.datumKraj!!.after(Date()) }
    }

    suspend fun getNotTaken(): List<Anketa>? {
        /*val myNotTaken: ArrayList<Anketa> = arrayListOf()
        val myAnkete: List<Anketa> = getMyAnkete()
        for(anketa: Anketa in myAnkete) {
            if(anketa.stanje == Anketa.Stanje.ENDED)
                myNotTaken.add(anketa)
        }
        return myNotTaken*/
        return getUpisane()!!.filter { anketa -> anketa.stanje == Anketa.Stanje.ENDED }
        //return getUpisane()!!.filter { anketa -> anketa.datumKraj!!.before(Date()) && TakeAnketaRepository.getAnketaTaken(anketa.id)!!.datumRada == null/*&& anketaTaken.datumRada == null*/ }
    }

    suspend fun getAll(offset: Int): List<Anketa>? {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getPolls(offset)
            //return@withContext response.body()
            if(response.body().isNullOrEmpty()) return@withContext null
            else return@withContext response.body()
        }
    }

    suspend fun getById(id: Int): Anketa? {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getPollById(id)
            return@withContext response.body()
            //if(response.isSuccessful) return@withContext response.body()
            //else return@withContext null
        }
    }

    suspend fun getUpisane(): List<Anketa>? {
        val upisaneAnkete = arrayListOf<Anketa>()
        val upisaneGrupe = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
        return withContext(Dispatchers.IO) {
            if(upisaneGrupe != null) {
                val ankete = getAll()
                for (i in upisaneGrupe) {
                    val anketeGrupa = ApiAdapter.retrofit.getPollsByGroup(i.id).body()
                    for (j in anketeGrupa!!) {
                        if(!upisaneAnkete.contains(j))
                        upisaneAnkete.add(ankete!!.find { anketa -> anketa.id == j.id }!!)
                    }
                }
            }
            /*val upisaneAnkete1 = upisaneAnkete.distinctBy { it.id }
            val upisanaIstrazivanja = IstrazivanjeRepository.getUpisani()
            for(a in upisaneAnkete1) {
                var nazivIstrazivanja = ""
                var flag = true
                val grupe = ApiAdapter.retrofit.getGroupsForPoll(a.id).body()
                a.nazivIstrazivanja = ""
                for(g in grupe!!) {
                    val istrazivanje = IstrazivanjeIGrupaRepository.getIstrazivanjeZaGrupu(g.id)
                    if(upisanaIstrazivanja.contains(istrazivanje) && !nazivIstrazivanja.contains(istrazivanje.naziv)) {
                        if (!flag) nazivIstrazivanja += ", " + istrazivanje.naziv
                        else {
                            nazivIstrazivanja = istrazivanje.naziv
                            flag = false
                        }
                    }
                }
                a.nazivIstrazivanja = nazivIstrazivanja*/
                /*val anketaTaken = TakeAnketaRepository.getAnketaTaken(a.id)  //ima vec u getAll
                a.datumRada = anketaTaken.datumRada
                a.progres = anketaTaken.progres
                a.postaviStanje()*/
            //}
            return@withContext upisaneAnkete
        }
    }
}
