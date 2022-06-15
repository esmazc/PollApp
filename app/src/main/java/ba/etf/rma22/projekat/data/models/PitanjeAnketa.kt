package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class PitanjeAnketa(
    @ColumnInfo(name = "AnketumId") @SerializedName("AnketumId") val AnketumId: Int,
    @ColumnInfo(name = "PitanjeId") @SerializedName("PitanjeId") val PitanjeId: Int
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PitanjeAnketa

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}