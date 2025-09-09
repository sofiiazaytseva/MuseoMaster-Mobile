package eu.pl.snk.senseibunny.museomaster.controllers.CuratorControllers

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import java.sql.Date
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentCreateExhibitionBinding
import eu.pl.snk.senseibunny.museomaster.models.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class CreateExhibitionFragment : Fragment() {

    private lateinit var binding: FragmentCreateExhibitionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateExhibitionBinding.inflate(inflater, container, false)
        runBlocking {
            withContext(Dispatchers.IO){
                Model.getInstance(context).clearRooms()
                Model.getInstance(context).setRoom()
                val rooms = Model.getInstance(context).rooms
                val adapter: ArrayAdapter<String?> = ArrayAdapter<String?>(requireContext(), android.R.layout.simple_spinner_item, rooms)
                binding.roomName.adapter = adapter
            }
        }
        binding.startBtn.setOnClickListener {
            showDatePicker(binding.startDate)
        }
        binding.endBtn.setOnClickListener{
            showDatePicker(binding.endDate)
        }
        binding.addExhibition.setOnClickListener{
            runBlocking {
                withContext(Dispatchers.IO){
                    createExhibition()
                }
            }

        }

        return (binding.root)
    }

    fun createExhibition(){
        val name = binding.exhibitionName.text.toString()
        val spinner = binding.roomName
        val room = spinner.selectedItem as String
        val place = binding.placeName.text.toString()
        val topic = binding.theme.text.toString()
        val author = binding.creator.text.toString()
        val startDate = convertStringToDate(binding.startDate.text.toString())!!
        val endDate = convertStringToDate(binding.endDate.text.toString())!!

        Model.getInstance(context).dataBaseDriver.createExhibition(
            name, room, place, topic, author, startDate, endDate
        )
        if(Model.getInstance(context).dataBaseDriver.createExhibitionSuccessFlag){
            activity?.runOnUiThread {
                binding.errorLbl.visibility= View.VISIBLE
                binding.errorLbl.setTextColor(Color.GREEN)
                binding.errorLbl.setText("Exhibition created succefully!")

                binding.exhibitionName.setText("")
                binding.roomName.setSelection(0)
                binding.placeName.setText("")
                binding.theme.setText("")
                binding.creator.setText("")
                binding.startDate.setText("0000-00-00")
                binding.endDate.setText("0000-00-00")
            }
        } else {
            activity?.runOnUiThread {
                binding.errorLbl.setTextColor(Color.RED)
                binding.errorLbl.setText("Error, check your configuration")
                binding.errorLbl.visibility = View.VISIBLE
            }
        }

    }

    private fun showDatePicker(textView : TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
            textView.text = selectedDate
        }, year, month, day)

        datePickerDialog.show()
    }
    fun convertStringToDate(dateString: String): Date? {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return try {
            val utilDate = format.parse(dateString)
            Date(utilDate.time)
        } catch (e: Exception) {
            null
        }
    }

}