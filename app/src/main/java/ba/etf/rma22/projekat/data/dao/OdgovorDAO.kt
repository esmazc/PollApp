package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ba.etf.rma22.projekat.data.models.Odgovor

@Dao
interface OdgovorDAO {
    @Transaction
    @Query("SELECT * FROM Odgovor")
    suspend fun getAll(): List<Odgovor>
    @Query("SELECT * FROM Odgovor WHERE id=:id")
    suspend fun getOne(id: Int): Odgovor
    @Insert
    suspend fun insert(vararg odgovor: Odgovor)
    @Query("DELETE FROM Odgovor")
    suspend fun delete()
}