package ba.etf.rma22.projekat.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.viewmodel.KorisnikViewModel
import ba.etf.rma22.projekat.viewmodel.PitanjeAnketaViewModel

class FragmentPitanje(private val question: Pitanje, private val poll: Anketa) : Fragment() {
    private lateinit var text: TextView
    private lateinit var answers: ListView
    private lateinit var button: Button
    private lateinit var answersAdapter: ListViewAdapter
    private var flag = -1
    private var korisnik = KorisnikViewModel().getUser()
    private var pitanjeAnketaViewModel = PitanjeAnketaViewModel()

    /*companion object {
        fun newInstance(): FragmentPitanje = FragmentPitanje()
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.pitanje_fragment, container, false)

        text = view.findViewById(R.id.tekstPitanja)
        answers = view.findViewById(R.id.odgovoriLista)
        button = view.findViewById(R.id.dugmeZaustavi)

        text.text = question.tekst
        //val arrayAdapter: ArrayAdapter<String>? = activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, question.opcije) }
        //answers.adapter = arrayAdapter
        answers.choiceMode = ListView.CHOICE_MODE_SINGLE
        answersAdapter = activity?.let { ListViewAdapter(it) }!!
        answers.adapter = answersAdapter
        answers.setOnItemClickListener { _, _, position, _ ->
            if(poll.stanje == Anketa.Stanje.ACTIVE) {
                flag = position
                answersAdapter.notifyDataSetChanged()
                //question.izabraniOdgovor = position
                if(!korisnik.odgovori.containsKey(Pair(poll.naziv, poll.nazivIstrazivanja)))
                    korisnik.odgovori.put(Pair(poll.naziv, poll.nazivIstrazivanja), arrayListOf(Pair(question.naziv, question.opcije[position])))
                else {
                    if(korisnik.odgovori.getValue(Pair(poll.naziv, poll.nazivIstrazivanja)).find { pair -> pair.first == question.naziv } != null)  //obrisati ako moze izabrat vise odg - 2 linije
                        korisnik.odgovori.getValue(Pair(poll.naziv, poll.nazivIstrazivanja)).remove(korisnik.odgovori.getValue(Pair(poll.naziv, poll.nazivIstrazivanja)).find { pair -> pair.first == question.naziv })
                    korisnik.odgovori.getValue(Pair(poll.naziv, poll.nazivIstrazivanja)).add(Pair(question.naziv, question.opcije[position]))
                }
                //question.izabraniOdgovor = question.opcije[position]
            }
        }
        /*if(question.izabraniOdgovor != null) {
            //answers.setSelection(question.izabraniOdgovor!!)
            //answers.setItemChecked(question.izabraniOdgovor!!, true)
            /*answers.post(Runnable {
                answers.setSelection(question.izabraniOdgovor!!)
                answersAdapter.notifyDataSetChanged()
            })*/
        }*/

        button.setOnClickListener {          //nesta ne valja kada sam na drugom pitanju
            MainActivity.viewPagerAdapter.removeAll()
            MainActivity.viewPagerAdapter.add(FragmentAnkete())
            MainActivity.viewPagerAdapter.add(FragmentIstrazivanje())
            MainActivity.viewPager.currentItem = 0
            updateProgress()
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                MainActivity.viewPagerAdapter.removeAll()
                MainActivity.viewPagerAdapter.add(FragmentAnkete())
                MainActivity.viewPagerAdapter.add(FragmentIstrazivanje())
                MainActivity.viewPager.currentItem = 0
                updateProgress()
            }
        })

        return view
    }

    private fun updateProgress() {
        var pr = 0F
        if(korisnik.odgovori.containsKey(Pair(poll.naziv, poll.nazivIstrazivanja)))
            pr = korisnik.odgovori.getValue(Pair(poll.naziv, poll.nazivIstrazivanja)).size.toFloat() / pitanjeAnketaViewModel.getPitanja(poll.naziv, poll.nazivIstrazivanja).size
        poll.progres = pr
    }

    inner class ListViewAdapter(private val context: Context) : BaseAdapter() {
        override fun getCount(): Int {
            return question.opcije.size
        }

        override fun getItem(p0: Int): Any {
            return question.opcije[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val rowView: View = View.inflate(context, R.layout.custom_cell_listview, null)

            val answerText: TextView = rowView.findViewById(R.id.answerTxt)
            if(flag == p0 && poll.stanje == Anketa.Stanje.ACTIVE)  //mozda i NOTSTARTEDYET
                answerText.setTextColor(ContextCompat.getColor(context, R.color.answer_click))
            //if(question.izabraniOdgovor != null && question.izabraniOdgovor == question.opcije[p0])
              //  answerText.setTextColor(ContextCompat.getColor(context, R.color.answer_click))
            if(korisnik.odgovori.containsKey(Pair(poll.naziv, poll.nazivIstrazivanja))
                && korisnik.odgovori.getValue(Pair(poll.naziv, poll.nazivIstrazivanja)).contains(Pair(question.naziv, question.opcije[p0])))
                answerText.setTextColor(ContextCompat.getColor(context, R.color.answer_click))
            answerText.text = question.opcije[p0]

            return rowView;
        }
    }
}