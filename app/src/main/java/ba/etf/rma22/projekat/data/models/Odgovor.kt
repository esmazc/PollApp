package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName

data class Odgovor(
    @SerializedName("id") val id: Int,
    @SerializedName("odgovoreno") var odgovoreno: Int
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
