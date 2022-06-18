package ba.etf.rma22.projekat.view

import android.annotation.SuppressLint
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
import kotlin.math.floor

class PollListAdapter(
    private var polls: List<Anketa>,
    private val onItemClick: (poll: Anketa) -> Unit
) : RecyclerView.Adapter<PollListAdapter.PollViewHolder>() {

    inner class PollViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pollName: TextView = itemView.findViewById(R.id.name)
        val pollState: ImageView = itemView.findViewById(R.id.state)
        val pollResearche: TextView = itemView.findViewById(R.id.researche)
        val pollProgressBar: ProgressBar = itemView.findViewById(R.id.progresZavrsetka)
        val pollDate: TextView = itemView.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PollViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_poll, viewGroup, false)
        return PollViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(viewHolder: PollViewHolder, position: Int) {
        viewHolder.pollName.text = polls[position].naziv
        viewHolder.pollResearche.text = polls[position].nazivIstrazivanja

        val state: String = polls[position].stanje.name1
        val context: Context = viewHolder.pollState.context
        val id: Int = context.resources.getIdentifier(state, "drawable", context.packageName)
        viewHolder.pollState.setImageResource(id)

        var s = SpannableStringBuilder()
        val formatter = SimpleDateFormat("dd.MM.yyyy.")
        when(state) {
            "plava" -> {
                if(polls[position].datumRada != null) s = SpannableStringBuilder().append("Anketa urađena: ").bold { append(formatter.format(polls[position].datumRada)) }
            }
            "zelena" -> {
                if(polls[position].datumKraj != null) s = SpannableStringBuilder().append("Vrijeme zatvaranja: ").bold { append(formatter.format(polls[position].datumKraj)) }
                else s = SpannableStringBuilder().append("Vrijeme zatvaranja: nepoznato")
            }
            "crvena" -> {
                if(polls[position].datumKraj != null) s = SpannableStringBuilder().append("Anketa zatvorena: ").bold { append(formatter.format(polls[position].datumKraj)) }
            }
            "zuta" -> {
                if(polls[position].datumPocetak != null) s = SpannableStringBuilder().append("Vrijeme aktiviranja: ").bold { append(formatter.format(polls[position].datumPocetak)) }
                else s = SpannableStringBuilder().append("Vrijeme aktiviranja: nepoznato")
            }
        }
        viewHolder.pollDate.text = s
        viewHolder.pollProgressBar.progress = (polls[position].progres * 100).toInt()
        viewHolder.itemView.setOnClickListener{ onItemClick(polls[position]) }
    }

    override fun getItemCount() = polls.size

    fun updatePolls(polls: List<Anketa>) {
        this.polls = polls
        notifyDataSetChanged()
    }

}