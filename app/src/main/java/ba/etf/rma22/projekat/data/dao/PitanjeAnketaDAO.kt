package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ba.etf.rma22.projekat.data.models.PitanjeAnketa

@Dao
interface PitanjeAnketaDAO {
    @Transaction
    @Query("SELECT * FROM PitanjeAnketa")
    suspend fun getAll(): List<PitanjeAnketa>
    @Query("SELECT * FROM PitanjeAnketa WHERE AnketumId=:anketumId")
    suspend fun getForPoll(anketumId: Int): List<PitanjeAnketa>
    @Query("SELECT * FROM PitanjeAnketa WHERE id=:id")
    suspend fun getOne(id: Int): PitanjeAnketa
    @Insert
    suspend fun insert(vararg pitanjeAnketa: PitanjeAnketa)
    //@Update
    //suspend fun update()
    @Query("DELETE FROM PitanjeAnketa")
    suspend fun delete()
}