package eu.pl.snk.senseibunny.museomaster.controllers.CuratorControllers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.pl.snk.senseibunny.museomaster.adapters.ExhibitAdapter
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentSearchedExhibitsBinding
import eu.pl.snk.senseibunny.museomaster.models.Exhibit
import eu.pl.snk.senseibunny.museomaster.models.Model
import kotlinx.coroutines.runBlocking
import java.util.ArrayList

class SearchedExhibitsFragment : Fragment() {

    private var searchedExhibits : ArrayList<Exhibit> = ArrayList()
    private lateinit var binding : FragmentSearchedExhibitsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchedExhibitsBinding.inflate(inflater, container, false)
        runBlocking {
            searchedExhibits = Model.getInstance(context).exhibitsSearched
        }
        val adapter = ExhibitAdapter(searchedExhibits)
        binding.recyclerViewSearchedEx.adapter = adapter
        // Inflate the layout for this fragment
        return (binding.root)
    }

}