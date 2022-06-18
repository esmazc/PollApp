package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ba.etf.rma22.projekat.data.models.Pitanje

@Dao
interface PitanjeDAO {
    @Transaction
    @Query("SELECT * FROM Pitanje")
    suspend fun getAll(): List<Pitanje>
    @Query("SELECT * FROM Pitanje WHERE id=:id")
    suspend fun getOne(id: Int): Pitanje
    @Insert
    suspend fun insert(vararg pitanje: Pitanje)
    @Query("DELETE FROM Pitanje")
    suspend fun delete()
}