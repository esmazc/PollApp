package ba.etf.rma22.projekat.data.models

import ba.etf.rma22.projekat.data.repositories.TakeAnketaRepository
import com.google.gson.annotations.SerializedName
import java.lang.IllegalArgumentException
import java.util.*

data class Anketa(
    @SerializedName("id") val id: Int,
    @SerializedName("naziv") val naziv: String,
    @SerializedName("datumPocetak") val datumPocetak: Date,
    @SerializedName("datumKraj") val datumKraj: Date?,
    @SerializedName("trajanje") val trajanje: Int,
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

    //init {
    fun postaviStanje() {
        //if(progres < 0 || progres > 1) throw IllegalArgumentException()
        //if(datumPocetak > datumKraj || (datumRada != null && (datumRada!! < datumPocetak || datumRada!! > datumKraj))) throw IllegalArgumentException()
        val now = Date()
        //val anketaTaken = TakeAnketaRepository.getAnketaTaken(id)
        when {
            //datumRada != null -> stanje = Stanje.DONE
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