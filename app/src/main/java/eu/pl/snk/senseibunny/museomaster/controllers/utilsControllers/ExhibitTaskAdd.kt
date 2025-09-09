package eu.pl.snk.senseibunny.museomaster.controllers.utilsControllers

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import eu.pl.snk.senseibunny.museomaster.adapters.ExhibitTaskAdapter
import eu.pl.snk.senseibunny.museomaster.databinding.ActivityExhibitTaskAddBinding
import eu.pl.snk.senseibunny.museomaster.models.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class ExhibitTaskAdd : AppCompatActivity() {

    private lateinit var binding: ActivityExhibitTaskAddBinding
    private lateinit var rooms: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityExhibitTaskAddBinding.inflate(layoutInflater)
        setContentView(binding.root)


        println( Model.getInstanceWC().exhibitsSearched)
        val adapter = ExhibitTaskAdapter(Model.getInstanceWC().exhibitsSearched)
        binding.recyclerView.adapter=adapter

        runBlocking {
            withContext(Dispatchers.IO){
                Model.getInstanceWC().clearAllRooms()
                Model.getInstanceWC().setAllRooms()
                val newItem = "null"
                rooms = Model.getInstanceWC().allRooms
                rooms.add(0, newItem)
            }
        }

        val adapter2: ArrayAdapter<String?> =
            ArrayAdapter<String?>(this, android.R.layout.simple_spinner_item,
                rooms as List<String?>
            )
        binding.spinnerSearch.adapter = adapter2
        binding.spinnerSearch.setSelection(-1)

        binding.button.setOnClickListener{
            setTask()
        }
    }

    fun setTask(){
        var nazwa: String?
        var spinner=binding.spinnerSearch.selectedItem as String
        if(Model.getInstanceWC().exAssigned.isNotEmpty() && spinner!="null" ) {
            for (client in Model.getInstanceWC().workersAssigned) {
                nazwa = Model.getInstanceWC().client.nazwaUzytkownika
                val exhibitIds: MutableList<Int> = ArrayList()

                for (exhibit in Model.getInstanceWC().exAssigned) {
                    val exhibitId: Int = exhibit.idZabytku
                    exhibitIds.add(exhibitId)
                }
                Model.getInstanceWC().changeDesc("Move exhibits with ID: $exhibitIds")

                runBlocking {
                    withContext(Dispatchers.IO) {
                        Model.getInstanceWC()
                            .createTask(client.idPracownika, client.nazwaUzytkownika, nazwa)
                    }
                }

                for (ex in Model.getInstanceWC().exAssigned) {
                    val ex_id: Int = ex.idZabytku
                    val worker_id = client.idPracownika
                    runBlocking {
                        withContext(Dispatchers.IO) {
                            Model.getInstanceWC().dataBaseDriver.createEksponatZadanie(
                                worker_id,
                                ex_id
                            )
                        }
                    }
                }
            }

            for (ex in Model.getInstanceWC().exAssigned) {
                runBlocking {
                    withContext(Dispatchers.IO) {
                        Model.getInstanceWC().dataBaseDriver.UpdateEx(
                            "Przenieś",
                            binding.spinnerSearch.selectedItem as String,
                            ex.idZabytku
                        )
                    }
                }
            }

            finish()
        }
        else{
            Toast.makeText(this, "No exhibits or room assigned", Toast.LENGTH_SHORT).show()
        }
    }
}