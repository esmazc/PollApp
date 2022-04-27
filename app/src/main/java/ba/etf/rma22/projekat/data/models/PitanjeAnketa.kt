package ba.etf.rma22.projekat.data.models

data class PitanjeAnketa(
    val naziv: String, //jedinstveni naziv pitanja u okviru ankete u kojoj se nalazi
    val anketa: String, //jedinstveni naziv ankete
    val istrazivanje: String
)