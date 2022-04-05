package ba.etf.rma22.projekat.data

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import java.util.*

fun researches(): List<Istrazivanje> {
    return listOf(
        Istrazivanje("Istraživanje broj 1", 3),
        Istrazivanje("Istraživanje broj 2", 1),
        Istrazivanje("Istraživanje broj 3", 3),
        Istrazivanje("Istraživanje broj 4", 1),
        Istrazivanje("Istraživanje broj 5", 4)
    )
}

fun groups(): List<Grupa> {
    return listOf(
        Grupa("Grupa1", "Istraživanje broj 1"),
        Grupa("Grupa2", "Istraživanje broj 1"),
        Grupa("Grupa1", "Istraživanje broj 2"),
        Grupa("Grupa2", "Istraživanje broj 2"),
        Grupa("Grupa1", "Istraživanje broj 3"),
        Grupa("Grupa2", "Istraživanje broj 3"),
        Grupa("Grupa1", "Istraživanje broj 4"),
        Grupa("Grupa2", "Istraživanje broj 4"),
        Grupa("Grupa3", "Istraživanje broj 4"),
        Grupa("Grupa1", "Istraživanje broj 5"),
        Grupa("Grupa2", "Istraživanje broj 5")
    )
}

fun polls(): List<Anketa> {

    val cal1: Calendar = Calendar.getInstance()
    cal1.set(2022,1,10)
    val date1: Date = cal1.time

    val cal2: Calendar = Calendar.getInstance()
    cal2.set(2022,7,15)
    val date2: Date = cal2.time

    val cal3: Calendar = Calendar.getInstance()
    cal3.set(2022,2,22)
    val date3: Date = cal3.time

    val cal4: Calendar = Calendar.getInstance()
    cal4.set(2022,6,27)
    val date4: Date = cal4.time

    return listOf(
        Anketa("Anketa 1", "Istraživanje broj 1", date1, date2, date3, 3,"Grupa1", 1.0F),
        Anketa("Anketa 2", "Istraživanje broj 1", date1, date3, null, 5,"Grupa2", 0.55F),
        Anketa("Anketa 1", "Istraživanje broj 1", date1, date4, null, 3,"Grupa2", 0.25F),
        Anketa("Anketa 1", "Istraživanje broj 2", date1, date2, null, 3,"Grupa1", 0.0F),
        Anketa("Anketa 1", "Istraživanje broj 2", date2, date2, null, 1,"Grupa2", 0.0F),
        Anketa("Anketa 1", "Istraživanje broj 3", date1, date4, null, 2,"Grupa1", 0.0F),
        Anketa("Anketa 1", "Istraživanje broj 3", date1, date3, null, 3,"Grupa2", 0.0F),
        Anketa("Anketa 2", "Istraživanje broj 3", date4, date4, null, 2,"Grupa2", 0.0F),
        Anketa("Anketa 1", "Istraživanje broj 4", date4, date2, null, 2,"Grupa1", 0.0F),
        Anketa("Anketa 1", "Istraživanje broj 4", date1, date3, null, 4,"Grupa2", 0.0F),
        Anketa("Anketa 1", "Istraživanje broj 4", date3, date2, null, 1,"Grupa3", 0.0F),
        Anketa("Anketa 1", "Istraživanje broj 5", date4, date2, null, 2,"Grupa1", 0.0F),
        Anketa("Anketa 1", "Istraživanje broj 5", date3, date4, date3, 4,"Grupa2", 0.0F),
        Anketa("Anketa 2", "Istraživanje broj 5", date1, date4, date1, 2,"Grupa1", 1.0F),
        Anketa("Anketa 3", "Istraživanje broj 5", date1, date3, date3, 4,"Grupa1", 1.0F)
    )

}
