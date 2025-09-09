package eu.pl.snk.senseibunny.museomaster.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import eu.pl.snk.senseibunny.museomaster.databinding.CustomUserPopupBinding
import eu.pl.snk.senseibunny.museomaster.databinding.UserListItemBinding
import eu.pl.snk.senseibunny.museomaster.models.Client
import eu.pl.snk.senseibunny.museomaster.models.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class ClientAdapter(private val clientList: ArrayList<Client>) : RecyclerView.Adapter<ClientAdapter.ClientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val view = UserListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ClientViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val client = clientList[position]
        holder.bind(client)

    }

    override fun getItemCount(): Int {
        return clientList.size
    }

    inner class ClientViewHolder(val itemBinding: UserListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private val nameTextView: TextView = itemBinding.nameTextView
        private val emailTextView: TextView = itemBinding.secondNameTextView

        init {
            itemBinding.detailsIcon.setOnClickListener {
                val client = clientList[adapterPosition]
                showPopup(client)
            }
        }
        fun bind(client: Client) {
            nameTextView.text = client.getNazwaUzytkownika()
            emailTextView.text = client.emailPracownika
            itemBinding.work.text = client.rola

        }

        fun showPopup(client:Client) {
            val popupBinding: CustomUserPopupBinding = CustomUserPopupBinding.inflate(LayoutInflater.from(itemBinding.root.context))
            val popupView: View = popupBinding.root
            val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)

            // Set the focusable property to true
            popupWindow.isFocusable = true

            // Set an OnTouchListener to consume touch events
            popupView.setOnTouchListener { _, _ -> true }

            popupBinding.name1TextView.text = "Name: "+client.imiePracownika
            popupBinding.name2TextView.text = "Second name: "+client.nazwaUzytkownika
            popupBinding.ageTextView.text = "Age: "+ client.wiekPracownika.toString()
            popupBinding.phoneNumberTextView.text="Phone number: "+ client.nrTelefonu.toString()
            popupBinding.roleTextView.text="Position: " + client.rola

            popupBinding.closeButton.setOnClickListener {
                popupWindow.dismiss()
            }

            popupBinding.deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    clientList.removeAt(position)
                    notifyItemRemoved(position)
                    popupWindow.dismiss()
                }
                runBlocking {
                    // Launch a coroutine in the IO dispatcher
                    withContext(Dispatchers.IO) {
                        Model.getInstanceWC().dataBaseDriver.deleteClient(client.idPracownika)

                    }

                }
            }

            // Show the popup window in the center of the screen
            popupWindow.showAtLocation(itemBinding.root, Gravity.CENTER, 0, 0)
        }
    }



}