package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.staticdata.groups

object GrupaRepository {

    fun getGroupsByIstrazivanje(nazivIstrazivanja: String): List<Grupa> {
        val groupsByIstrazivanje: ArrayList<Grupa> = arrayListOf()
        val groups: List<Grupa> = groups()
        for(grupa: Grupa in groups) {
            if(grupa.nazivIstrazivanja == nazivIstrazivanja)
                groupsByIstrazivanje.add(grupa)
        }
        return groupsByIstrazivanje
    }
}