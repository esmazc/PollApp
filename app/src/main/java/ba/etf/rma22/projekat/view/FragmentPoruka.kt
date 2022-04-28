package ba.etf.rma22.projekat.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa

class FragmentPoruka(
    private val flag: Boolean,
    private val grupa: Grupa?,
    private val anketa: Anketa?/*,
    private val istrazivanje: Istrazivanje?*/
) : Fragment() {
    private lateinit var message: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.poruka_fragment, container, false)

        message = view.findViewById(R.id.tvPoruka)
        if(flag)
            message.text = "Uspješno ste upisani u grupu ${grupa?.naziv} istraživanja ${grupa?.nazivIstrazivanja}!"
        else
            message.text = "Završili ste anketu ${anketa?.naziv} u okviru istraživanja ${/*istrazivanje?.naziv*/anketa?.nazivIstrazivanja}"
        return view
    }

}