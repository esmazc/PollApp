package ba.etf.rma22.projekat.data.repositories

import android.content.Context
import android.util.Log
import ba.etf.rma22.projekat.data.AppDatabase
import ba.etf.rma22.projekat.data.models.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountRepository {
    companion object {
        var acHash: String = "4b9f0bb9-214f-4c5f-88d2-a69941de67be"
        private lateinit var context:Context

        fun setContext(_context: Context){
            context=_context
        }

        fun getHash(): String {
            return acHash
        }

        suspend fun postaviHash(hash: String): Boolean {
            return withContext(Dispatchers.IO) {
                try {
                    val db = AppDatabase.getInstance(context)
                    val newAccount = Account(hash)
                    val account = db.accountDao().getAccount()
                    if(account == null) {
                        db.accountDao().insert(newAccount)
                        obrisiBazu()
                    }
                    else if(account.hash != hash) {
                        db.accountDao().delete()
                        db.accountDao().insert(newAccount)
                        obrisiBazu()
                    }
                    acHash = hash
                    return@withContext true
                }
                catch(error:Exception){
                    return@withContext false
                }
            }
        }

        suspend fun obrisiBazu() {
            try {
                val db = AppDatabase.getInstance(context)
                db.anketaDao().delete()
                db.anketaGrupaDao().delete()
                db.anketaTakenDao().delete()
                db.grupaDao().delete()
                db.istrazivanjeDao().delete()
                db.odgovorDao().delete()
                db.pitanjeAnketaDao().delete()
                db.pitanjeDao().delete()
            }
            catch(error:Exception){
            }
        }
    }
}