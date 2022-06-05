package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName

data class Grupa(
    @SerializedName("id") val id: Int,
    @SerializedName("naziv") val naziv: String,
) {
    val nazivIstrazivanja: String = ""
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Grupa

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
