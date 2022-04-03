package ba.etf.rma22.projekat.data.models

/*class Korisnik {
    val naziviIstrazivanja: ArrayList<String>
    val naziviGrupa: ArrayList<String>
*/
data class Korisnik(
    //val naziviIstrazivanja: ArrayList<String>,
    //val naziviGrupa: ArrayList<String>
    val parovi: ArrayList<Pair<String, String>> = arrayListOf()
) {

    //constructor() : this(arrayListOf(), arrayListOf())

    /*constructor(naziviIstrazivanja: ArrayList<String>, naziviGrupa: ArrayList<String>) {
        this.naziviIstrazivanja = naziviIstrazivanja
        this.naziviGrupa = naziviGrupa
    }*/

    /*fun addIstrazivanje(nazivIstrazivanja: String): Unit {
        naziviIstrazivanja.add(nazivIstrazivanja)
    }

    fun addGrupa(nazivGrupe: String) {
        naziviIstrazivanja.add(nazivGrupe)
    }*/
}