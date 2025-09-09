package eu.pl.snk.senseibunny.museomaster.controllers.utilsControllers

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Display.Mode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import eu.pl.snk.senseibunny.museomaster.R
import eu.pl.snk.senseibunny.museomaster.controllers.CuratorControllers.SearchedExhibitsFragment
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentSearchExhibitBinding
import eu.pl.snk.senseibunny.museomaster.models.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class SearchExhibitFragment : Fragment() {

    private lateinit var fragmentManage: FragmentManager
    private lateinit var binding: FragmentSearchExhibitBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchExhibitBinding.inflate(inflater, container, false)
        fragmentManage = requireActivity().supportFragmentManager

        runBlocking {
            withContext(Dispatchers.IO){
                Model.getInstance(context).clearAllRooms()
                Model.getInstance(context).setAllRooms()
                val newItem = "null"
                val rooms = Model.getInstance(context).allRooms
                rooms.add(0, newItem)
                val adapter: ArrayAdapter<String?> =
                    ArrayAdapter<String?>(requireContext(), android.R.layout.simple_spinner_item, rooms)
                binding.roomSpinner.adapter = adapter
                binding.roomSpinner.setSelection(-1)
            }
        }
        binding.searchExhibitBtn.setOnClickListener {
            runBlocking {
                withContext(Dispatchers.IO) {
                    searchExhibits()
                }
            }
        }
        return (binding.root)
    }
    private fun openFragment(fragment: Fragment){
        val fragmentTransaction: FragmentTransaction = fragmentManage.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_curator, fragment)
        fragmentTransaction.commit()
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

        Model.getInstance(context).clearSearchedExhibits()
        Model.getInstance(context).setExhibitsSearched(
            name, author, topic, year1, year2, place
        )


        if(Model.getInstanceWC().searchTaskFlag==1){
            Model.getInstanceWC().searchTaskFlag=0
            val intent = Intent(requireContext(), ExhibitTaskAdd::class.java)
            startActivity(intent)
        }
        else if (Model.getInstance(context).isSetSearchedExhibitsSuccessFlag){
            openFragment(SearchedExhibitsFragment())
        } else {
            activity?.runOnUiThread {
                binding.errorLbl.setTextColor(Color.RED)
                binding.errorLbl.setText("Error, check your configuration")
                binding.errorLbl.visibility = View.VISIBLE
            }
        }
    }
}