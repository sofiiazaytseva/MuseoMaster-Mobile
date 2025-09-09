package eu.pl.snk.senseibunny.museomaster.controllers.CuratorControllers

import android.os.Bundle
import android.view.Display.Mode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.pl.snk.senseibunny.museomaster.adapters.ExhibitionsAdapter
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentExhibitionsListBinding
import eu.pl.snk.senseibunny.museomaster.models.Exhibition
import eu.pl.snk.senseibunny.museomaster.models.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*

class ExhibitionsListFragment : Fragment() {

    private var exhibitions : ArrayList<Exhibition> = ArrayList()
    private lateinit var binding : FragmentExhibitionsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExhibitionsListBinding.inflate(inflater, container, false)
        runBlocking {
            withContext(Dispatchers.IO){
                Model.getInstance(context).clearExhibitions()
                Model.getInstance(context).setExhibitions()
                exhibitions = Model.getInstance(context).exhibitions
            }
        }
        val adapter = ExhibitionsAdapter(exhibitions)
        binding.recyclerView.adapter = adapter
        return binding.root
    }

}