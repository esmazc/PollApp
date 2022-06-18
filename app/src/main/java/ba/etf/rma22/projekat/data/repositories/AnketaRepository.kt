package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import ba.etf.rma22.projekat.InternetConnectivity
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaGrupa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnketaRepository {

    companion object {
        private lateinit var context: Context

        fun setContext(_context: Context) {
            context = _context
        }

        suspend fun writePolls(polls: List<Anketa>): String? {
            return withContext(Dispatchers.IO) {
                try {
                    val db = AppDatabase.getInstance(context)
                    db.anketaDao().delete()
                    for (poll in polls)
                        db.anketaDao().insert(poll)
                    return@withContext "success"
                } catch (error: Exception) {
                    return@withContext null
                }
            }
        }


        suspend fun updatePoll(idAnketaTaken: Int, progres: Int) {
            return withContext(Dispatchers.IO) {
                val db = AppDatabase.getInstance(context)
                val idAnketa = db.anketaTakenDao().getAll().find { at -> at.id == idAnketaTaken }!!.AnketumId
                if (progres == 100)
                    db.anketaDao().update(idAnketa, progres / 100F, Anketa.Stanje.DONE)
                else
                    db.anketaDao().update(idAnketa, progres / 100F, Anketa.Stanje.ACTIVE)
            }
        }

        suspend fun getAll(): List<Anketa> {
            return withContext(Dispatchers.IO) {
                if (InternetConnectivity.isOnline(context)) {
                    val ankete = arrayListOf<Anketa>()
                    var i = 0
                    while (true) {
                        i++
                        val response = ApiAdapter.retrofit.getPolls(i)
                        val polls = response.body()
                        if (polls!!.isEmpty() && i == 0) return@withContext listOf()
                        if (polls.isEmpty() && i != 0) break
                        ankete.addAll(polls)
                    }
                    for (a in ankete) {
                        var nazivIstrazivanja = ""
                        var flag = true
                        val grupe = ApiAdapter.retrofit.getGroupsForPoll(a.id).body()
                        for (g in grupe!!) {
                            val istrazivanje = IstrazivanjeIGrupaRepository.getIstrazivanjeZaGrupu(g.id)
                            if (!nazivIstrazivanja.contains(istrazivanje.naziv)) {
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
                        if (anketaTaken != null) {
                            a.datumRada = anketaTaken.datumRada
                            a.progres = anketaTaken.progres / 100f
                        }
                        a.postaviStanje()
                        if (a.progres == -1f) a.progres = 0f
                    }
                    writePolls(ankete)
                    //ApiAdapter.retrofit.delete(AccountRepository.acHash)
                    return@withContext ankete
                }
                else {
                    val db = AppDatabase.getInstance(context)
                    val polls = db.anketaDao().getAll()
                    return@withContext polls
                }
            }
        }

        suspend fun getDone(): List<Anketa> {
            return getUpisane().filter { anketa -> anketa.stanje == Anketa.Stanje.DONE }
        }

        suspend fun getFuture(): List<Anketa> {
            return getUpisane().filter { anketa -> anketa.stanje == Anketa.Stanje.ACTIVE || anketa.stanje == Anketa.Stanje.NOTSTARTEDYET }
        }

        suspend fun getNotTaken(): List<Anketa> {
            return getUpisane().filter { anketa -> anketa.stanje == Anketa.Stanje.ENDED }
        }

        suspend fun getAll(offset: Int): List<Anketa> {
            return withContext(Dispatchers.IO) {
                val response = ApiAdapter.retrofit.getPolls(offset)
                if (response.body().isNullOrEmpty()) return@withContext listOf()
                return@withContext response.body()!!
            }
        }

        suspend fun getById(id: Int): Anketa? {
            return withContext(Dispatchers.IO) {
                val response = ApiAdapter.retrofit.getPollById(id)
                return@withContext response.body()
            }
        }

        suspend fun getUpisane(): List<Anketa> {
            return withContext(Dispatchers.IO) {
                if (InternetConnectivity.isOnline(context)) {
                    val upisaneAnkete = arrayListOf<Anketa>()
                    val upisaneGrupe = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
                    val ankete = getAll()
                    for (i in upisaneGrupe) {
                        val anketeGrupa = ApiAdapter.retrofit.getPollsByGroup(i.id).body()
                        for (j in anketeGrupa!!) {
                            if (!upisaneAnkete.contains(j))
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
                        a.nazivIstrazivanja = nazivIstrazivanja
                    }*/
                    return@withContext upisaneAnkete
                }
                else {
                    val upisaneAnkete = arrayListOf<Anketa>()
                    val db = AppDatabase.getInstance(context)
                    val ankete = getAll()
                    val upisaneGrupe = IstrazivanjeIGrupaRepository.getUpisaneGrupe()
                    for (i in upisaneGrupe) {
                        val anketeGrupa =
                            db.anketaGrupaDao().getAll().filter { ag -> ag.GrupaId == i.id }
                        for (j in anketeGrupa) {
                            val anketa = ankete.find { a -> a.id == j.AnketumId }
                            if (!upisaneAnkete.contains(anketa))
                                upisaneAnkete.add(anketa!!)
                        }
                    }
                    return@withContext upisaneAnkete
                }
            }
        }

        suspend fun writeAnketaGrupa(): String? {
            return withContext(Dispatchers.IO) {
                try {
                    val db = AppDatabase.getInstance(context)
                    db.anketaGrupaDao().delete()
                    val ankete = getAll()
                    for (a in ankete) {
                        val grupe = ApiAdapter.retrofit.getGroupsForPoll(a.id).body()
                        for (g in grupe!!)
                            db.anketaGrupaDao().insert(AnketaGrupa(g.id, a.id))
                    }
                    return@withContext "success"
                } catch (error: Exception) {
                    return@withContext null
                }
            }
        }
    }
}
