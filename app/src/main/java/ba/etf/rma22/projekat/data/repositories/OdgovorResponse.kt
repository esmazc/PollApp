package ba.etf.rma22.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class OdgovorResponse(
    @SerializedName("odgovoreno") var odgovoreno: Int,
    @SerializedName("AnketaTakenId") val anketaTakenId: Int,
    @SerializedName("PitanjeId") val pitanjeId: Int
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OdgovorResponse

        if (pitanjeId != other.pitanjeId) return false
        if (anketaTakenId != other.anketaTakenId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pitanjeId
        result = 31 * result + anketaTakenId
        return result
    }
}
