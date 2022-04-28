package ba.etf.rma22.projekat.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity, var fragments: MutableList<Fragment>): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun add(fragment: Fragment) {
        fragments.add(fragment)
        notifyDataSetChanged()
    }

    fun addAll(fragments1: List<Fragment>) {
        fragments.addAll(fragments1)
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        fragments.removeAt(index)
        notifyDataSetChanged()
    }

    fun removeAll() {
        fragments.clear()
        notifyDataSetChanged()
    }

    fun refresh(index: Int, fragment: Fragment) {
        fragments[index] = fragment
        notifyItemChanged(index)
    }

    fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemId(position: Int): Long {
        return fragments[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return fragments.find { it.hashCode().toLong() == itemId } != null
    }

}