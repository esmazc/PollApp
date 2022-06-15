package ba.etf.rma22.projekat.data.dao

import androidx.room.*
import ba.etf.rma22.projekat.data.models.AnketaTaken

@Dao
interface AnketaTakenDAO {
    @Transaction
    @Query("SELECT * FROM AnketaTaken")
    suspend fun getAll(): List<AnketaTaken>
    @Query("SELECT * FROM AnketaTaken WHERE AnketumId=:anketumId")
    suspend fun getForPoll(anketumId: Int): AnketaTaken?
    @Insert
    suspend fun insert(vararg anketaTaken: AnketaTaken)
    @Query("UPDATE AnketaTaken SET progres=:progres WHERE id=:idAnketaTaken")
    suspend fun update(idAnketaTaken: Int, progres: Int)
    @Query("DELETE FROM AnketaTaken")
    suspend fun delete()
}