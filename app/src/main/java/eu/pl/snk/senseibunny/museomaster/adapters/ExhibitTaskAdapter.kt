package eu.pl.snk.senseibunny.museomaster.adapters

import android.R
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import eu.pl.snk.senseibunny.museomaster.databinding.CustomExhibitPopupBinding
import eu.pl.snk.senseibunny.museomaster.databinding.ExhibitListItemBinding
import eu.pl.snk.senseibunny.museomaster.databinding.ExhibitListTaskItemBinding
import eu.pl.snk.senseibunny.museomaster.models.Exhibit
import eu.pl.snk.senseibunny.museomaster.models.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.ArrayList

class ExhibitTaskAdapter (private val exList: ArrayList<Exhibit>) : RecyclerView.Adapter<ExhibitTaskAdapter.SearchedExViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedExViewHolder {
        val view =
            ExhibitListTaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchedExViewHolder(view)
    }


    override fun onBindViewHolder(holder: SearchedExViewHolder, position: Int) {
        val ex = exList[position]
        holder.bind(ex)

    }

    override fun getItemCount(): Int {
        return exList.size
    }

    inner class SearchedExViewHolder(val itemBinding: ExhibitListTaskItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(ex: Exhibit) {
            itemBinding.ExNameTextView.text = ex.nazwa_zabytku_tf.toString()
            itemBinding.TopicTextView.text = ex.tematyka_tf.toString()

            itemBinding.exCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                println("HAHA2")

                if (isChecked) {
                    Model.getInstanceWC().assignEx(ex);
                    System.out.println(Model.getInstanceWC().exAssigned.toString())
                } else {
                    Model.getInstanceWC().removeEx(ex);
                    System.out.println(Model.getInstanceWC().exAssigned.toString())
                }

            }

        }
    }

}