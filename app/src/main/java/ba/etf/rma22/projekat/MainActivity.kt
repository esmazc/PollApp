package ba.etf.rma22.projekat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import ba.etf.rma22.projekat.view.FragmentAnkete
import ba.etf.rma22.projekat.view.FragmentIstrazivanje
import ba.etf.rma22.projekat.view.FragmentPoruka
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
        /*viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if(viewPager.currentItem == 0 && viewPagerAdapter.getItem(0) is FragmentAnkete) {
                    FragmentAnkete.pollsAdapter.updatePolls(FragmentAnkete.pollListViewModel.getMyAnkete().sortedBy { poll -> poll.datumPocetak })
                    if(viewPagerAdapter.getItem(1) is FragmentPoruka)
                        viewPagerAdapter.refresh(1, FragmentIstrazivanje())
                }
                super.onPageSelected(position)
            }
        })*/
    }
}
