package eu.pl.snk.senseibunny.museomaster.controllers.utilsControllers

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentTransaction
import eu.pl.snk.senseibunny.museomaster.R
import eu.pl.snk.senseibunny.museomaster.controllers.CuratorControllers.SearchedExhibitsFragment
import eu.pl.snk.senseibunny.museomaster.controllers.PermissionWorkerControllers.PermissionWorkerActivity
import eu.pl.snk.senseibunny.museomaster.databinding.ActivityDetailAndAddTaskBinding
import eu.pl.snk.senseibunny.museomaster.models.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Calendar

class DetailAndAddTask : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAndAddTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAndAddTaskBinding.inflate(layoutInflater)

        binding.addTaskButton.setOnClickListener {
            setTask()
        }
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            showDatePicker(binding.startDate)
        }
        binding.endBtn.setOnClickListener {
            showDatePicker(binding.endDate)
        }

        binding.ExhibitButton.setOnClickListener {
            if (isWorkerAssignedDifferent() && Model.getInstanceWC().getWorkersAssigned().size<=1) { //Checking if worker is technician
                val startDate = convertStringToDate(binding.startDate.text.toString())
                val endDate = convertStringToDate(binding.endDate.text.toString())
                Model.getInstanceWC().setTaskVars(binding.DescEditText.text.toString(),binding.TopicText.text.toString(),startDate,endDate);
                goToExFragment()
            }
            else{
                binding.error.setText("Not a technician selected")
                binding.error.setTextColor(Color.RED)
            }

            }
        }

        private fun setTask(){
            var subject = binding.TopicText.text.toString()
            var desc=binding.DescEditText.text.toString()
            val startDate = convertStringToDate(binding.startDate.text.toString())
            val endDate = convertStringToDate(binding.endDate.text.toString())
            val nazwa = Model.getInstanceWC().client.nazwaUzytkownika
            println(subject)
            println(desc)
            println(startDate)
            println(endDate)

            if (subject.isNotEmpty() && desc.isNotEmpty() && startDate != null && endDate != null) {
                Model.getInstanceWC().setTaskVars(desc,subject, startDate,endDate);
                runBlocking {
                    withContext(Dispatchers.IO) {
                        for (client in Model.getInstanceWC().workersAssigned) {
                            Model.getInstanceWC()
                                .createTask(client.idPracownika, client.nazwaUzytkownika, nazwa)
                        }

                        goBackToPermissionWorkerActivity()
                    }
                }



            }
            else{
                runOnUiThread {
                    binding.error.setText("Invalid formula")
                    binding.error.setTextColor(Color.RED)
                }

            }

        }

        private fun showDatePicker(textView : TextView) {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
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
                //xd
            }
        }

        private fun goBackToPermissionWorkerActivity() {
            runOnUiThread {
                Toast.makeText(this, "Task assigned", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

    private fun goToExFragment() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
        finish()

    }
        fun isWorkerAssignedDifferent(): Boolean {
            for (worker in Model.getInstanceWC().workersAssigned) {
                val position = worker.rola
                println(position)
                if (position != "Pracownik Techniczny" && position != "Pracownik Techniczny+") {
                    return false
                }
            }
            return true
        }
    }



