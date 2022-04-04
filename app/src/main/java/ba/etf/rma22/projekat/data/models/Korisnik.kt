package ba.etf.rma22.projekat.data.models

data class Korisnik(
    //val naziviIstrazivanja: ArrayList<String>,
    //val naziviGrupa: ArrayList<String>
    val parovi: ArrayList<Pair<String, String>> = arrayListOf()
)