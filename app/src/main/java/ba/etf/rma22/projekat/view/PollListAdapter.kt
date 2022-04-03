package ba.etf.rma22.projekat.view

import android.content.Context
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.text.bold
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import java.text.SimpleDateFormat

class PollListAdapter(private var polls: List<Anketa>) : RecyclerView.Adapter<PollListAdapter.PollViewHolder>() {

    inner class PollViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pollName: TextView = itemView.findViewById(R.id.name)
        val pollState: ImageView = itemView.findViewById(R.id.state)
        val pollNumber: TextView = itemView.findViewById(R.id.number)
        val pollProgressBar: ProgressBar = itemView.findViewById(R.id.progresZavrsetka)
        val pollDate: TextView = itemView.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PollViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_poll, viewGroup, false)
        return PollViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: PollViewHolder, position: Int) {
        viewHolder.pollName.text = polls[position].naziv                     //nazivGrupe?
        viewHolder.pollNumber.text = polls[position].nazivIstrazivanja

        val state: String = polls[position].stanje.name1
        val context: Context = viewHolder.pollState.context
        val id: Int = context.resources.getIdentifier(state, "drawable", context.packageName)
        viewHolder.pollState.setImageResource(id)

        var s = SpannableStringBuilder()
        val formatter = SimpleDateFormat("dd.MM.yyyy.")
        when(state) {
            "plava" -> s = SpannableStringBuilder().append("Anketa urađena: ").bold { append(formatter.format(polls[position].datumRada)) }  //null?
            "zelena" -> s = SpannableStringBuilder().append("Vrijeme zatvaranja: ").bold { append(formatter.format(polls[position].datumKraj)) }
            "crvena" -> s = SpannableStringBuilder().append("Anketa zatvorena: ").bold { append(formatter.format(polls[position].datumKraj)) }
            "zuta" -> s = SpannableStringBuilder().append("Vrijeme aktiviranja: ").bold { append(formatter.format(polls[position].datumPocetak)) }
        }
        viewHolder.pollDate.text = s

        var progres: Float = (Math.ceil((polls[position].progres * 10).toDouble()) * 10).toFloat()
        if((progres / 2) % 2 != 0F)
            progres += 10F
        viewHolder.pollProgressBar.progress = progres.toInt()
    }

    override fun getItemCount() = polls.size

    fun updatePolls(polls: List<Anketa>) {
        this.polls = polls
        notifyDataSetChanged()
    }
}