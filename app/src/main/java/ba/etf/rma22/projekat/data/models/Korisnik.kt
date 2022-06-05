package ba.etf.rma22.projekat.data.models

data class Korisnik(
    val parovi: ArrayList<Pair<String, String>> = arrayListOf()
) {
    var odgovori: MutableMap<Pair<String, String>, ArrayList<Pair<String, String>>> = mutableMapOf()
}
