package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.util.Log
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaGrupa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AnketaRepository {

    suspend fun writePolls(context: Context, polls: List<Anketa>) : String?{
        return withContext(Dispatchers.IO) {
            try{
                val db = AppDatabase.getInstance(context)
                db.anketaDao().delete()
                //db.anketaDao().insertAll(polls)
                for(poll in polls)
                    db.anketaDao().insert(poll)
                return@withContext "success"
            }
            catch(error:Exception){
                return@withContext null
            }
        }
    }

    /*suspend fun writePolls(context: Context) : String?{
        return withContext(Dispatchers.IO) {
            try{
                val polls = getAll()
                val db = AppDatabase.getInstance(context)
                db.anketaDao().delete()
                //db.anketaDao().insertAll(polls)
                for(poll in polls)
                    db.anketaDao().insert(poll)
                return@withContext "success"
            }
            catch(error:Exception){
                return@withContext null
            }
        }
    }*/

    suspend fun updatePoll(context: Context, idAnketaTaken: Int, progres: Int) {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            val idAnketa = db.anketaTakenDao().getAll().find { at -> at.id == idAnketaTaken }!!.AnketumId
            if(progres == 100)
                db.anketaDao().update(idAnketa, progres / 100F, Anketa.Stanje.DONE)
            else
                db.anketaDao().update(idAnketa, progres / 100F, Anketa.Stanje.ACTIVE)
        }
    }

    suspend fun getAll(context: Context) : List<Anketa> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            val polls = db.anketaDao().getAll()
            return@withContext polls
        }
    }

    suspend fun getAll(): List<Anketa> {
        val ankete = arrayListOf<Anketa>()
        var i = 0
        while(true) {
            var brojAnketa: Int
            withContext(Dispatchers.IO) {
                i++
                val response = ApiAdapter.retrofit.getPolls(i).body()
                //val polls = response.body()
                //if(polls!!.isEmpty() && i == 0) return@withContext listOf()
                //if(polls.isEmpty() && i != 0) break
                //ankete.addAll(polls)
                brojAnketa = response!!.size
                ankete.addAll(response)
            }
            if(brojAnketa != 5)
                break
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
            val anketaTaken: AnketaTaken? = TakeAnketaRepository.getAnketaTaken(a.id)
            if(anketaTaken != null) {
                a.datumRada = anketaTaken.datumRada
                a.progres = anketaTaken.progres / 100f
            }
            a.postaviStanje()
            if(a.progres == -1f) a.progres = 0f
        }
            //ApiAdapter.retrofit.delete(AccountRepository.acHash)
        return ankete
            //return@withContext ankete
        //}
    }

    suspend fun getDone(context: Context): List<Anketa> {
        return getUpisane(context).filter { anketa -> anketa.stanje == Anketa.Stanje.DONE }
    }

    suspend fun getDone(): List<Anketa> {
        return getUpisane().filter { anketa -> anketa.stanje == Anketa.Stanje.DONE }
    }

    suspend fun getFuture(context: Context): List<Anketa> {
        return getUpisane(context).filter { anketa -> anketa.stanje == Anketa.Stanje.ACTIVE || anketa.stanje == Anketa.Stanje.NOTSTARTEDYET }
    }

    suspend fun getFuture(): List<Anketa> {
        return getUpisane().filter { anketa -> anketa.stanje == Anketa.Stanje.ACTIVE || anketa.stanje == Anketa.Stanje.NOTSTARTEDYET }
    }

    suspend fun getNotTaken(context: Context): List<Anketa> {
        return getUpisane(context).filter { anketa -> anketa.stanje == Anketa.Stanje.ENDED }
    }

    suspend fun getNotTaken(): List<Anketa> {
        return getUpisane().filter { anketa -> anketa.stanje == Anketa.Stanje.ENDED }
    }

    suspend fun getAll(offset: Int): List<Anketa> {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getPolls(offset)
            if(response.body().isNullOrEmpty()) return@withContext listOf()
            return@withContext response.body()!!
        }
    }

    suspend fun getById(id: Int): Anketa? {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getPollById(id)
            return@withContext response.body()
        }
    }

    suspend fun getUpisane(context: Context): List<Anketa> {
        /*return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            val anketaTakens = db.anketaTakenDao().getAll()
            val anketeIds = anketaTakens.map { a -> a.AnketumId }
            var upisaneAnkete = getAll(context)
            upisaneAnkete = upisaneAnkete.filter { a -> anketeIds.contains(a.id) }*/
        val upisaneAnkete = arrayListOf<Anketa>()
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            val ankete = getAll(context)
            val upisaneGrupe = IstrazivanjeIGrupaRepository.getUpisaneGrupe(context)
            for (i in upisaneGrupe) {
                //val istrazivanjeGrupa = db.istrazivanjeDao().getOne(i.IstrazivanjeId)
                //val anketeGrupa = ankete.filter { a -> a.nazivIstrazivanja.contains(istrazivanjeGrupa.naziv) }
                val anketeGrupa = db.anketaGrupaDao().getAll().filter { ag -> ag.GrupaId == i.id }
                for (j in anketeGrupa) {
                    val anketa = ankete.find { a -> a.id == j.AnketumId }
                    if(!upisaneAnkete.contains(anketa))
                        upisaneAnkete.add(anketa!!)
                        //upisaneAnkete.add(ankete.find { anketa -> anketa.id == j.id }!!)
                }
            }
            return@withContext upisaneAnkete
        }
    }

    suspend fun getUpisane(): List<Anketa> {
        val upisaneAnkete = arrayListOf<Anketa>()
        val upisaneGrupe = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
        return withContext(Dispatchers.IO) {
            val ankete = getAll()
            for (i in upisaneGrupe) {
                val anketeGrupa = ApiAdapter.retrofit.getPollsByGroup(i.id).body()
                for (j in anketeGrupa!!) {
                    if(!upisaneAnkete.contains(j))
                    upisaneAnkete.add(ankete.find { anketa -> anketa.id == j.id }!!)
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

    suspend fun writeAnketaGrupa(context: Context) : String?{
        return withContext(Dispatchers.IO) {
            try{
                val db = AppDatabase.getInstance(context)
                db.anketaGrupaDao().delete()
                val ankete = getAll(context)
                //val anketeGrupe = arrayListOf<AnketaGrupa>()
                for(a in ankete) {
                    val grupe = ApiAdapter.retrofit.getGroupsForPoll(a.id).body()
                    for(g in grupe!!) {
                        //anketeGrupe.add(AnketaGrupa(i, g.id, a.id))
                        db.anketaGrupaDao().insert(AnketaGrupa(g.id, a.id))
                    }
                }
                //for(anketaGrupa in anketeGrupe)
                  //  db.anketaGrupaDao().insert(anketaGrupa)
                return@withContext "success"
            }
            catch(error:Exception){
                return@withContext null
            }
        }
    }
}
