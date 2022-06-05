package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName
import java.util.*

class AnketaTaken(
    @SerializedName("id") val id: Int,
    @SerializedName("sudent") var student: String,
    @SerializedName("progres") var progres: Float,
    @SerializedName("datumRada") var datumRada: Date?,
    @SerializedName("AnketumId") var AnketumId: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AnketaTaken

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}