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
import ba.etf.rma22.projekat.InternetConnectivity
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Odgovor
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.viewmodel.PitanjeAnketaViewModel

class FragmentPitanje(private val question: Pitanje, private val poll: Anketa) : Fragment() {
    private lateinit var text: TextView
    private lateinit var answers: ListView
    private lateinit var button: Button
    private lateinit var answersAdapter: ListViewAdapter
    private var flag = -1
    private var odgovoreno = false
    companion object {
        lateinit var context: Context
        var pitanjeAnketaViewModel = PitanjeAnketaViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.pitanje_fragment, container, false)

        text = view.findViewById(R.id.tekstPitanja)
        answers = view.findViewById(R.id.odgovoriLista)
        button = view.findViewById(R.id.dugmeZaustavi)

        text.text = question.tekstPitanja
        //val arrayAdapter: ArrayAdapter<String>? = activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, question.opcije) }
        //answers.adapter = arrayAdapter
        answers.choiceMode = ListView.CHOICE_MODE_SINGLE
        answersAdapter = activity?.let { ListViewAdapter(it) }!!
        answers.adapter = answersAdapter
        answers.setOnItemClickListener { _, _, position, _ ->
            if(poll.stanje == Anketa.Stanje.ACTIVE && !odgovoreno) {
                odgovoreno = true
                flag = position
                answersAdapter.notifyDataSetChanged()
                context?.let {
                    pitanjeAnketaViewModel.getAnketaTaken(poll.id, onSuccess = ::onSuccess, null)
                }
            }
        }

        button.setOnClickListener {
            updateFragmentsAndProgress()
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                updateFragmentsAndProgress()
            }
        })

        return view
    }

    private fun updateFragmentsAndProgress() {
        MainActivity.viewPagerAdapter.removeAll()
        MainActivity.viewPagerAdapter.add(FragmentAnkete())
        MainActivity.viewPagerAdapter.add(FragmentIstrazivanje())
        MainActivity.viewPager.currentItem = 0
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
            answerText.text = question.opcije[p0]

            if(InternetConnectivity.isOnline(context) && flag == p0 && poll.stanje == Anketa.Stanje.ACTIVE && !odgovori.map { odgovor -> odgovor.pitanjeId }.contains(question.id))
                answerText.setTextColor(ContextCompat.getColor(context, R.color.answer_click))
            pitanjeAnketaViewModel.getAnswersForPoll(poll.id, onSuccess = ::onSuccessGetAnswers, null, p0, answerText)

            return rowView
        }
    }

    private var odgovori = listOf<Odgovor>()
    fun onSuccessGetAnswers(answers: List<Odgovor>, position: Int, answerText: TextView) {
        odgovori = answers
        for(o in odgovori) {
            if(o.pitanjeId == question.id) {
                if(o.odgovoreno == position)
                    answerText.setTextColor(ContextCompat.getColor(requireContext(), R.color.answer_click))
            }
        }
    }

    fun onSuccessPostAnswer(progres: Int) {
    }

    fun onSuccess(anketaTaken: AnketaTaken) {
        if(InternetConnectivity.isOnline(requireContext()))
            pitanjeAnketaViewModel.postAnswer(anketaTaken.id, question.id, flag, onSuccess = ::onSuccessPostAnswer, null)
    }
}
