package ba.etf.rma22.projekat.view

import android.os.Bundle
import android.os.Handler
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
    private lateinit var spinnerIstrazivanjeAdapter: ArrayAdapter<Istrazivanje>
    private lateinit var spinnerGrupaAdapter: ArrayAdapter<Grupa>
    private var researches = listOf<Istrazivanje>()
    private var groups = listOf<Grupa>()
    private var enrolledResearches = listOf<Istrazivanje>()
    //private var korisnik = KorisnikViewModel().getUser()
    var istrazivanje = Istrazivanje(0, "", 0)
    var grupa = Grupa(0, "")

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
        /*val s: String = korisnik.parovi.last().first
        var godina: Int = upisIstrazivanjeViewModel.getAll().find { istrazivanje -> istrazivanje.naziv == s }!!.godina
        spinnerGodina.setSelection(godina - 1)*/
        spinnerGodina.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val godina = spinnerGodina.selectedItem.toString().toInt()
                upisIstrazivanjeViewModel.getIstrazivanjeByGodina(godina, onSuccess = ::onSuccessResearches, null)
//                val istrazivanjaList = researches
//                println(researches.size)
//                upisIstrazivanjeViewModel.getUpisani(onSuccess = ::onSuccessEnrolledResearches, null)
//                val upisani = enrolledResearches.map { istr -> istr.id }.toList()
//                println(upisani.size)
//                val istrazivanjaList1 = arrayListOf<Istrazivanje>()
//                //***obrisati
//                istrazivanjaList1.add(Istrazivanje(0, "", 0))
//                //*****
//                for(ist: Istrazivanje in istrazivanjaList)
//                    if(!upisani.contains(ist.id))
//                        istrazivanjaList1.add(ist)
//                spinnerIstrazivanjeAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, istrazivanjaList1)
//                spinnerIstrazivanje.adapter = spinnerIstrazivanjeAdapter
//                //***promijeniti if i izbrisati "" iz listOf
//                if(istrazivanjaList1.size == 1) {
//                    //if(istrazivanjaList1.isEmpty()) {
//                    spinnerGrupaAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, listOf(Grupa(0, "")))
//                    spinnerGrupa.adapter = spinnerGrupaAdapter
//                }
//                //******
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        spinnerIstrazivanje.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                istrazivanje = spinnerIstrazivanje.selectedItem as Istrazivanje
                upisIstrazivanjeViewModel.getGroupsByIstrazivanje(istrazivanje.id, onSuccess = ::onSuccessGroups, null)
//                val grupaList = groups
//                val grupaList1: ArrayList<Grupa> = arrayListOf()
//                //***obrisati
//                grupaList1.add(Grupa(0, ""))
//                //*****
//                grupaList1.addAll(grupaList)
//                spinnerGrupaAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, grupaList1)
//                spinnerGrupa.adapter = spinnerGrupaAdapter
//                button.isEnabled = !(istrazivanje.naziv == "" || grupa.naziv == "")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        spinnerGrupa.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                grupa = spinnerGrupa.selectedItem as Grupa
                button.isEnabled = !(istrazivanje.naziv == "" || grupa.naziv == "")
                //upisIstrazivanjeViewModel.upisiUGrupu(grupa.id, onSuccess = ::onSuccessEnroll, onError = ::onErrorEnroll)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        button.setOnClickListener {
            //korisnik.parovi.add(Pair(istrazivanje, grupa))
            //MainActivity.viewPagerAdapter.refresh(1, FragmentPoruka(true, Grupa(grupa, istrazivanje), null/*, null*/))
            if(button.isEnabled) upisIstrazivanjeViewModel.upisiUGrupu(grupa.id, onSuccess = ::onSuccessEnroll, null)
        }
        return view
    }

    fun onSuccessResearches(istrazivanja: List<Istrazivanje>) {
        researches = istrazivanja
        upisIstrazivanjeViewModel.getUpisani(onSuccess = ::onSuccessEnrolledResearches, null)
    }

    fun onSuccessEnrolledResearches(upisanaIstrazivanja: List<Istrazivanje>) {
        enrolledResearches = upisanaIstrazivanja
        val upisani = enrolledResearches.map { istr -> istr.id }.toList()
        val istrazivanjaList1 = arrayListOf<Istrazivanje>()
        //***obrisati
        istrazivanjaList1.add(Istrazivanje(0, "", 0))
        //*****
        for(ist: Istrazivanje in researches)
            if(!upisani.contains(ist.id))
                istrazivanjaList1.add(ist)
        spinnerIstrazivanjeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, istrazivanjaList1)
        spinnerIstrazivanje.adapter = spinnerIstrazivanjeAdapter
        //***promijeniti if i izbrisati "" iz listOf
        if(istrazivanjaList1.size == 1) {
            //if(istrazivanjaList1.isEmpty()) {
            spinnerGrupaAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, listOf(Grupa(0, "")))
            spinnerGrupa.adapter = spinnerGrupaAdapter
        }
        //******
    }

    /*fun onSuccessResearches(istrazivanja: List<Istrazivanje>) {
        val istrazivanjaList = arrayListOf<Istrazivanje>()
        istrazivanjaList.add(Istrazivanje(0, "", 0))
        istrazivanjaList.addAll(istrazivanja)
        spinnerIstrazivanjeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, istrazivanjaList)
        spinnerIstrazivanje.adapter = spinnerIstrazivanjeAdapter
        if(istrazivanjaList.size == 1) {
            spinnerGrupaAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, listOf(Grupa(0, "")))
            spinnerGrupa.adapter = spinnerGrupaAdapter
        }
    }*/

    fun onSuccessGroups(grupe: List<Grupa>) {
        groups = grupe
        val grupaList: ArrayList<Grupa> = arrayListOf()
        //***obrisati
        grupaList.add(Grupa(0, ""))
        //*****
        grupaList.addAll(groups)
        spinnerGrupaAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, grupaList)
        spinnerGrupa.adapter = spinnerGrupaAdapter
        button.isEnabled = !(istrazivanje.naziv == "" || grupa.naziv == "")
    }

    fun onSuccessEnroll() {
        //MainActivity.viewPagerAdapter.refresh(1, FragmentPoruka(true, istrazivanje.naziv, grupa.naziv, null))
        MainActivity.viewPagerAdapter.refresh(1, FragmentPoruka("Uspješno ste upisani u grupu ${grupa.naziv} istraživanja ${istrazivanje.naziv}!"))
    }
}