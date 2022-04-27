package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.staticdata.polls
import kotlin.collections.ArrayList

object AnketaRepository {
    private val korisnik = KorisnikRepository.getUser()

    fun getAll(): List<Anketa> {
        //return polls()
        return polls
    }

    fun getMyAnkete(): List<Anketa> {
        val myAnkete: ArrayList<Anketa> = arrayListOf()
        //val polls: List<Anketa> = polls()
        val polls: List<Anketa> = polls
        for(anketa: Anketa in polls) {
            if(korisnik.parovi.contains(Pair(anketa.nazivIstrazivanja, anketa.nazivGrupe)))
                myAnkete.add(anketa)
        }
        return myAnkete
    }

    fun getDone(): List<Anketa> {
        val myDone: ArrayList<Anketa> = arrayListOf()
        val myAnkete: List<Anketa> = getMyAnkete()
        for(anketa: Anketa in myAnkete) {
            if(anketa.stanje == Anketa.Stanje.DONE)
                myDone.add(anketa)
        }
        return myDone
    }

    fun getFuture(): List<Anketa> {
        val myFuture: ArrayList<Anketa> = arrayListOf()
        val myAnkete: List<Anketa> = getMyAnkete()
        for(anketa: Anketa in myAnkete) {
            if(anketa.stanje == Anketa.Stanje.ACTIVE || anketa.stanje == Anketa.Stanje.NOTSTARTEDYET)
                myFuture.add(anketa)
        }
        return myFuture
    }

    fun getNotTaken(): List<Anketa> {
        val myNotTaken: ArrayList<Anketa> = arrayListOf()
        val myAnkete: List<Anketa> = getMyAnkete()
        for(anketa: Anketa in myAnkete) {
            if(anketa.stanje == Anketa.Stanje.ENDED)
                myNotTaken.add(anketa)
        }
        return myNotTaken
    }
}
