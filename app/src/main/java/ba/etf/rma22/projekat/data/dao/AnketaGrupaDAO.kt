package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ba.etf.rma22.projekat.data.models.AnketaGrupa

@Dao
interface AnketaGrupaDAO {
    @Transaction
    @Query("SELECT * FROM AnketaGrupa")
    suspend fun getAll(): List<AnketaGrupa>
    @Insert
    suspend fun insert(vararg anketaGrupa: AnketaGrupa)
    @Query("DELETE FROM AnketaGrupa")
    suspend fun delete()
}