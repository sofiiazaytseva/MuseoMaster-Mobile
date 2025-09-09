package eu.pl.snk.senseibunny.museomaster.controllers.AdminControllers

import android.os.Bundle
import android.view.Display.Mode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.pl.snk.senseibunny.museomaster.R
import eu.pl.snk.senseibunny.museomaster.adapters.ClientAdapter
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentAddRoomBinding
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentUserListBinding
import eu.pl.snk.senseibunny.museomaster.models.Client
import eu.pl.snk.senseibunny.museomaster.models.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class UserListFragment : Fragment() {

    private var clients: ArrayList<Client> = ArrayList()

    private lateinit var binding: FragmentUserListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        runBlocking {
            // Launch a coroutine in the IO dispatcher
            withContext(Dispatchers.IO) {
                Model.getInstance(context).setClients()
                clients = Model.getInstance(context).clients;
            }
            println(clients)
        }
        val adapter=ClientAdapter(clients)
        binding.recyclerView.adapter=adapter

        return (binding.root)
    }

}