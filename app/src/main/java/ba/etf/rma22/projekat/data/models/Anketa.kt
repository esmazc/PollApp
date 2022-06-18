package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class Anketa(
    @PrimaryKey @SerializedName("id") val id: Int,
    @ColumnInfo(name = "naziv") @SerializedName("naziv") val naziv: String,
    @ColumnInfo(name = "datumPocetak") @SerializedName("datumPocetak") val datumPocetak: Date,
    @ColumnInfo(name = "datumKraj") @SerializedName("datumKraj") val datumKraj: Date?,
    @ColumnInfo(name = "trajanje") @SerializedName("trajanje") val trajanje: Int,
    var nazivIstrazivanja: String = "",
    var datumRada: Date? = null,
    var progres: Float = 0f,
    var stanje: Stanje = Stanje.ACTIVE
) {

    enum class Stanje(val name1: String) {
        DONE("plava"),
        ACTIVE("zelena"),
        ENDED("crvena"),
        NOTSTARTEDYET("zuta")
    }

    fun postaviStanje() {
        val now = Date()
        when {
            progres == 1f -> stanje = Stanje.DONE
            datumPocetak.after(now) -> stanje = Stanje.NOTSTARTEDYET
            datumKraj == null || datumKraj.after(now) -> stanje = Stanje.ACTIVE
            datumKraj.before(now) && progres == -1f -> stanje = Stanje.ENDED
            datumKraj.before(now) -> stanje = Stanje.DONE  //ended
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Anketa

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}