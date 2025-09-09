package eu.pl.snk.senseibunny.museomaster.controllers.utilsControllers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.pl.snk.senseibunny.museomaster.R
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentMuseumClientBasicBinding


class MuseumClientBasicFragment : Fragment() {

    private lateinit var binding: FragmentMuseumClientBasicBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMuseumClientBasicBinding.inflate(inflater, container, false)
        return(binding.root)
    }

}