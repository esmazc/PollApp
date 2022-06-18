package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ba.etf.rma22.projekat.data.models.Account

@Dao
interface AccountDAO {
    @Transaction
    @Query("SELECT * FROM Account Limit 1")
    suspend fun getAccount(): Account
    @Insert
    suspend fun insert(vararg account: Account)
    @Query("DELETE FROM Account")
    suspend fun delete()
}