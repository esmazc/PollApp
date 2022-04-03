package ba.etf.rma22.projekat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.view.PollListAdapter
import ba.etf.rma22.projekat.viewmodel.PollListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var polls: RecyclerView
    private lateinit var button: FloatingActionButton
    private var pollListViewModel = PollListViewModel()
    //private lateinit var spinnerAdapter: ArrayAdapter<CharSequence>
    private lateinit var pollsAdapter: PollListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        polls = findViewById(R.id.listaAnketa)
        polls.layoutManager = GridLayoutManager(this, 2)
        pollsAdapter = PollListAdapter(arrayListOf())
        polls.adapter = pollsAdapter
        pollsAdapter.updatePolls(pollListViewModel.getMyAnkete().sortedBy { poll -> poll.datumPocetak })

        spinner = findViewById(R.id.filterAnketa)
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

        button = findViewById(R.id.upisDugme)
        button.setOnClickListener {
            val intent = Intent(this, UpisIstrazivanje::class.java)
            startActivity(intent)
            pollsAdapter.updatePolls(pollListViewModel.getMyAnkete().sortedBy { poll -> poll.datumPocetak })
        }
    }

}
