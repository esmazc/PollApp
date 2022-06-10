package ba.etf.rma22.projekat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import ba.etf.rma22.projekat.data.repositories.AccountRepository
import ba.etf.rma22.projekat.data.repositories.ApiAdapter
import ba.etf.rma22.projekat.view.FragmentAnkete
import ba.etf.rma22.projekat.view.FragmentIstrazivanje
import ba.etf.rma22.projekat.view.ViewPagerAdapter

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var viewPager: ViewPager2
        lateinit var viewPagerAdapter: ViewPagerAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //initAccount()

        val fragments = mutableListOf(FragmentAnkete.newInstance(), FragmentIstrazivanje.newInstance())
        viewPager = findViewById(R.id.pager)
        viewPagerAdapter = ViewPagerAdapter(this, fragments)
        viewPager.adapter = viewPagerAdapter
    }

    /*private fun initAccount(){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            AccountRepository.postaviHash("4b9f0bb9-214f-4c5f-88d2-a69941de67be")
        }
    }*/
}
