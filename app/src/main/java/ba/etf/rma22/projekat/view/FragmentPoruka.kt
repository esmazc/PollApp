//package ba.etf.rma22.projekat.view
//
//import android.annotation.SuppressLint
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import ba.etf.rma22.projekat.R
//import ba.etf.rma22.projekat.data.models.Anketa
//import ba.etf.rma22.projekat.data.models.Grupa
//
//class FragmentPoruka(
//    private val flag: Boolean,
//    private val istrazivanje: String?,
//    private val grupa: String?,
//    private val anketa: String?
//) : Fragment() {
//    private lateinit var message: TextView
//
//    @SuppressLint("SetTextI18n")
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val view = inflater.inflate(R.layout.poruka_fragment, container, false)
//
//        message = view.findViewById(R.id.tvPoruka)
//        if(flag)
//            message.text = "Uspješno ste upisani u grupu $grupa istraživanja $istrazivanje!"
//        else
//            message.text = "Završili ste anketu $anketa u okviru istraživanja $istrazivanje"
//        return view
//    }
//
//}

package ba.etf.rma22.projekat.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa

class FragmentPoruka(
    private val poruka: String
) : Fragment() {
    private lateinit var message: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.poruka_fragment, container, false)

        message = view.findViewById(R.id.tvPoruka)
        message.text = poruka
        return view
    }

}