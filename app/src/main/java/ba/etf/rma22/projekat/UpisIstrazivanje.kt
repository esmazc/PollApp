package ba.etf.rma22.projekat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.KorisnikRepository
import ba.etf.rma22.projekat.viewmodel.UpisIstrazivanjeViewModel
import kotlin.streams.toList

class UpisIstrazivanje : AppCompatActivity() {
    private lateinit var spinnerGodina: Spinner
    private lateinit var spinnerIstrazivanje: Spinner
    private lateinit var spinnerGrupa: Spinner
    private lateinit var button: Button
    private var upisIstrazivanjeViewModel = UpisIstrazivanjeViewModel()
    private lateinit var spinnerGodinaAdapter: ArrayAdapter<String>
    private lateinit var spinnerIstrazivanjeAdapter: ArrayAdapter<String>
    private lateinit var spinnerGrupaAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upis_istrazivanje)

        spinnerGodina = findViewById(R.id.odabirGodina)
        spinnerIstrazivanje = findViewById(R.id.odabirIstrazivanja)
        spinnerGrupa = findViewById(R.id.odabirGrupa)
        button = findViewById(R.id.dodajIstrazivanjeDugme)

        val godineList = listOf("1", "2", "3", "4", "5")
        spinnerGodinaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, godineList)
        spinnerGodina.adapter = spinnerGodinaAdapter
        val s: String = KorisnikRepository.getUser().parovi.last().first
        var godina: Int = upisIstrazivanjeViewModel.getAll().find { istrazivanje -> istrazivanje.naziv == s }!!.godina
        spinnerGodina.setSelection(godina-1)
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
                spinnerIstrazivanjeAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, istrazivanjaList1)
                spinnerIstrazivanje.adapter = spinnerIstrazivanjeAdapter
                //***promijeniti if i izbrisati "" iz listOf
                if(istrazivanjaList1.size == 1) {
                //if(istrazivanjaList1.isEmpty()) {
                    spinnerGrupaAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, listOf(""))
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
                spinnerGrupaAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, grupaList1)
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
            KorisnikRepository.getUser().parovi.add(Pair(istrazivanje, grupa))
            val intent = Intent(applicationContext, MainActivity::class.java)
            finish()
            startActivity(intent)
        }
    }
}