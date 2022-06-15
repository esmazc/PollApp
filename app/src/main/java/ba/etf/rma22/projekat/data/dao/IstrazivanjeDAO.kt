package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ba.etf.rma22.projekat.data.models.Istrazivanje

@Dao
interface IstrazivanjeDAO {
    @Transaction
    @Query("SELECT * FROM Istrazivanje")
    suspend fun getAll(): List<Istrazivanje>
    @Query("SELECT * FROM Istrazivanje WHERE id=:id")
    suspend fun getOne(id: Int): Istrazivanje
    @Insert
    suspend fun insert(vararg poll: Istrazivanje)
    @Query("DELETE FROM Istrazivanje")
    suspend fun delete()
}