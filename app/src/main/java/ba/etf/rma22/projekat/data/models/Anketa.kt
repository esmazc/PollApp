package ba.etf.rma22.projekat.data.models

import java.lang.IllegalArgumentException
import java.util.*

data class Anketa(
    val naziv: String,
    val nazivIstrazivanja: String,
    val datumPocetak: Date,
    val datumKraj: Date,
    var datumRada: Date?,
    val trajanje: Int,
    val nazivGrupe: String,
    var progres: Float
) {
    var stanje: Stanje = Stanje.ACTIVE

    enum class Stanje(val name1: String) {
        DONE("plava"),
        ACTIVE("zelena"),
        ENDED("crvena"),
        NOTSTARTEDYET("zuta")
    }

    init {
        if(progres < 0 || progres > 1) throw IllegalArgumentException()
        if(datumPocetak > datumKraj || (datumRada != null && (datumRada!! < datumPocetak || datumRada!! > datumKraj))) throw IllegalArgumentException()
        val now = Date()
        when {
            datumRada != null -> stanje = Stanje.DONE
            datumPocetak.after(now) -> stanje = Stanje.NOTSTARTEDYET
            datumKraj.before(now) -> stanje = Stanje.ENDED
            datumKraj.after(now) -> stanje = Stanje.ACTIVE
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Anketa

        if (naziv != other.naziv) return false
        if (nazivIstrazivanja != other.nazivIstrazivanja) return false
        if (nazivGrupe != other.nazivGrupe) return false

        return true
    }

    override fun hashCode(): Int {
        var result = naziv.hashCode()
        result = 31 * result + nazivIstrazivanja.hashCode()
        result = 31 * result + nazivGrupe.hashCode()
        return result
    }

}