package eu.pl.snk.senseibunny.museomaster.controllers.utilsControllers

import android.database.SQLException
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.pl.snk.senseibunny.museomaster.R
import eu.pl.snk.senseibunny.museomaster.adapters.AssignedToTaskAdapter
import eu.pl.snk.senseibunny.museomaster.adapters.FinishedTaskAdapter
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentAssignedToListBinding
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentBugBinding
import eu.pl.snk.senseibunny.museomaster.models.Model
import eu.pl.snk.senseibunny.museomaster.models.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.sql.ResultSet
import java.util.Timer
import java.util.TimerTask


class AssignedToListFragment : Fragment() {

    private lateinit var binding: FragmentAssignedToListBinding
    private var assignedTasks: ArrayList<Task> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAssignedToListBinding.inflate(inflater, container, false)
        Model.getInstance(context)
        runBlocking {
            // Launch a coroutine in the IO dispatcher
            withContext(Dispatchers.IO) {
                Model.getInstance(context).clearFinishedTasks()
                Model.getInstanceWC().setTasks("assignedTo")
                assignedTasks = Model.getInstance(context).assignedToTasks
                println(assignedTasks.toString())
            }
        }

        val adapter= AssignedToTaskAdapter(assignedTasks)
        binding.recyclerView.adapter=adapter


        val timer = Timer()
        val delay = 0L // Delay before the task starts (in milliseconds)
        val period = 5000L // Interval between each execution (in milliseconds)

        //check every 5 sec for new tasks

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runBlocking {
                    val assignedTasks: ArrayList<Task> = Model.getInstanceWC().assignedToTasks
                    val idTaskAssigned: ArrayList<Int>
                    var resultSet: ResultSet? = null

                    for (zadanie in assignedTasks) {
                        resultSet = Model.getInstanceWC().dataBaseDriver.getAssignedTaskState(zadanie.idZadania)
                        try {
                            if (resultSet.next()) {
                                if (zadanie.status != resultSet.getString("status")) {
                                    zadanie.status = resultSet.getString("status")
                                    activity?.runOnUiThread {
                                        adapter.notifyDataSetChanged()
                                        println("Znaleziono zadanie")
                                    }
                                }
                            }
                        } catch (e: SQLException) {
                            throw RuntimeException(e)
                        }
                    }

                }


            }
        }, delay, period)

        return (binding.root)
    }

}