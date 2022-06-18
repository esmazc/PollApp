package ba.etf.rma22.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Account(
    //@PrimaryKey @SerializedName("id") val id: Int,
    //@ColumnInfo(name = "email") @SerializedName("student") val email: String,
    @PrimaryKey @ColumnInfo(name = "acHash") @SerializedName("acHash") val hash: String
)