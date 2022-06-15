package ba.etf.rma22.projekat.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import ba.etf.rma22.projekat.InternetConnectivity
import ba.etf.rma22.projekat.MainActivity
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.viewmodel.UpisIstrazivanjeViewModel

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
    var istrazivanje = Istrazivanje(0, "", 0)
    var grupa = Grupa(0, "", 0)

    companion object {
        fun newInstance(): FragmentIstrazivanje = FragmentIstrazivanje()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.istrazivanje_fragment, container, false)

        spinnerGodina = view.findViewById(R.id.odabirGodina)
        spinnerIstrazivanje = view.findViewById(R.id.odabirIstrazivanja)
        spinnerGrupa = view.findViewById(R.id.odabirGrupa)
        button = view.findViewById(R.id.dodajIstrazivanjeDugme)
        button.isEnabled = false

        val godineList = listOf("1", "2", "3", "4", "5")
        spinnerGodinaAdapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, godineList) }!!
        spinnerGodina.adapter = spinnerGodinaAdapter
        spinnerGodina.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val godina = spinnerGodina.selectedItem.toString().toInt()
                context?.let {
                    upisIstrazivanjeViewModel.getIstrazivanjeByGodina(it, godina, onSuccess = ::onSuccessResearches, null)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        spinnerIstrazivanje.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                istrazivanje = spinnerIstrazivanje.selectedItem as Istrazivanje
                context?.let {
                    upisIstrazivanjeViewModel.getGroupsByIstrazivanje(it, istrazivanje.id, onSuccess = ::onSuccessGroups, null)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        spinnerGrupa.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                grupa = spinnerGrupa.selectedItem as Grupa
                context?.let {
                    if(InternetConnectivity.isOnline(it))
                        button.isEnabled = !(istrazivanje.naziv == "" || grupa.naziv == "")
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        button.setOnClickListener {
            if(button.isEnabled) {
                context?.let {
                    upisIstrazivanjeViewModel.upisiUGrupu(it, grupa.id, onSuccess = ::onSuccessEnroll, null)
                }
            }
        }
        return view
    }

    fun onSuccessResearches(context: Context, istrazivanja: List<Istrazivanje>) {
        researches = istrazivanja
        upisIstrazivanjeViewModel.getUpisani(context, onSuccess = ::onSuccessEnrolledResearches, null)
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
            spinnerGrupaAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, listOf(Grupa(0, "", 0)))
            spinnerGrupa.adapter = spinnerGrupaAdapter
        }
//        if(InternetConnectivity.isOnline(context))
//            upisIstrazivanjeViewModel.writeResearches(context, researches, null, null)
        //******
    }

    fun onSuccessGroups(context: Context, grupe: List<Grupa>) {
        groups = grupe
        val grupaList: ArrayList<Grupa> = arrayListOf()
        //***obrisati
        grupaList.add(Grupa(0, "", 0))
        //*****
        grupaList.addAll(groups)
        spinnerGrupaAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, grupaList)
        spinnerGrupa.adapter = spinnerGrupaAdapter
        if(InternetConnectivity.isOnline(context)) {
            button.isEnabled = !(istrazivanje.naziv == "" || grupa.naziv == "")
            //upisIstrazivanjeViewModel.writeGroups(context, groups, null, null)
        }
    }

    fun onSuccessEnroll() {
        MainActivity.viewPagerAdapter.refresh(1, FragmentPoruka("Uspješno ste upisani u grupu ${grupa.naziv} istraživanja ${istrazivanje.naziv}!"))
    }
}