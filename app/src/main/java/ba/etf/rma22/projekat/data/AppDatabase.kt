package ba.etf.rma22.projekat.data

import android.content.Context
import androidx.room.*
import ba.etf.rma22.projekat.data.converters.DateConverter
import ba.etf.rma22.projekat.data.converters.OpcijeConverter
import ba.etf.rma22.projekat.data.dao.*
import ba.etf.rma22.projekat.data.models.*

@Database(entities = arrayOf(/*Account::class, */Anketa::class, AnketaTaken::class, Grupa::class, Istrazivanje::class, AnketaGrupa::class, Odgovor::class, Pitanje::class, PitanjeAnketa::class),
    version = 2)
@TypeConverters(DateConverter::class, OpcijeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    //abstract fun accountDao(): AccountDAO
    abstract fun anketaDao(): AnketaDAO
    abstract fun anketaTakenDao(): AnketaTakenDAO
    abstract fun grupaDao(): GrupaDAO
    abstract fun istrazivanjeDao(): IstrazivanjeDAO
    abstract fun anketaGrupaDao(): AnketaGrupaDAO
    abstract fun odgovorDao(): OdgovorDAO
    abstract fun pitanjeDao(): PitanjeDAO
    abstract fun pitanjeAnketaDao(): PitanjeAnketaDAO
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }
        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "RMA22DB"
            ).fallbackToDestructiveMigration().build()
    }
}