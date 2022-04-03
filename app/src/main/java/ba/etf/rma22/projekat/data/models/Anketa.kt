package ba.etf.rma22.projekat.data.models

import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.util.*

/*class Anketa {
    val naziv: String
    val nazivIstrazivanja: String
    val datumPocetak: Date
    val datumKraj: Date
    val datumRada: Date?
    val trajanje: Int
    val nazivGrupe: String
    val progres: Float
*/
data class Anketa(
    val naziv: String,
    val nazivIstrazivanja: String,
    val datumPocetak: Date,
    val datumKraj: Date,
    val datumRada: Date?,
    val trajanje: Int,
    val nazivGrupe: String,
    val progres: Float
) {
    var stanje: Stanje = Stanje.ACTIVE

    enum class Stanje(val name1: String) {
        DONE("plava"),
        ACTIVE("zelena"),
        ENDED("crvena"),
        NOTSTARTEDYET("zuta")
    }

    init {
        if(progres < 0 || progres > 1) throw IllegalArgumentException()
        if(datumPocetak > datumKraj || (datumRada != null && (datumRada < datumPocetak || datumRada > datumKraj))) throw IllegalArgumentException()
        val localDateTime = LocalDateTime.now()
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(localDateTime.year, localDateTime.monthValue, localDateTime.dayOfMonth)
        val now: Date = calendar.time
        if(datumRada != null) stanje = Stanje.DONE
        else if(datumPocetak.after(now)) stanje = Stanje.NOTSTARTEDYET
        else if(datumKraj.before(now)) stanje = Stanje.ENDED
        else if(datumKraj.after(now)) stanje = Stanje.ACTIVE
    }

    /*constructor(naziv: String, nazivIstrazivanja: String, datumPocetak: Date, datumKraj: Date,
                datumRada: Date?, trajanje: Int, nazivGrupe: String, progres: Float) {
        if(progres < 0 || progres > 1) throw IllegalArgumentException()
        if(datumPocetak > datumKraj || (datumRada != null && (datumRada < datumPocetak || datumRada > datumKraj))) throw IllegalArgumentException()
        this.naziv = naziv
        this.nazivIstrazivanja = nazivIstrazivanja
        this.datumPocetak = datumPocetak
        this.datumKraj = datumKraj
        this.datumRada = datumRada
        this.trajanje = trajanje
        this.nazivGrupe = nazivGrupe
        this.progres = progres
        val localDateTime = LocalDateTime.now()
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(localDateTime.year, localDateTime.monthValue, localDateTime.dayOfMonth)
        val now: Date = calendar.time
        if(datumRada != null) stanje = Stanje.DONE
        else if(datumPocetak.after(now)) stanje = Stanje.NOTSTARTEDYET
        else if(datumKraj.before(now)) stanje = Stanje.ENDED
        else if(datumKraj.after(now)) stanje = Stanje.ACTIVE
    }*/

    constructor(naziv: String, nazivIstrazivanja: String, datumPocetak: Date, datumKraj: Date,
                datumRada: Date?, trajanje: Int, nazivGrupe: String, progres: Float, stanje: Stanje):
            this(naziv, nazivIstrazivanja, datumPocetak, datumKraj, datumRada, trajanje, nazivGrupe, progres) {
        if(stanje != this.stanje) throw IllegalArgumentException()
    }
}