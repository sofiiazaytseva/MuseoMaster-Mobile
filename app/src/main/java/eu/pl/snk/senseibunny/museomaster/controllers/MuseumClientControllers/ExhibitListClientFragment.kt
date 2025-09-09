package eu.pl.snk.senseibunny.museomaster.controllers.MuseumClientControllers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.pl.snk.senseibunny.museomaster.R
import eu.pl.snk.senseibunny.museomaster.adapters.ExhibitClientAdapter
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentExhibitListClientBinding
import eu.pl.snk.senseibunny.museomaster.models.Exhibit
import eu.pl.snk.senseibunny.museomaster.models.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*

class ExhibitListClientFragment : Fragment() {

    private var exhibits : ArrayList<Exhibit> = ArrayList()
    private lateinit var binding: FragmentExhibitListClientBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExhibitListClientBinding.inflate(inflater, container, false)
        runBlocking {
            withContext(Dispatchers.IO){
                Model.getInstance(context).clearExhibitList()
                Model.getInstance(context).setExhibits()
                exhibits = Model.getInstance(context).exhibits
            }
        }
        val adapter = ExhibitClientAdapter(exhibits)
        binding.recyclerView.adapter = adapter
        // Inflate the layout for this fragment
        return (binding.root)
    }

}