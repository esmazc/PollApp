package ba.etf.rma22.projekat.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.viewmodel.UpisIstrazivanjeViewModel
import ba.etf.rma22.projekat.viewmodel.KorisnikViewModel
import kotlin.streams.toList

class FragmentIstrazivanje() : Fragment() {
    private lateinit var spinnerGodina: Spinner
    private lateinit var spinnerIstrazivanje: Spinner
    private lateinit var spinnerGrupa: Spinner
    private lateinit var button: Button
    private var upisIstrazivanjeViewModel = UpisIstrazivanjeViewModel()
    private lateinit var spinnerGodinaAdapter: ArrayAdapter<String>
    private lateinit var spinnerIstrazivanjeAdapter: ArrayAdapter<String>
    private lateinit var spinnerGrupaAdapter: ArrayAdapter<String>
    private var korisnik = KorisnikViewModel().getUser()

    companion object {
        fun newInstance(): FragmentIstrazivanje = FragmentIstrazivanje()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.istrazivanje_fragment, container, false)

        spinnerGodina = view.findViewById(R.id.odabirGodina)
        spinnerIstrazivanje = view.findViewById(R.id.odabirIstrazivanja)
        spinnerGrupa = view.findViewById(R.id.odabirGrupa)
        button = view.findViewById(R.id.dodajIstrazivanjeDugme)

        val godineList = listOf("1", "2", "3", "4", "5")
        spinnerGodinaAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, godineList) }!!
        spinnerGodina.adapter = spinnerGodinaAdapter
        val s: String = korisnik.parovi.last().first
        var godina: Int = upisIstrazivanjeViewModel.getAll().find { istrazivanje -> istrazivanje.naziv == s }!!.godina
        spinnerGodina.setSelection(godina - 1)
        var istrazivanje = ""
        var grupa = ""
        spinnerGodina.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                godina = spinnerGodina.selectedItem.toString().toInt()
                val istrazivanjaList = upisIstrazivanjeViewModel.getIstrazivanjeByGodina(godina)
                val x = upisIstrazivanjeViewModel.getUpisani().stream().map { istr -> istr.naziv }.toList()
                val istrazivanjaList1: ArrayList<String> = arrayListOf()
                //***obrisati
                istrazivanjaList1.add("")
                //*****
                for(ist: Istrazivanje in istrazivanjaList)
                    if(!x.contains(ist.naziv))
                        istrazivanjaList1.add(ist.naziv)
                //istrazivanjaList.filter { istrazivanje -> !upisIstrazivanjeViewModel.getUpisani().stream().map { istr -> istr.naziv }.toList().contains(istrazivanje.naziv) }
                //val istrazivanjaList1: List<String> = istrazivanjaList.stream().map { istrazivanje -> istrazivanje.naziv }.toList()
                spinnerIstrazivanjeAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, istrazivanjaList1)
                spinnerIstrazivanje.adapter = spinnerIstrazivanjeAdapter
                //***promijeniti if i izbrisati "" iz listOf
                if(istrazivanjaList1.size == 1) {
                    //if(istrazivanjaList1.isEmpty()) {
                    spinnerGrupaAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, listOf(""))
                    spinnerGrupa.adapter = spinnerGrupaAdapter
                }
                //******
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        spinnerIstrazivanje.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                istrazivanje = spinnerIstrazivanje.selectedItem.toString()
                val grupaList = upisIstrazivanjeViewModel.getGroupsByIstrazivanje(istrazivanje)
                val grupaList1: ArrayList<String> = arrayListOf()
                //***obrisati
                grupaList1.add("")
                //*****
                grupaList1.addAll(grupaList.stream().map { grupa -> grupa.naziv }.toList())
                spinnerGrupaAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, grupaList1)
                spinnerGrupa.adapter = spinnerGrupaAdapter
                button.isEnabled = !(istrazivanje == "" || grupa == "")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        spinnerGrupa.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                grupa = spinnerGrupa.selectedItem.toString()
                button.isEnabled = !(istrazivanje == "" || grupa == "")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        button.setOnClickListener {
            korisnik.parovi.add(Pair(istrazivanje, grupa))
            /*val intent = Intent(activity, MainActivity::class.java)
            //finish()
            startActivity(intent)*/
            /*val grupe = GrupaRepository.getGroupsByIstrazivanje(istrazivanje)
            for (g: Grupa in grupe) {
                if (g.naziv == grupa) {
                    //MainActivity.refreshSecondFragment(g)
                    MainActivity.viewPagerAdapter.refresh(1, FragmentPoruka(g))
                    break
                }
            }*/
            //val group = Grupa(grupa, istrazivanje)
            //MainActivity.viewPagerAdapter.refresh(1, FragmentPoruka(group))
            MainActivity.viewPagerAdapter.refresh(1, FragmentPoruka(true, Grupa(grupa, istrazivanje), null/*, null*/))
        }
        return view
    }

}