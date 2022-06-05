package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AccountRepository {
    var acHash: String = "4b9f0bb9-214f-4c5f-88d2-a69941de67be"

    fun postaviHash(acHash: String): Boolean {
        this.acHash = acHash
        return true
    }

    fun getHash(): String {
        return acHash
    }

    suspend fun getAccount(hash: String): Account {
        return withContext(Dispatchers.IO) {
            val response = ApiAdapter.retrofit.getAccount(hash)
            return@withContext response.body()!!
        }
    }
}