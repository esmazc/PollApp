package ba.etf.rma22.projekat.data.converters

import androidx.room.TypeConverter

object OpcijeConverter {

    @TypeConverter
    fun toOpcije(opcije: String?): List<String>? {
        if (opcije == null) return null
        return opcije.split(",")
    }

    @TypeConverter
    fun fromOpcije(opcije: List<String>?): String? {
        if (opcije == null) return null
        return opcije.joinToString(",")
    }
}