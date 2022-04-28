package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.models.PitanjeAnketa
import ba.etf.rma22.projekat.data.staticdata.questions
import ba.etf.rma22.projekat.data.staticdata.questionsPolls

object PitanjeAnketaRepository {

    fun getPitanja(nazivAnkete: String, nazivIstrazivanja: String): List<Pitanje> {
        val pitanja: ArrayList<Pitanje> = arrayListOf()
        val listPitanjeAnketa = questionsPolls().filter { poll -> poll.anketa == nazivAnkete && poll.istrazivanje == nazivIstrazivanja }
        for(pitanjeAnketa: PitanjeAnketa in listPitanjeAnketa)
            questions()/*questions*/.find { que -> que.naziv == pitanjeAnketa.naziv }?.let { pitanja.add(it) }
        return pitanja
    }
}