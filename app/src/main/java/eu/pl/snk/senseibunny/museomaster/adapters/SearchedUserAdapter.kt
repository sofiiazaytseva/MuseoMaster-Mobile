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
import eu.pl.snk.senseibunny.museomaster.databinding.UserTaskAssignItemBinding
import eu.pl.snk.senseibunny.museomaster.models.Client
import eu.pl.snk.senseibunny.museomaster.models.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class SearchedUserAdapter (private val userList: ArrayList<Client>) : RecyclerView.Adapter<SearchedUserAdapter.SearchedUserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedUserViewHolder {
        val view =
            UserTaskAssignItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchedUserViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchedUserViewHolder, position: Int) {
        val client = userList[position]
        holder.bind(client)

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class SearchedUserViewHolder(val itemBinding: UserTaskAssignItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private val nameTextView: TextView = itemBinding.nameTextView
        private val emailTextView: TextView = itemBinding.secondNameTextView


        fun bind(client: Client) {
            nameTextView.text = client.getNazwaUzytkownika()
            emailTextView.text = client.emailPracownika
            itemBinding.work.text = client.rola

            itemBinding.workerCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                println("HAHA")

                if (isChecked) {
                    Model.getInstanceWC().assignWorker(client)
                    System.out.println(Model.getInstanceWC().workersAssigned.toString())
                } else {
                    Model.getInstanceWC().removeWorker(client)
                    System.out.println(Model.getInstanceWC().workersAssigned.toString())
                }

            }

        }
    }
}