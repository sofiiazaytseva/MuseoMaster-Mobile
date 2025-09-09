package eu.pl.snk.senseibunny.museomaster.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eu.pl.snk.senseibunny.museomaster.databinding.ExhibitAssignedListItemBinding
import eu.pl.snk.senseibunny.museomaster.models.Exhibit
import eu.pl.snk.senseibunny.museomaster.models.Model
import java.util.*

class ExhibitTransferAdapter(private val exList: ArrayList<Exhibit>) : RecyclerView.Adapter<ExhibitTransferAdapter.ExTransViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExhibitTransferAdapter.ExTransViewHolder {
        val view = ExhibitAssignedListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExTransViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExhibitTransferAdapter.ExTransViewHolder, position: Int) {
        val ex = exList[position]
        holder.bind(ex)

    }

    override fun getItemCount(): Int {
        return exList.size
    }
    inner class ExTransViewHolder(val itemBinding: ExhibitAssignedListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(ex: Exhibit) {
            itemBinding.ExNameTextView.text = ex.nazwa_zabytku_tf.toString()
            itemBinding.curRoomTV.text = ex.akt_miej_przech_tf.toString()
            itemBinding.tarRoomTV.text = ex.docelowe_miej_przech.toString()

            itemBinding.exCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->

                if (isChecked) {
                    Model.getInstanceWC().checkExhibit(ex);
                } else {
                    Model.getInstanceWC().uncheckExhibit(ex);
                }

            }

        }
    }
}