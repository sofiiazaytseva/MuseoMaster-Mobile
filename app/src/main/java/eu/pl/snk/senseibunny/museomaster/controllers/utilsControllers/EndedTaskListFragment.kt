package eu.pl.snk.senseibunny.museomaster.controllers.utilsControllers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.pl.snk.senseibunny.museomaster.R
import eu.pl.snk.senseibunny.museomaster.adapters.FinishedTaskAdapter
import eu.pl.snk.senseibunny.museomaster.adapters.TaskAdapter
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentEndedTaskListBinding
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentReportBinding
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentTaskListBinding
import eu.pl.snk.senseibunny.museomaster.models.Model
import eu.pl.snk.senseibunny.museomaster.models.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class EndedTaskListFragment : Fragment() {

    private lateinit var binding: FragmentEndedTaskListBinding
    private var finishedTasks: ArrayList<Task> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEndedTaskListBinding.inflate(inflater, container, false)

        runBlocking {
            // Launch a coroutine in the IO dispatcher
            withContext(Dispatchers.IO) {
                Model.getInstanceWC().clearFinishedTasks()
                Model.getInstance(context).setTasks("finished")
                finishedTasks = Model.getInstance(context).fishedTasks
            }
        }

        val adapter= FinishedTaskAdapter(finishedTasks)
        binding.recyclerView.adapter=adapter

        return (binding.root)
    }

}