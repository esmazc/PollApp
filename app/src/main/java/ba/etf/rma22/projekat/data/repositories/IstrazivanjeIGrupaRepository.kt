package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


object IstrazivanjeIGrupaRepository {
    private val hash = AccountRepository.acHash

    suspend fun writeResearchesAndGroups(context: Context): String? {
        return withContext(Dispatchers.IO) {
            try{
                //val db = AppDatabase.getInstance(context)
                val researches = getIstrazivanja()
                //for(researche in researches)
                //    db.istrazivanjeDao().insert(researche)
                writeResearches(context, researches)
                val groups = getGrupe()
                //for(group in groups)
                //    db.grupaDao().insert(group)
                writeGroups(context, groups)
                return@withContext "success"
            }
            catch(error:Exception){
                return@withContext null
            }
        }
    }

    suspend fun writeResearches(context: Context, researches: List<Istrazivanje>) : String?{
        return withContext(Dispatchers.IO) {
            try{
                val db = AppDatabase.getInstance(context)
                db.istrazivanjeDao().delete()
                for(researche in researches)
                    db.istrazivanjeDao().insert(researche)
                return@withContext "success"
            }
            catch(error:Exception){
                return@withContext null
            }
        }
    }

    suspend fun writeGroups(context: Context, groups: List<Grupa>) : String?{
        return withContext(Dispatchers.IO) {
            try{
                val upisaneGrupe = getUpisaneGrupe()
                val db = AppDatabase.getInstance(context)
                db.grupaDao().delete()
                for(group in groups) {
                    if(upisaneGrupe.contains(group))
                        group.upisana = 1
                    db.grupaDao().insert(group)
                }
                return@withContext "success"
            }
            catch(error:Exception){
                return@withContext null
            }
        }
    }

    suspend fun getIstrazivanja(context: Context) : List<Istrazivanje> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            val researches = db.istrazivanjeDao().getAll()
            return@withContext researches
        }
    }

    suspend fun getIstrazivanja(): List<Istrazivanje> {
        val istrazivanja = arrayListOf<Istrazivanje>()
        var i = 0
        return withContext(Dispatchers.IO) {
            while(true) {
                i++
                val response = ApiAdapter.retrofit.getResearches(i)
                val researches = response.body()
                if(researches!!.isEmpty() && i == 0) return@withContext listOf()
                if(researches.isEmpty() && i != 0) break
                istrazivanja.addAll(researches)
            }
            return@withContext istrazivanja
        }
    }

    suspend fun getIstrazivanja(offset: Int): List<Istrazivanje> {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getResearches(offset)
            if(response.body().isNullOrEmpty()) return@withContext listOf()
            return@withContext response.body()!!
        }
    }

    suspend fun getGrupe(context: Context): List<Grupa> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            val groups = db.grupaDao().getAll()
            return@withContext groups
        }
    }

    suspend fun getGrupe(): List<Grupa> {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getGroups()
            if(response.body().isNullOrEmpty()) return@withContext listOf()
            return@withContext response.body()!!
        }
    }

    suspend fun getGrupeZaIstrazivanje(context: Context, idIstrazivanja: Int): List<Grupa> {
//        return withContext(Dispatchers.IO) {
            val grupe = getGrupe(context)
//            val grupeZaIstrazivanje = arrayListOf<Grupa>()
//            val db = AppDatabase.getInstance(context)
//            for(i in grupe) {
//                //val istr = db.istrazivanjeDao().getOne(i.IstrazivanjeId)
//                if(istr.id == idIstrazivanja)
//                    grupeZaIstrazivanje.add(i)
//            }
//            return@withContext grupeZaIstrazivanje
//            return@withContext grupe.filter { g -> g.IstrazivanjeId == idIstrazivanja }
            return grupe.filter { g -> g.IstrazivanjeId == idIstrazivanja }
//        }
    }

    suspend fun getGrupeZaIstrazivanje(idIstrazivanja: Int): List<Grupa> {
//        return withContext(Dispatchers.IO) {
            val grupe = getGrupe()
//            val grupeZaIstrazivanje = arrayListOf<Grupa>()
//            for(i in grupe) {
//                val istr = ApiAdapter.retrofit.getResearcheByGroup(i.id)
//                if(istr.body()!!.id == idIstrazivanja)
//                    grupeZaIstrazivanje.add(i)
//            }
//            return@withContext grupeZaIstrazivanje
            return grupe.filter { g -> g.IstrazivanjeId == idIstrazivanja }
//        }
    }

    suspend fun upisiUGrupu(idGrupa: Int): Boolean {
        return withContext(Dispatchers.IO) {
            //val response = ApiAdapter.retrofit.enrollGroup(idGrupa, hash)
            //return@withContext !response.message().contains("Grupa not found")
            val response = ApiAdapter.retrofit.enrollGroup(idGrupa, hash).body()
            return@withContext !response!!.message.contains("Grupa not found")
        }
    }

    suspend fun getUpisaneGrupe(context: Context): List<Grupa> {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            var upisaneGrupe = db.grupaDao().getAll()
            upisaneGrupe = upisaneGrupe.filter { g -> g.upisana == 1 }
            return@withContext upisaneGrupe
        }
        //return listOf()
    }

    suspend fun getUpisaneGrupe(): List<Grupa> {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getEnrolledGroups(hash)
            if(response.body().isNullOrEmpty()) return@withContext listOf()
            return@withContext response.body()!!
        }
    }

    suspend fun getIstrazivanjeZaGrupu(context: Context, idGrupa: Int): Istrazivanje {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            val grupa = db.grupaDao().getOne(idGrupa)
            val istrazivanje = db.istrazivanjeDao().getOne(grupa.IstrazivanjeId)
            return@withContext istrazivanje
        }
    }

    suspend fun getIstrazivanjeZaGrupu(idGrupa: Int): Istrazivanje {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getResearcheByGroup(idGrupa)
            return@withContext response.body()!!
        }
    }
}