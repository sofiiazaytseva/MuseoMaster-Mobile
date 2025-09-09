package eu.pl.snk.senseibunny.museomaster.controllers.utilsControllers

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import eu.pl.snk.senseibunny.museomaster.R
import eu.pl.snk.senseibunny.museomaster.controllers.CuratorControllers.SearchedExhibitsFragment
import eu.pl.snk.senseibunny.museomaster.databinding.ActivityExhibitTaskAddBinding
import eu.pl.snk.senseibunny.museomaster.databinding.ActivitySearchBinding
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentSearchExhibitBinding
import eu.pl.snk.senseibunny.museomaster.models.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var rooms: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySearchBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        runBlocking {
            withContext(Dispatchers.IO){
                Model.getInstanceWC().clearAllRooms()
                Model.getInstanceWC().setAllRooms()
                val newItem = "null"
                rooms = Model.getInstanceWC().allRooms
                rooms.add(0, newItem)


            }
        }

        val adapter: ArrayAdapter<String?> =
            ArrayAdapter<String?>(this, android.R.layout.simple_spinner_item,
                rooms as List<String?>
            )
        binding.roomSpinner.adapter = adapter
        binding.roomSpinner.setSelection(-1)


        binding.searchExhibitBtn.setOnClickListener {
            runBlocking {
                withContext(Dispatchers.IO) {
                    searchExhibits()
                }
            }
        }
    }

    private fun searchExhibits(){
        val name = binding.exhibitName.text.toString()
        val author = binding.author.text.toString()
        val topic = binding.topic.text.toString()
        var year1 = binding.startDate.text.toString().toIntOrNull()
        var year2 = binding.endDate.text.toString().toIntOrNull()
        if (year1 == null){ year1 = 10000 }
        if (year2 == null){ year2 = 10000 }
        val spinner = binding.roomSpinner
        var place: String? = null
        if (spinner.selectedItemPosition != -1){
            place = spinner.selectedItem as String
        }

        Model.getInstanceWC().clearSearchedExhibits()
        Model.getInstanceWC().setExhibitsSearched(
            name, author, topic, year1, year2, place
        )

        if (Model.getInstanceWC().isSetSearchedExhibitsSuccessFlag){
            val intent = Intent(this, ExhibitTaskAdd::class.java)
            startActivity(intent)
            finish()
        } else {
            runOnUiThread {
                binding.errorLbl.setTextColor(Color.RED)
                binding.errorLbl.setText("Error, check your configuration")
                binding.errorLbl.visibility = View.VISIBLE
            }
        }
    }
}