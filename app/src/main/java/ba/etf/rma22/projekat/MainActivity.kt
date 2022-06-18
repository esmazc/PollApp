package ba.etf.rma22.projekat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import ba.etf.rma22.projekat.data.repositories.*
import ba.etf.rma22.projekat.view.FragmentAnkete
import ba.etf.rma22.projekat.view.FragmentIstrazivanje
import ba.etf.rma22.projekat.view.FragmentPitanje
import ba.etf.rma22.projekat.view.ViewPagerAdapter

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var viewPager: ViewPager2
        lateinit var viewPagerAdapter: ViewPagerAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragments = mutableListOf(FragmentAnkete.newInstance(), FragmentIstrazivanje.newInstance())
        viewPager = findViewById(R.id.pager)
        viewPagerAdapter = ViewPagerAdapter(this, fragments)
        viewPager.adapter = viewPagerAdapter

        AccountRepository.setContext(applicationContext)
        val payload = intent?.getStringExtra("payload")
        if(payload == null)
            FragmentAnkete.pollListViewModel.postaviHash("4b9f0bb9-214f-4c5f-88d2-a69941de67be", null, null)
        else
            FragmentAnkete.pollListViewModel.postaviHash(payload, null, null)

        AnketaRepository.setContext(applicationContext)
        IstrazivanjeIGrupaRepository.setContext(applicationContext)
        OdgovorRepository.setContext(applicationContext)
        PitanjeAnketaRepository.setContext(applicationContext)
        TakeAnketaRepository.setContext(applicationContext)
        applicationContext?.let {
            if(InternetConnectivity.isOnline(it)) {
                FragmentAnkete.pollListViewModel.writeResearchesAndGroups(null, null)
                FragmentAnkete.pollListViewModel.writeAnketaTakens(null, null)
                FragmentAnkete.pollListViewModel.writeAnketaGrupa(null, null)
                FragmentAnkete.pollListViewModel.writePitanjaIPitanjaAnketa(null, null)
                FragmentPitanje.pitanjeAnketaViewModel.writeOdgovori(null, null)
            }
        }
    }
}
