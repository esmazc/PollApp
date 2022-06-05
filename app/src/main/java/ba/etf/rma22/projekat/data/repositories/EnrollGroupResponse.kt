package ba.etf.rma22.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class EnrollGroupResponse(
    @SerializedName("message") val message: String
)