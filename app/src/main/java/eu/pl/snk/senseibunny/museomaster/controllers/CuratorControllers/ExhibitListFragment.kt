package eu.pl.snk.senseibunny.museomaster.controllers.CuratorControllers

import android.os.Bundle
import android.view.Display.Mode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.pl.snk.senseibunny.museomaster.R
import eu.pl.snk.senseibunny.museomaster.adapters.ExhibitAdapter
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentExhibitListBinding
import eu.pl.snk.senseibunny.museomaster.models.Exhibit
import eu.pl.snk.senseibunny.museomaster.models.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.ArrayList

class ExhibitListFragment : Fragment() {

    private var exhibits : ArrayList<Exhibit> = ArrayList()
    private lateinit var binding: FragmentExhibitListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExhibitListBinding.inflate(inflater, container, false)
        runBlocking {
            withContext(Dispatchers.IO){
                Model.getInstance(context).clearExhibitList()
                Model.getInstance(context).clearAllRooms()
                Model.getInstance(context).setExhibits()
                Model.getInstance(context).setAllRooms()
                exhibits = Model.getInstance(context).exhibits
            }
        }
        val adapter = ExhibitAdapter(exhibits)
        binding.recyclerView.adapter = adapter
        // Inflate the layout for this fragment
        return (binding.root)
    }

}