package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.viewmodel.PitanjeAnketaViewModel
import ba.etf.rma22.projekat.viewmodel.PollListViewModel
import java.util.*
import kotlin.collections.ArrayList

class FragmentAnkete : Fragment() {
    private lateinit var spinner: Spinner
    private lateinit var polls: RecyclerView
    private var pitanjeAnketaViewModel = PitanjeAnketaViewModel()
    //private lateinit var spinnerAdapter: ArrayAdapter<CharSequence>
    companion object {
        lateinit var pollsAdapter: PollListAdapter
        var pollListViewModel = PollListViewModel()
        fun newInstance(): FragmentAnkete = FragmentAnkete()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.ankete_fragment, container, false)

        polls = view.findViewById(R.id.listaAnketa)
        polls.layoutManager = GridLayoutManager(activity, 2)
        pollsAdapter = PollListAdapter(arrayListOf()) { poll -> showPollsQuestions(poll) }
        polls.adapter = pollsAdapter
        //pollsAdapter.updatePolls(pollListViewModel.getMyAnkete().sortedBy { poll -> poll.datumPocetak })

        spinner = view.findViewById(R.id.filterAnketa)
        //spinner.setSelection(0)
        //spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.filter_anketa, android.R.layout.simple_spinner_dropdown_item)
        //spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                polls.removeAllViews()
                when(spinner.selectedItem.toString()) {
                    /*"Sve moje ankete" -> pollsAdapter.updatePolls(pollListViewModel.getMyAnkete().sortedBy { poll -> poll.datumPocetak })
                    "Sve ankete" -> pollsAdapter.updatePolls(pollListViewModel.getAll().sortedBy { poll -> poll.datumPocetak })
                    "Urađene ankete" -> pollsAdapter.updatePolls(pollListViewModel.getDone().sortedBy { poll -> poll.datumPocetak })
                    "Buduće ankete" -> pollsAdapter.updatePolls(pollListViewModel.getFuture().sortedBy { poll -> poll.datumPocetak })
                    "Prošle ankete" -> pollsAdapter.updatePolls(pollListViewModel.getNotTaken().sortedBy { poll -> poll.datumPocetak })*/
                    "Sve moje ankete" -> pollListViewModel.getMyAnkete(onSuccess = ::onSuccess, null)
                    "Sve ankete" -> pollListViewModel.getAll(onSuccess = ::onSuccess, null)
                    "Urađene ankete" -> pollListViewModel.getDone(onSuccess = ::onSuccess, null)
                    "Buduće ankete" -> pollListViewModel.getFuture(onSuccess = ::onSuccess, null)
                    "Prošle ankete" -> pollListViewModel.getNotTaken(onSuccess = ::onSuccess, null)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        return view
    }
    private var questions = listOf<Pitanje>()
    private var poll = Anketa(0, "", Date(), null, 1)
    private var myAnkete = listOf<Anketa>()
    private fun showPollsQuestions(poll: Anketa) {
        this.poll = poll
        if(poll.stanje != Anketa.Stanje.NOTSTARTEDYET && myAnkete.contains(poll)) {
            pollListViewModel.startPoll(poll.id, null, null)
            pitanjeAnketaViewModel.getPitanja(poll.id, onSuccess = ::onSuccessShowPolls, null)
        }
//        if(poll.stanje != Anketa.Stanje.NOTSTARTEDYET && pollListViewModel.getMyAnkete().contains(poll)) {
//            val questions = pitanjeAnketaViewModel.getPitanja(poll.id, null, null)
//            val fragments: ArrayList<Fragment> = arrayListOf()
//            for (question: Pitanje in questions)
//                fragments.add(FragmentPitanje(question, poll))
//            if(fragments.size != 0) {
//                MainActivity.viewPagerAdapter.removeAll()
//                MainActivity.viewPagerAdapter.addAll(fragments)
//                MainActivity.viewPager.currentItem = 0
//            }
//            //if(poll.stanje == Anketa.Stanje.ACTIVE)
//            MainActivity.viewPagerAdapter.add(FragmentPredaj(poll))
//        }
    }

    fun onSuccessShowPolls(pitanja: List<Pitanje>) {
        questions = pitanja
        val fragments: ArrayList<Fragment> = arrayListOf()
        for(question in questions)
            fragments.add(FragmentPitanje(question, poll))
        if(fragments.size != 0) {
            MainActivity.viewPagerAdapter.removeAll()
            MainActivity.viewPagerAdapter.addAll(fragments)
            MainActivity.viewPager.currentItem = 0
        }
//        //if(poll.stanje == Anketa.Stanje.ACTIVE)
        MainActivity.viewPagerAdapter.add(FragmentPredaj(poll))
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            if(MainActivity.viewPagerAdapter.fragments.size > 1 && MainActivity.viewPagerAdapter.getItem(1) is FragmentPoruka) {
                spinner.setSelection(0)
                //pollsAdapter.updatePolls(pollListViewModel.getMyAnkete().sortedBy { poll -> poll.datumPocetak })
                //pollListViewModel.getAll(onSuccess = ::onSuccess)
                //pollsAdapter.updatePolls(list)
                pollListViewModel.getMyAnkete(onSuccess = ::onSuccess, null)
                MainActivity.viewPagerAdapter.refresh(1, FragmentIstrazivanje())
            }
        }, 1000)
    }

    private fun onSuccess(lista: List<Anketa>) {
        pollsAdapter.updatePolls(lista.sortedBy { poll -> poll.datumPocetak })
        if(spinner.selectedItem.toString() == "Sve moje ankete")
            myAnkete = lista
    }

    private fun onSuccessStartPoll(anketaTaken: AnketaTaken){

    }
}