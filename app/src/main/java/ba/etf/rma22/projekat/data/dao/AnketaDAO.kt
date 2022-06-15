package ba.etf.rma22.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ba.etf.rma22.projekat.data.models.Anketa

@Dao
interface AnketaDAO {
    @Transaction
    @Query("SELECT * FROM Anketa")
    suspend fun getAll(): List<Anketa>
    @Insert
    suspend fun insert(vararg poll: Anketa)
    //@Insert
    //suspend fun insertAll(vararg polls: List<Anketa>)
    @Query("DELETE FROM Anketa")
    suspend fun delete()
    @Query("UPDATE Anketa SET progres=:progres, stanje=:stanje WHERE id=:idAnketa")
    suspend fun update(idAnketa: Int, progres: Float, stanje: Anketa.Stanje)
}