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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.viewmodel.PitanjeAnketaViewModel
import ba.etf.rma22.projekat.viewmodel.PollListViewModel

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
        pollsAdapter.updatePolls(pollListViewModel.getMyAnkete().sortedBy { poll -> poll.datumPocetak })

        spinner = view.findViewById(R.id.filterAnketa)
        spinner.setSelection(0)
        //spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.filter_anketa, android.R.layout.simple_spinner_dropdown_item)
        //spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                polls.removeAllViews()
                when(spinner.selectedItem.toString()) {
                    "Sve moje ankete" -> pollsAdapter.updatePolls(pollListViewModel.getMyAnkete().sortedBy { poll -> poll.datumPocetak })
                    "Sve ankete" -> pollsAdapter.updatePolls(pollListViewModel.getAll().sortedBy { poll -> poll.datumPocetak })
                    "Urađene ankete" -> pollsAdapter.updatePolls(pollListViewModel.getDone().sortedBy { poll -> poll.datumPocetak })
                    "Buduće ankete" -> pollsAdapter.updatePolls(pollListViewModel.getFuture().sortedBy { poll -> poll.datumPocetak })
                    "Prošle ankete" -> pollsAdapter.updatePolls(pollListViewModel.getNotTaken().sortedBy { poll -> poll.datumPocetak })
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        return view
    }

    private fun showPollsQuestions(poll: Anketa) {
        if(poll.stanje != Anketa.Stanje.NOTSTARTEDYET && pollListViewModel.getMyAnkete().contains(poll)) {
            val questions = pitanjeAnketaViewModel.getPitanja(poll.naziv, poll.nazivIstrazivanja)
            println("xxxx")
            val fragments: ArrayList<Fragment> = arrayListOf()
            for (question: Pitanje in questions)
                fragments.add(FragmentPitanje(question, poll))
            if(fragments.size != 0) {
                MainActivity.viewPagerAdapter.removeAll()
                MainActivity.viewPagerAdapter.addAll(fragments)
                MainActivity.viewPager.currentItem = 0
            }
            //if(poll.stanje == Anketa.Stanje.ACTIVE)
            MainActivity.viewPagerAdapter.add(FragmentPredaj(poll))
        }
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            if(MainActivity.viewPagerAdapter.fragments.size > 1 && MainActivity.viewPagerAdapter.getItem(1) is FragmentPoruka) {
                spinner.setSelection(0)
                pollsAdapter.updatePolls(pollListViewModel.getMyAnkete().sortedBy { poll -> poll.datumPocetak })
                MainActivity.viewPagerAdapter.refresh(1, FragmentIstrazivanje())
            }
        }, 10)
    }

}