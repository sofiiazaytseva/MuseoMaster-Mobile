package eu.pl.snk.senseibunny.museomaster.adapters

import android.R
import android.graphics.Color
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
import eu.pl.snk.senseibunny.museomaster.models.Exhibit
import eu.pl.snk.senseibunny.museomaster.models.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.ArrayList

class ExhibitAdapter(private val exhibitsList: ArrayList<Exhibit>) : RecyclerView.Adapter<ExhibitAdapter.ExhibitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExhibitViewHolder{
        val view = ExhibitListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExhibitViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExhibitViewHolder, position: Int) {
        val exhibit = exhibitsList[position]
        holder.bind(exhibit)
    }

    override fun getItemCount(): Int {
        return exhibitsList.size
    }

    inner class ExhibitViewHolder(val itemBinding: ExhibitListItemBinding) : RecyclerView.ViewHolder(itemBinding.root){
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
            val popupBinding: CustomExhibitPopupBinding = CustomExhibitPopupBinding.inflate(LayoutInflater.from(itemBinding.root.context))
            val popupView: View = popupBinding.root
            val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)

            val items = Model.getInstanceWC().allRooms
            val adapter = ArrayAdapter(itemBinding.root.context, R.layout.simple_spinner_item, items)
            // Set the focusable property to true
            popupWindow.isFocusable = true

            // Set an OnTouchListener to consume touch events
            popupView.setOnTouchListener { _, _ -> true }

            popupBinding.nameEt.setText(exhibit.nazwa_zabytku_tf)
            popupBinding.periodEt.setText(exhibit.okres_powstawnia_tf)
            popupBinding.topicEt.setText(exhibit.tematyka_tf)
            popupBinding.authorEt.setText(exhibit.tworca_tf)
            popupBinding.roomName.adapter = adapter

            val valueToSelect = exhibit.akt_miej_przech_tf
            if (adapter is ArrayAdapter<*>) {
                val position = adapter.getPosition(valueToSelect)
                popupBinding.roomName.setSelection(position)
            }
            popupBinding.descEt.setText(exhibit.opis_ta)

            popupBinding.closeButton.setOnClickListener {
                popupWindow.dismiss()
            }

            popupBinding.deleteButton.setOnClickListener {
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    exhibitsList.removeAt(position)
                    notifyItemRemoved(position)
                    popupWindow.dismiss()
                }
                runBlocking {
                    withContext(Dispatchers.IO) {
                        Model.getInstanceWC().dataBaseDriver.deleteExhibit(exhibit.idZabytku)
                    }
                }
            }
            popupBinding.editButton.setOnClickListener {
                runBlocking {
                    withContext(Dispatchers.IO) {
                        val id = exhibit.idZabytku
                        val name = popupBinding.nameEt.text.toString()
                        val intValue = popupBinding.periodEt.text.toString().toIntOrNull()
                        val topic = popupBinding.topicEt.text.toString()
                        val author = popupBinding.authorEt.text.toString()
                        var spinner = popupBinding.roomName
                        val roomName = spinner.selectedItem as String
                        val desc = popupBinding.descEt.text.toString()
                        Model.getInstanceWC().dataBaseDriver.editExhibit(
                            id, name, intValue, topic, author, roomName, "null", desc
                        )
                    }
                }

            }


            // Show the popup window in the center of the screen
            popupWindow.showAtLocation(itemBinding.root, Gravity.CENTER, 0, 0)

        }
    }

}