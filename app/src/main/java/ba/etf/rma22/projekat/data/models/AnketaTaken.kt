package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class AnketaTaken(
    @PrimaryKey @SerializedName("id") val id: Int,
    @ColumnInfo(name = "student") @SerializedName("student") var student: String,
    @ColumnInfo(name = "progres") @SerializedName("progres") var progres: Float,
    @ColumnInfo(name = "datumRada") @SerializedName("datumRada") var datumRada: Date?,
    @ColumnInfo(name = "AnketumId") @SerializedName("AnketumId") var AnketumId: Int
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