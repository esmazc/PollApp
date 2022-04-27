package ba.etf.rma22.projekat.data.staticdata

import ba.etf.rma22.projekat.data.models.Pitanje

/*val questions = listOf(
    Pitanje("Pitanje1", "Koji je glavni grad Bosne i Hercegovine?", listOf("Tuzla", "Sarajevo", "Banjaluka")),
    Pitanje("Pitanje2", "Koji je glavni grad Austrije?", listOf("Beč", "Grac", "Salzburg")),
    Pitanje("Pitanje3", "Koji je glavni grad Turske?", listOf("Istanbul", "Ankara", "Izmir")),
    Pitanje("Pitanje4", "Koji je glavni grad Egipta?", listOf("Kairo", "Aleksandrija", "Giza")),
    Pitanje("Pitanje5", "Koji je glavni grad Španije?", listOf("Barcelona", "Valencia", "Madrid"))
)*/

fun questions(): List<Pitanje> {
    return listOf(
        Pitanje("Pitanje1", "Koji je glavni grad Bosne i Hercegovine?", listOf("Tuzla", "Sarajevo", "Banjaluka")),
        Pitanje("Pitanje2", "Koji je glavni grad Austrije?", listOf("Beč", "Grac", "Salzburg")),
        Pitanje("Pitanje3", "Koji je glavni grad Turske?", listOf("Istanbul", "Ankara", "Izmir")),
        Pitanje("Pitanje4", "Koji je glavni grad Egipta?", listOf("Kairo", "Aleksandrija", "Giza")),
        Pitanje("Pitanje5", "Koji je glavni grad Španije?", listOf("Barcelona", "Valencia", "Madrid")),
        Pitanje("Pitanje6", "Koji je glavni grad Bosne i Hercegovine?", listOf("Tuzla", "Sarajevo", "Banjaluka")),
        Pitanje("Pitanje7", "Koji je glavni grad Austrije?", listOf("Beč", "Grac", "Salzburg")),
        Pitanje("Pitanje8", "Koji je glavni grad Turske?", listOf("Istanbul", "Ankara", "Izmir")),
        Pitanje("Pitanje9", "Koji je glavni grad Egipta?", listOf("Kairo", "Aleksandrija", "Giza")),
        Pitanje("Pitanje10", "Koji je glavni grad Španije?", listOf("Barcelona", "Valencia", "Madrid")),
        Pitanje("Pitanje11", "Koji je glavni grad Bosne i Hercegovine?", listOf("Tuzla", "Sarajevo", "Banjaluka")),
        Pitanje("Pitanje12", "Koji je glavni grad Austrije?", listOf("Beč", "Grac", "Salzburg")),
        Pitanje("Pitanje13", "Koji je glavni grad Turske?", listOf("Istanbul", "Ankara", "Izmir")),
        Pitanje("Pitanje14", "Koji je glavni grad Egipta?", listOf("Kairo", "Aleksandrija", "Giza")),
        Pitanje("Pitanje15", "Koji je glavni grad Španije?", listOf("Barcelona", "Valencia", "Madrid")),
        Pitanje("Pitanje16", "Koji je glavni grad Bosne i Hercegovine?", listOf("Tuzla", "Sarajevo", "Banjaluka")),
        Pitanje("Pitanje17", "Koji je glavni grad Austrije?", listOf("Beč", "Grac", "Salzburg")),
        Pitanje("Pitanje18", "Koji je glavni grad Turske?", listOf("Istanbul", "Ankara", "Izmir")),
        Pitanje("Pitanje19", "Koji je glavni grad Egipta?", listOf("Kairo", "Aleksandrija", "Giza")),
        Pitanje("Pitanje20", "Koji je glavni grad Španije?", listOf("Barcelona", "Valencia", "Madrid")),
        Pitanje("Pitanje21", "Koji je glavni grad Bosne i Hercegovine?", listOf("Tuzla", "Sarajevo", "Banjaluka")),
        Pitanje("Pitanje22", "Koji je glavni grad Austrije?", listOf("Beč", "Grac", "Salzburg")),
        Pitanje("Pitanje23", "Koji je glavni grad Turske?", listOf("Istanbul", "Ankara", "Izmir")),
        Pitanje("Pitanje24", "Koji je glavni grad Egipta?", listOf("Kairo", "Aleksandrija", "Giza")),
        Pitanje("Pitanje25", "Koji je glavni grad Španije?", listOf("Barcelona", "Valencia", "Madrid")),
        Pitanje("Pitanje26", "Koji je glavni grad Bosne i Hercegovine?", listOf("Tuzla", "Sarajevo", "Banjaluka")),
        Pitanje("Pitanje27", "Koji je glavni grad Austrije?", listOf("Beč", "Grac", "Salzburg")),
        Pitanje("Pitanje28", "Koji je glavni grad Turske?", listOf("Istanbul", "Ankara", "Izmir")),
        Pitanje("Pitanje29", "Koji je glavni grad Egipta?", listOf("Kairo", "Aleksandrija", "Giza")),
        Pitanje("Pitanje30", "Koji je glavni grad Španije?", listOf("Barcelona", "Valencia", "Madrid")),
        Pitanje("Pitanje31", "Koji je glavni grad Bosne i Hercegovine?", listOf("Tuzla", "Sarajevo", "Banjaluka")),
        Pitanje("Pitanje32", "Koji je glavni grad Austrije?", listOf("Beč", "Grac", "Salzburg")),
        Pitanje("Pitanje33", "Koji je glavni grad Turske?", listOf("Istanbul", "Ankara", "Izmir")),
        Pitanje("Pitanje34", "Koji je glavni grad Egipta?", listOf("Kairo", "Aleksandrija", "Giza")),
        Pitanje("Pitanje35", "Koji je glavni grad Španije?", listOf("Barcelona", "Valencia", "Madrid")),
        Pitanje("Pitanje36", "Koji je glavni grad Bosne i Hercegovine?", listOf("Tuzla", "Sarajevo", "Banjaluka")),
        Pitanje("Pitanje37", "Koji je glavni grad Austrije?", listOf("Beč", "Grac", "Salzburg")),
        Pitanje("Pitanje38", "Koji je glavni grad Turske?", listOf("Istanbul", "Ankara", "Izmir")),
        Pitanje("Pitanje39", "Koji je glavni grad Egipta?", listOf("Kairo", "Aleksandrija", "Giza")),
        Pitanje("Pitanje40", "Koji je glavni grad Španije?", listOf("Barcelona", "Valencia", "Madrid")),
        Pitanje("Pitanje41", "Koji je glavni grad Bosne i Hercegovine?", listOf("Tuzla", "Sarajevo", "Banjaluka")),
        Pitanje("Pitanje42", "Koji je glavni grad Austrije?", listOf("Beč", "Grac", "Salzburg")),
        Pitanje("Pitanje43", "Koji je glavni grad Turske?", listOf("Istanbul", "Ankara", "Izmir")),
        Pitanje("Pitanje44", "Koji je glavni grad Egipta?", listOf("Kairo", "Aleksandrija", "Giza")),
        Pitanje("Pitanje45", "Koji je glavni grad Španije?", listOf("Barcelona", "Valencia", "Madrid"))
    )
}