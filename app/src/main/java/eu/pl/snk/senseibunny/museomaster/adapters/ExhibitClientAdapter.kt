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
import eu.pl.snk.senseibunny.museomaster.databinding.CustomExhibitClientPopupBinding
import eu.pl.snk.senseibunny.museomaster.databinding.ExhibitListItemBinding
import eu.pl.snk.senseibunny.museomaster.models.Exhibit
import eu.pl.snk.senseibunny.museomaster.models.Model
import java.util.*

class ExhibitClientAdapter(private val exhibitsList: ArrayList<Exhibit>) : RecyclerView.Adapter<ExhibitClientAdapter.ExhibitClientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExhibitClientViewHolder{
        val view = ExhibitListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExhibitClientViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExhibitClientViewHolder, position: Int) {
        val exhibit = exhibitsList[position]
        holder.bind(exhibit)
    }

    override fun getItemCount(): Int {
        return exhibitsList.size
    }

    inner class ExhibitClientViewHolder(val itemBinding: ExhibitListItemBinding) : RecyclerView.ViewHolder(itemBinding.root){
        private val ExNameTextView : TextView = itemBinding.ExNameTextView
        private val TopicTextView : TextView = itemBinding.TopicTextView

        init{
            itemBinding.detailsIcon.setOnClickListener{
                val exhibit = exhibitsList[adapterPosition]
                showPopup(exhibit)
            }
        }

        fun bind(exhibit: Exhibit){
            ExNameTextView.text = exhibit.nazwa_zabytku_tf
            TopicTextView.text = exhibit.tematyka_tf
        }

        fun showPopup(exhibit: Exhibit){
            val popupBinding: CustomExhibitClientPopupBinding = CustomExhibitClientPopupBinding.inflate(
                LayoutInflater.from(itemBinding.root.context))
            val popupView: View = popupBinding.root
            val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)

            val items = Model.getInstanceWC().allRooms
            val adapter = ArrayAdapter(itemBinding.root.context, R.layout.simple_spinner_item, items)
            // Set the focusable property to true
            popupWindow.isFocusable = true

            // Set an OnTouchListener to consume touch events
            popupView.setOnTouchListener { _, _ -> true }

            popupBinding.nameEt.text = exhibit.nazwa_zabytku_tf
            popupBinding.periodEt.text = exhibit.okres_powstawnia_tf
            popupBinding.topicEt.text = exhibit.tematyka_tf
            popupBinding.authorEt.text = exhibit.tworca_tf

            popupBinding.descEt.text = exhibit.opis_ta

            popupBinding.closeButton.setOnClickListener {
                popupWindow.dismiss()
            }

            // Show the popup window in the center of the screen
            popupWindow.showAtLocation(itemBinding.root, Gravity.CENTER, 0, 0)

        }
    }

}