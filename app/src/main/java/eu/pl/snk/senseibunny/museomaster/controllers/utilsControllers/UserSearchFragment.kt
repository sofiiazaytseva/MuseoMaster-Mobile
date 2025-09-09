package eu.pl.snk.senseibunny.museomaster.controllers.utilsControllers

import android.content.Intent
import android.os.Bundle
import android.view.Display.Mode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import eu.pl.snk.senseibunny.museomaster.R
import eu.pl.snk.senseibunny.museomaster.adapters.AssignedToTaskAdapter
import eu.pl.snk.senseibunny.museomaster.adapters.SearchedUserAdapter
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentAssignedToListBinding
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentUserSearchBinding
import eu.pl.snk.senseibunny.museomaster.models.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class UserSearchFragment : Fragment() {

    private lateinit var binding: FragmentUserSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserSearchBinding.inflate(inflater, container, false)
        Model.getInstance(context)
        if(Model.getInstanceWC().workersAssigned.isNotEmpty()){
            Model.getInstanceWC().clearWorkersAssigned()
        }


        val positions =
            arrayOf("", "Worker", "Worker+", "Technical Worker", "Technical Worker+", "Curator")
        val adapter: ArrayAdapter<String?> =
            ArrayAdapter<String?>(requireContext(), android.R.layout.simple_spinner_item, positions)
        binding.positionSpinner.adapter = adapter

        val adapter2= SearchedUserAdapter(Model.getInstanceWC().clients)
        binding.recyclerView.adapter=adapter2

        binding.searchButton.setOnClickListener {
            Model.getInstanceWC().clearWorkers()
            if (binding.searchBar.text.isEmpty() || binding.searchBar.text.isBlank()) {
                var x= binding.positionSpinner.selectedItem as String
                println(x)
                println("xddddaa")
                if(x == "Worker"){
                    x="Pracownik"
                }
                else if(x=="Worker+"){
                    x="Pracownik+"
                }
                else if(x=="Technical Worker"){
                    x="Pracownik Techniczny"
                }
                else if(x=="Technical Worker+"){
                    x="Pracownik Techniczny+"
                }
                else if(x=="Curator"){
                    x="Kurator"
                }
                else{
                    println(x)
                    x="Pracownik"
                }
                runBlocking {
                    withContext(Dispatchers.IO) {
                        Model.getInstanceWC().setWorkers(
                            binding.searchBar.text.toString(),
                            x
                        )

                        activity?.runOnUiThread {
                            adapter2.notifyDataSetChanged()
                            println("zmiana")
                        }
                    }
                }
            } else {
                runBlocking {
                    // Launch a coroutine in the IO dispatcher
                    withContext(Dispatchers.IO) {
                        Model.getInstanceWC().setWorkers(binding.searchBar.text.toString(), "")
                    }

                    activity?.runOnUiThread {
                        adapter2.notifyDataSetChanged()
                        println("Znaleziono zadanie")
                    }

                }
            }
        }

        binding.nextButton.setOnClickListener {
            if (Model.getInstanceWC().workersAssigned.isNotEmpty()) {
                val intent = Intent(requireContext(), DetailAndAddTask::class.java)
                startActivity(intent)
            }
        }
            return (binding.root)
        }

    }

