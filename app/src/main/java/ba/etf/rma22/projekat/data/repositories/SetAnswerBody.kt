package ba.etf.rma22.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class SetAnswerBody(
    @SerializedName("odgovor") val odgovorPozicija: Int,
    @SerializedName("pitanje") val pitanjeId: Int,
    @SerializedName("progres") val progres: Int,
)
