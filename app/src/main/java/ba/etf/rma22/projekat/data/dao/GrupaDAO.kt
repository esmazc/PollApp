package ba.etf.rma22.projekat.data.dao

import androidx.room.*
import ba.etf.rma22.projekat.data.models.Grupa

@Dao
interface GrupaDAO {
    @Transaction
    @Query("SELECT * FROM Grupa")
    suspend fun getAll(): List<Grupa>
    @Query("SELECT * FROM Grupa WHERE id=:id")
    suspend fun getOne(id: Int): Grupa
    @Insert
    suspend fun insert(vararg poll: Grupa)
    @Query("DELETE FROM Grupa")
    suspend fun delete()
}