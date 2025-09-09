package eu.pl.snk.senseibunny.museomaster.controllers.utilsControllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import eu.pl.snk.senseibunny.museomaster.adapters.ExhibitTransferAdapter
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentExhibitsAssignedBinding
import eu.pl.snk.senseibunny.museomaster.models.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class ExhibitsAssignedFragment : Fragment() {

    private lateinit var binding : FragmentExhibitsAssignedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExhibitsAssignedBinding.inflate(inflater, container, false)
        initData()
        val adapter = ExhibitTransferAdapter(Model.getInstanceWC().exhibitsAssigned)
        binding.recyclerView.adapter=adapter

        binding.confirmBtn.setOnClickListener {
            runBlocking {
                withContext(Dispatchers.IO){
                    for (ex in Model.getInstance(context).exChecked) {
                        Model.getInstance(context).dataBaseDriver.UpdateExPlaceAndStatu(
                            "",
                            ex.docelowe_miej_przech,
                            ex.idZabytku
                        )
                        Model.getInstance(context).dataBaseDriver.deleteEx_Task(ex.idZadEx)
                    }
                    initData()
                    activity?.runOnUiThread {
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
        return(binding.root)
    }

    fun initData() {
        runBlocking {
            withContext(Dispatchers.IO){
                Model.getInstance(context).clearExAssigned()
                Model.getInstance(context).clearCheckedEx()
                Model.getInstance(context).clearExIds()
                Model.getInstance(context).setExIds()
                Model.getInstance(context).setExModel()
            }
        }

    }
}