package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import ba.etf.rma22.projekat.InternetConnectivity
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.AccountRepository.Companion.acHash as hash
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class IstrazivanjeIGrupaRepository {
    companion object {
        private lateinit var context: Context
        //private val hash = AccountRepository.acHash

        fun setContext(_context: Context) {
            context = _context
        }

        suspend fun writeResearchesAndGroups(): String? {
            return withContext(Dispatchers.IO) {
                try {
                    val researches = getIstrazivanja()
                    writeResearches(researches)
                    val groups = getGrupe()
                    writeGroups(groups)
                    return@withContext "success"
                } catch (error: Exception) {
                    return@withContext null
                }
            }
        }

        suspend fun writeResearches(researches: List<Istrazivanje>): String? {
            return withContext(Dispatchers.IO) {
                try {
                    val db = AppDatabase.getInstance(context)
                    db.istrazivanjeDao().delete()
                    for (researche in researches)
                        db.istrazivanjeDao().insert(researche)
                    return@withContext "success"
                } catch (error: Exception) {
                    return@withContext null
                }
            }
        }

        suspend fun writeGroups(groups: List<Grupa>): String? {
            return withContext(Dispatchers.IO) {
                try {
                    val upisaneGrupe = getUpisaneGrupe()
                    val db = AppDatabase.getInstance(context)
                    db.grupaDao().delete()
                    for (group in groups) {
                        if (upisaneGrupe.contains(group))
                            group.upisana = 1
                        db.grupaDao().insert(group)
                    }
                    return@withContext "success"
                } catch (error: Exception) {
                    return@withContext null
                }
            }
        }

        suspend fun getIstrazivanja(): List<Istrazivanje> {
            return withContext(Dispatchers.IO) {
                if (InternetConnectivity.isOnline(context)) {
                    val istrazivanja = arrayListOf<Istrazivanje>()
                    var i = 0
                    while (true) {
                        i++
                        val response = ApiAdapter.retrofit.getResearches(i)
                        val researches = response.body()
                        if (researches!!.isEmpty() && i == 0) return@withContext listOf()
                        if (researches.isEmpty() && i != 0) break
                        istrazivanja.addAll(researches)
                    }
                    return@withContext istrazivanja
                } else {
                    val db = AppDatabase.getInstance(context)
                    val researches = db.istrazivanjeDao().getAll()
                    return@withContext researches
                }
            }
        }

        suspend fun getIstrazivanja(offset: Int): List<Istrazivanje> {
            return withContext(Dispatchers.IO) {
                val response = ApiAdapter.retrofit.getResearches(offset)
                if (response.body().isNullOrEmpty()) return@withContext listOf()

                val db = AppDatabase.getInstance(context)
                db.istrazivanjeDao().delete()
                for(istr in response.body()!!)
                    db.istrazivanjeDao().insert(istr)

                return@withContext response.body()!!
            }
        }

        suspend fun getGrupe(): List<Grupa> {
            return withContext(Dispatchers.IO) {
                if(InternetConnectivity.isOnline(context)) {
                    val response = ApiAdapter.retrofit.getGroups()
                    if (response.body().isNullOrEmpty()) return@withContext listOf()
                    return@withContext response.body()!!
                }
                else {
                    val db = AppDatabase.getInstance(context)
                    val groups = db.grupaDao().getAll()
                    return@withContext groups
                }
            }
        }

        suspend fun getGrupeZaIstrazivanje(idIstrazivanja: Int): List<Grupa> {
            val grupe = getGrupe()
            return grupe.filter { g -> g.IstrazivanjeId == idIstrazivanja }
        }

        suspend fun upisiUGrupu(idGrupa: Int): Boolean {
            return withContext(Dispatchers.IO) {
                val response = ApiAdapter.retrofit.enrollGroup(idGrupa, hash)
                return@withContext !response.message().contains("Grupa not found")
            }
        }

        suspend fun getUpisaneGrupe(): List<Grupa> {
            return withContext(Dispatchers.IO) {
                if(InternetConnectivity.isOnline(context)) {
                    val response = ApiAdapter.retrofit.getEnrolledGroups(hash)
                    if (response.body().isNullOrEmpty()) return@withContext listOf()
                    return@withContext response.body()!!
                }
                else {
                    val db = AppDatabase.getInstance(context)
                    var upisaneGrupe = db.grupaDao().getAll()
                    upisaneGrupe = upisaneGrupe.filter { g -> g.upisana == 1 }
                    return@withContext upisaneGrupe
                }
            }
        }

        suspend fun getIstrazivanjeZaGrupu(idGrupa: Int): Istrazivanje {
            return withContext(Dispatchers.IO) {
                if(InternetConnectivity.isOnline(context)) {
                    val response = ApiAdapter.retrofit.getResearcheByGroup(idGrupa)
                    return@withContext response.body()!!
                }
                else {
                    val db = AppDatabase.getInstance(context)
                    val grupa = db.grupaDao().getOne(idGrupa)
                    val istrazivanje = db.istrazivanjeDao().getOne(grupa.IstrazivanjeId)
                    return@withContext istrazivanje
                }
            }
        }

        suspend fun getIstrazivanjeByGodina(godina: Int) : List<Istrazivanje> {
            var istrazivanja = getIstrazivanja()
            istrazivanja = istrazivanja.filter { istrazivanje -> istrazivanje.godina == godina }
            return istrazivanja
        }

        suspend fun getUpisani() : List<Istrazivanje> {
            val upisanaIstrazivanja = arrayListOf<Istrazivanje>()
            val upisaneGrupe = getUpisaneGrupe()
            for(i in upisaneGrupe) {
                val istr = getIstrazivanjeZaGrupu(i.id)
                if(!upisanaIstrazivanja.contains(istr))
                    upisanaIstrazivanja.add(istr)
            }
            if(upisanaIstrazivanja.isEmpty()) return listOf()
            return upisanaIstrazivanja
        }
    }
}