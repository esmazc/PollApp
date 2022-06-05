package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName

data class Istrazivanje (
    @SerializedName("id") val id: Int,
    @SerializedName("naziv") val naziv: String,
    @SerializedName("godina") val godina: Int
) {

    /*init {
        if(godina < 1 || godina > 5) throw IllegalArgumentException()
    }*/

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Istrazivanje

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    override fun toString(): String {
        return naziv
    }
}
