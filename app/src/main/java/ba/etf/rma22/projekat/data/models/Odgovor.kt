package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Odgovor(
    @PrimaryKey @SerializedName("id") val id: Int,
    @ColumnInfo(name = "odgovoreno") @SerializedName("odgovoreno") var odgovoreno: Int,
    @ColumnInfo(name = "AnketaTakenId") @SerializedName("AnketaTakenId") val anketaTakenId: Int,
    @ColumnInfo(name = "PitanjeId") @SerializedName("PitanjeId") val pitanjeId: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Odgovor

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}
