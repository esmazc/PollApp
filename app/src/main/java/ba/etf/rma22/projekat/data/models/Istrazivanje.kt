package ba.etf.rma22.projekat.data.models

import java.lang.IllegalArgumentException

data class Istrazivanje (
    val naziv: String,
    val godina: Int
) {

    init {
        if(godina < 1 || godina > 5) throw IllegalArgumentException()
    }

}
