package ba.etf.rma22.projekat.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import ba.etf.rma22.projekat.InternetConnectivity
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.viewmodel.PitanjeAnketaViewModel

class FragmentPredaj(private val poll: Anketa) : Fragment() {
    private lateinit var progres: TextView
    private lateinit var button: Button
    private var pitanjeAnketaViewModel = PitanjeAnketaViewModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.predaj_fragment, container, false)

        progres = view.findViewById(R.id.progresTekst)
        button = view.findViewById(R.id.dugmePredaj)

        context?.let {
            if(!InternetConnectivity.isOnline(it) || poll.stanje != Anketa.Stanje.ACTIVE)
                button.isEnabled = false
        }
        //if(poll.stanje != Anketa.Stanje.ACTIVE) button.isEnabled = false

        button.setOnClickListener {
//            poll.stanje = Anketa.Stanje.DONE
//            poll.datumRada = Date()
            MainActivity.viewPagerAdapter.removeAll()
            MainActivity.viewPagerAdapter.add(FragmentAnkete())
            MainActivity.viewPagerAdapter.add(FragmentPoruka("Završili ste anketu ${poll.naziv} u okviru istraživanja ${poll.nazivIstrazivanja}"))
            MainActivity.viewPager.currentItem = 1
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                MainActivity.viewPagerAdapter.removeAll()
                MainActivity.viewPagerAdapter.add(FragmentAnkete())
                MainActivity.viewPagerAdapter.add(FragmentIstrazivanje())
                MainActivity.viewPager.currentItem = 0
            }
        })

        return view
    }

    override fun onResume() {
        super.onResume()
        context?.let {
            pitanjeAnketaViewModel.getAnketaTaken(poll.id, onSuccess = ::onSuccess, null)
        }
    }

    @SuppressLint("SetTextI18n")
    fun onSuccess(anketaTaken: AnketaTaken) {
        progres.text = anketaTaken.progres.toInt().toString() + "%"
    }
}
