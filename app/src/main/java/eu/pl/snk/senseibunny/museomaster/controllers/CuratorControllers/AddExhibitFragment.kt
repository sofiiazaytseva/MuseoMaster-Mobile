package eu.pl.snk.senseibunny.museomaster.controllers.CuratorControllers

import android.R
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentAddExhibitBinding
import eu.pl.snk.senseibunny.museomaster.models.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class AddExhibitFragment : Fragment() {

    private lateinit var binding : FragmentAddExhibitBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddExhibitBinding.inflate(inflater, container, false)
        runBlocking {
            withContext(Dispatchers.IO) {
                Model.getInstance(context).clearAllRooms()
                Model.getInstance(context).setAllRooms()
                val rooms = Model.getInstance(context).allRooms
                val adapter: ArrayAdapter<String?> =
                    ArrayAdapter<String?>(requireContext(), R.layout.simple_spinner_item, rooms)
                binding.CurrentStorageRoom.adapter = adapter
                binding.TargetStorageRoom.adapter = adapter
            }
        }
        binding.addExhibit.setOnClickListener {
            runBlocking {
                withContext(Dispatchers.IO){
                    createExhibit()
                }
            }
        }

        return (binding.root)
    }
    fun createExhibit(){
        val name = binding.exhibitName.text.toString()
        val period = binding.period.text.toString().toIntOrNull()
        val topic = binding.topic.text.toString()
        val author = binding.author.text.toString()
        val spinnerCurrent = binding.CurrentStorageRoom
        val currentRoom = spinnerCurrent.selectedItem as String
        val spinnerTarget = binding.TargetStorageRoom
        val targetRoom = spinnerTarget.selectedItem as String
        val desc = binding.description.text.toString()

        Model.getInstance(context).dataBaseDriver.createExhibit(
            name, period, topic, author, currentRoom, targetRoom, desc
        )
        if(Model.getInstance(context).dataBaseDriver.createExhibitSuccessFlag){
            activity?.runOnUiThread {
                binding.errorLbl.visibility= View.VISIBLE
                binding.errorLbl.setTextColor(Color.GREEN)
                binding.errorLbl.setText("Exhibit created succefully!")

                binding.exhibitName.text.clear()
                binding.period.text.clear()
                binding.topic.text.clear()
                binding.author.text.clear()
                binding.CurrentStorageRoom.setSelection(0)
                binding.TargetStorageRoom.setSelection(0)
                binding.description.text.clear()
            }
        } else {
            activity?.runOnUiThread {
                binding.errorLbl.setTextColor(Color.RED)
                binding.errorLbl.setText("Error, check your configuration")
                binding.errorLbl.visibility = View.VISIBLE
            }
        }
    }

}