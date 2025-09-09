package eu.pl.snk.senseibunny.museomaster.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import eu.pl.snk.senseibunny.museomaster.databinding.CustomExhibitionPopupBinding
import eu.pl.snk.senseibunny.museomaster.databinding.ExhibitionsListItemBinding
import eu.pl.snk.senseibunny.museomaster.models.Exhibition
import eu.pl.snk.senseibunny.museomaster.models.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*

class ExhibitionsAdapter(private val exhibitionsList: ArrayList<Exhibition>) : RecyclerView.Adapter<ExhibitionsAdapter.ExhibitionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExhibitionViewHolder {
        val view = ExhibitionsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExhibitionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return exhibitionsList.size
    }

    override fun onBindViewHolder(holder: ExhibitionViewHolder, position: Int) {
        val exhibition = exhibitionsList[position]
        holder.bind(exhibition)
    }


    inner class ExhibitionViewHolder(val itemBinding: ExhibitionsListItemBinding) : RecyclerView.ViewHolder(itemBinding.root){
        private val ExNameTextView : TextView = itemBinding.ExNameTextView
        private val TopicTextView : TextView = itemBinding.TopicTextView

        init{
            itemBinding.detailsIcon.setOnClickListener{
                val exhibit = exhibitionsList[adapterPosition]
                showPopup(exhibit)
            }
        }

        fun bind(exhibition: Exhibition){
            ExNameTextView.text = exhibition.nazwaWystawy
            TopicTextView.text = exhibition.tematyka
        }

        fun showPopup(exhibition: Exhibition){
            val popupBinding: CustomExhibitionPopupBinding = CustomExhibitionPopupBinding.inflate(LayoutInflater.from(itemBinding.root.context))
            val popupView: View = popupBinding.root
            val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)

            // Set the focusable property to true
            popupWindow.isFocusable = true

            // Set an OnTouchListener to consume touch events
            popupView.setOnTouchListener { _, _ -> true }

            popupBinding.nameTextView.text = "Name:" + exhibition.nazwaWystawy
            popupBinding.roomTextView.text = "Room:" + exhibition.sala
            popupBinding.PlaceOfCreationTextView.text = "Place of Creation:" + exhibition.miejsceWykonania
            popupBinding.topicTextView.text = "Theme:" + exhibition.tematyka
            popupBinding.authorTextView.text = "Creator:" + exhibition.tworca
            popupBinding.startDateTextView.text = "Start Date:" + exhibition.dataRozpoczecia
            popupBinding.EndDateTextView.text = "End Date:" + exhibition.dataZakonczenia

            popupBinding.closeButton.setOnClickListener {
                popupWindow.dismiss()
            }

            popupBinding.deleteButton.setOnClickListener {
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    exhibitionsList.removeAt(position)
                    notifyItemRemoved(position)
                    popupWindow.dismiss()
                }
                runBlocking {
                    withContext(Dispatchers.IO) {
                        Model.getInstanceWC().dataBaseDriver.deleteExhibition(exhibition.idWystawy)
                    }
                }
            }

            // Show the popup window in the center of the screen
            popupWindow.showAtLocation(itemBinding.root, Gravity.CENTER, 0, 0)

        }
    }
}