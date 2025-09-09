package eu.pl.snk.senseibunny.museomaster.controllers.utilsControllers

import android.os.Bundle
import android.view.Display.Mode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.set
import eu.pl.snk.senseibunny.museomaster.R
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentBugBinding
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentEndedTaskListBinding
import eu.pl.snk.senseibunny.museomaster.models.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class BugFragment : Fragment() {

    private lateinit var binding: FragmentBugBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBugBinding.inflate(inflater, container, false)

        binding.sendButton.setOnClickListener {
            runBlocking {
                // Launch a coroutine in the IO dispatcher
                withContext(Dispatchers.IO) {
                    println(Model.getInstanceWC().client.nazwaUzytkownika)
                    Model.getInstanceWC().dataBaseDriver.createReport(binding.editText.text.toString(),Model.getInstanceWC().client.idPracownika, Model.getInstanceWC().client.nazwaUzytkownika)
                }
                binding.editText.setText("")
                binding.text.setText("Bug report send successfully")
            }
        }
        return (binding.root)
    }

}