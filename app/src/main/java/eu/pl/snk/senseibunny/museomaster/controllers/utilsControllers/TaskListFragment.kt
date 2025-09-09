package eu.pl.snk.senseibunny.museomaster.controllers.utilsControllers

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.pl.snk.senseibunny.museomaster.adapters.TaskAdapter
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentTaskListBinding
import eu.pl.snk.senseibunny.museomaster.models.Model
import eu.pl.snk.senseibunny.museomaster.models.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.Timer
import java.util.TimerTask

class TaskListFragment : Fragment() {

    private lateinit var binding: FragmentTaskListBinding
    private var tasks: ArrayList<Task> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(inflater, container, false)

        runBlocking {
            // Launch a coroutine in the IO dispatcher
            withContext(Dispatchers.IO) {
                Model.getInstanceWC().clearTasks()
                Model.getInstance(context).setTasks("assigned")
                tasks = Model.getInstance(context).tasks

            }
        }

        val adapter= TaskAdapter(tasks)
        binding.recyclerView.adapter=adapter

        val timer = Timer()
        val delay = 0L // Delay before the task starts (in milliseconds)
        val period = 5000L // Interval between each execution (in milliseconds)

        //check every 5 sec for new tasks
        var currentSize=0
        var tableSizeNew=0
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runBlocking {
                    // Launch a coroutine in the IO dispatcher
                    withContext(Dispatchers.IO) {
                        currentSize = tasks.size
                        tableSizeNew = Model.getInstanceWC().getDataBaseDriver().getSizeAssignedTask(13)
                        println("przed"+currentSize)
                        println("po"+tableSizeNew)

                    }

                    if (currentSize != tableSizeNew) {
                        currentSize = tableSizeNew
                        Model.getInstanceWC().clearTasks()
                        Model.getInstanceWC().setTasks("assigned")
                        println("XD")

                        activity?.runOnUiThread {
                            adapter.notifyDataSetChanged()
                            println("Znaleziono zadanie")
                        }
                    }

                }


            }
        }, delay, period)



        return (binding.root)
    }

 }