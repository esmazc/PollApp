package ba.etf.rma22.projekat.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.InternetConnectivity
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

        spinner = view.findViewById(R.id.filterAnketa)
        //spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.filter_anketa, android.R.layout.simple_spinner_dropdown_item)
        //spinner.adapter = spinnerAdapter
        context?.let {
            //if(InternetConnectivity.isOnline(it))
                pollListViewModel.getMyAnkete(it, onSuccess = ::onSuccess1, null)
            //if(InternetConnectivity.isOnline(it)) {
                //pollListViewModel.writeResearchesAndGroups(it, null, null)
                //pollListViewModel.writeAnketaTakens(it, null, null)
                //pollListViewModel.writeAnketaGrupa(it, null, null)
                //pollListViewModel.writePitanjaIPitanjaAnketa(it, null, null)
                //FragmentPitanje.pitanjeAnketaViewModel.writeOdgovori(it, null, null)
            //}

        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                polls.removeAllViews()
                when(spinner.selectedItem.toString()) {
                    "Sve ankete" -> {
                        context?.let {
                            pollListViewModel.getAll(it, onSuccess = ::onSuccess, null)
                            /*if(InternetConnectivity.isOnline(it)) {
                                pollListViewModel.writeResearchesAndGroups(it, null, null)
                                pollListViewModel.writeAnketaTakens(it, null, null)
                                pollListViewModel.writeAnketaGrupa(it, null, null)
                            }*/
                        }
                    }
                    "Sve moje ankete" -> {
                        context?.let {
                            pollListViewModel.getMyAnkete(it, onSuccess = ::onSuccess, null)
                        }
                    }
                    "Urađene ankete" -> {
                        context?.let {
                            pollListViewModel.getDone(it, onSuccess = ::onSuccess, null)
                        }
                    }
                    "Buduće ankete" -> {
                        context?.let {
                            pollListViewModel.getFuture(it, onSuccess = ::onSuccess, null)
                        }
                    }
                    "Prošle ankete" -> {
                        context?.let {
                            pollListViewModel.getNotTaken(it, onSuccess = ::onSuccess, null)
                        }
                    }
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
            context?.let {
                if(InternetConnectivity.isOnline(it)) {
                    pollListViewModel.startPoll(it, poll.id, null, null)
                    //pollListViewModel.writeAnketaTaken(it, anketaTaken, null, null)
                }
                pitanjeAnketaViewModel.getPitanja(it, poll.id, onSuccess = ::onSuccessShowPolls, null)
            }
        }
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
        //if(poll.stanje == Anketa.Stanje.ACTIVE)
        MainActivity.viewPagerAdapter.add(FragmentPredaj(poll))
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            if(MainActivity.viewPagerAdapter.fragments.size > 1 && MainActivity.viewPagerAdapter.getItem(1) is FragmentPoruka) {
                spinner.setSelection(0)
                context?.let {
                    pollListViewModel.getMyAnkete(it, onSuccess = ::onSuccess1, null)
                    pollListViewModel.getAll(it, onSuccess = ::onSuccess, null)
                }
                MainActivity.viewPagerAdapter.refresh(1, FragmentIstrazivanje())
            }
        }, 1000)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onSuccess(context: Context, ankete: List<Anketa>) {
        pollsAdapter.updatePolls(ankete.sortedBy { poll -> poll.datumPocetak })
        if(spinner.selectedItem.toString() == "Sve moje ankete")
            myAnkete = ankete
        //if(InternetConnectivity.isOnline(context) && spinner.selectedItem.toString() == "Sve ankete") {
            //pollListViewModel.writePolls(context, ankete, null, null)
            //pollListViewModel.writeResearchesAndGroups(context, null, null)
        //}
        pollsAdapter.notifyDataSetChanged()
    }

    private fun onSuccessStartPoll(anketaTaken: AnketaTaken){

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onSuccess1(context: Context, ankete: List<Anketa>) {
        myAnkete = ankete
    }
}