package eu.pl.snk.senseibunny.museomaster.controllers.AdminControllers

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import eu.pl.snk.senseibunny.museomaster.R
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentAddRoomBinding
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentAddUserBinding
import eu.pl.snk.senseibunny.museomaster.models.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class AddRoomFragment : Fragment() {

    private lateinit var binding: FragmentAddRoomBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRoomBinding.inflate(inflater, container, false)
        val types = arrayOf("Exhibit room","Storage room" )
        val adapter: ArrayAdapter<String?> = ArrayAdapter<String?>(requireContext(), android.R.layout.simple_spinner_item, types)
        binding.typeRoom.adapter=adapter

        binding.addRoom.setOnClickListener{
            runBlocking {
                // Launch a coroutine in the IO dispatcher
                withContext(Dispatchers.IO) {
                    createRoom()
                }

            }
        }
        return (binding.root)
    }

    fun createRoom(){
        val name = binding.name.text.toString()
        val size=binding.size.text.toString()
        val intSize=size.toIntOrNull()
        var spinner = binding.typeRoom
        var type=spinner.selectedItem as String

        if (name.isEmpty() || type.isEmpty()) {

            activity?.runOnUiThread {
                binding.errorLbl.setTextColor(Color.RED)
                binding.errorLbl.setText("Error empty fields")
                binding.errorLbl.visibility= View.VISIBLE
            }

        } else {
            Model.getInstance(context).getDataBaseDriver().createRoom(intSize,name,type)

            activity?.runOnUiThread {
                binding.errorLbl.visibility= View.VISIBLE
                binding.errorLbl.setTextColor(Color.GREEN)
                binding.errorLbl.setText("Room created succefully!")

                binding.name.setText("")
                binding.size.setText("")
            }
        }
    }
}

