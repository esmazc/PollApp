package ba.etf.rma22.projekat.data.staticdata

import ba.etf.rma22.projekat.data.models.Anketa
import java.util.*

private val dates = getDates()

val polls: List<Anketa> = listOf(
    Anketa("Anketa 1", "Istraživanje broj 1", dates[0], dates[1], dates[2], 3,"Grupa1", 0.0F),
    Anketa("Anketa 2", "Istraživanje broj 1", dates[0], dates[3], null, 3,"Grupa2", 0.0F),
    Anketa("Anketa 3", "Istraživanje broj 1", dates[0], dates[2], null, 5,"Grupa2", 0.0F),
    Anketa("Anketa 1", "Istraživanje broj 2", dates[0], dates[1], null, 3,"Grupa1", 0.0F),
    Anketa("Anketa 2", "Istraživanje broj 2", dates[1], dates[1], null, 1,"Grupa2", 0.0F),
    Anketa("Anketa 1", "Istraživanje broj 3", dates[0], dates[3], null, 2,"Grupa1", 0.0F),
    Anketa("Anketa 2", "Istraživanje broj 3", dates[0], dates[2], null, 3,"Grupa2", 0.0F),
    Anketa("Anketa 3", "Istraživanje broj 3", dates[3], dates[3], null, 2,"Grupa2", 0.0F),
    Anketa("Anketa 1", "Istraživanje broj 4", dates[3], dates[1], null, 2,"Grupa1", 0.0F),
    Anketa("Anketa 2", "Istraživanje broj 4", dates[2], dates[3], dates[2], 4,"Grupa2", 0.0F),
    Anketa("Anketa 3", "Istraživanje broj 4", dates[0], dates[1], null, 2,"Grupa1", 0.0F),
    Anketa("Anketa 4", "Istraživanje broj 4", dates[0], dates[2], dates[2], 4,"Grupa1", 0.0F),
    Anketa("Anketa 1", "Istraživanje broj 5", dates[3], dates[1], null, 2,"Grupa1", 0.0F),
    Anketa("Anketa 2", "Istraživanje broj 5", dates[0], dates[2], null, 4,"Grupa2", 0.0F),
    Anketa("Anketa 3", "Istraživanje broj 5", dates[2], dates[1], null, 1,"Grupa3", 0.0F)
)

fun getDates(): List<Date> {
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

    return listOf(date1, date2, date3, date4)
}

/*fun polls(): List<Anketa> {

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

    val polls: List<Anketa> = listOf(
        Anketa("Anketa 1", "Istraživanje broj 1", date1, date2, date3, 3,"Grupa1", 1.0F),
        Anketa("Anketa 2", "Istraživanje broj 1", date1, date4, null, 3,"Grupa2", 0.25F),
        Anketa("Anketa 3", "Istraživanje broj 1", date1, date3, null, 5,"Grupa2", 0.55F),
        Anketa("Anketa 1", "Istraživanje broj 2", date1, date2, null, 3,"Grupa1", 0.0F),
        Anketa("Anketa 2", "Istraživanje broj 2", date2, date2, null, 1,"Grupa2", 0.0F),
        Anketa("Anketa 1", "Istraživanje broj 3", date1, date4, null, 2,"Grupa1", 0.0F),
        Anketa("Anketa 2", "Istraživanje broj 3", date1, date3, null, 3,"Grupa2", 0.0F),
        Anketa("Anketa 3", "Istraživanje broj 3", date4, date4, null, 2,"Grupa2", 0.0F),
        Anketa("Anketa 1", "Istraživanje broj 4", date4, date2, null, 2,"Grupa1", 0.0F),
        Anketa("Anketa 2", "Istraživanje broj 4", date1, date3, null, 4,"Grupa2", 0.0F),
        Anketa("Anketa 3", "Istraživanje broj 4", date3, date2, null, 1,"Grupa3", 0.0F),
        Anketa("Anketa 1", "Istraživanje broj 5", date4, date2, null, 2,"Grupa1", 0.0F),
        Anketa("Anketa 2", "Istraživanje broj 5", date3, date4, date3, 4,"Grupa2", 0.0F),
        Anketa("Anketa 3", "Istraživanje broj 5", date1, date4, date1, 2,"Grupa1", 1.0F),
        Anketa("Anketa 4", "Istraživanje broj 5", date1, date3, date3, 4,"Grupa1", 1.0F)
    )

    return polls
}*/