package ba.etf.rma22.projekat.data.models

import java.lang.IllegalArgumentException

data class Istrazivanje (
    val naziv: String,
    val godina: Int
) {

    init {
        if(godina < 1 || godina > 5) throw IllegalArgumentException()
    }

    /*override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Istrazivanje

        if (naziv != other.naziv) return false

        return true
    }

    override fun hashCode(): Int {
        return naziv.hashCode()
    }*/
}
