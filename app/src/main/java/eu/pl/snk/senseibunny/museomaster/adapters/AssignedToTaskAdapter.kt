package eu.pl.snk.senseibunny.museomaster.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import eu.pl.snk.senseibunny.museomaster.controllers.utilsControllers.AssignedToListFragment
import eu.pl.snk.senseibunny.museomaster.databinding.TaskItemAssignedBinding
import eu.pl.snk.senseibunny.museomaster.databinding.TaskItemFailBinding
import eu.pl.snk.senseibunny.museomaster.databinding.TaskItemProblemBinding
import eu.pl.snk.senseibunny.museomaster.databinding.TaskItemSuccessBinding
import eu.pl.snk.senseibunny.museomaster.models.Task

class AssignedToTaskAdapter (private val assignedTaskList: ArrayList<Task>) : RecyclerView.Adapter<AssignedToTaskAdapter.AssignedToTaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignedToTaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        println("tutaj")
        val binding = when (viewType) {
            VIEW_TYPE_SUCCESS -> TaskItemSuccessBinding.inflate(inflater, parent, false)
            VIEW_TYPE_PROBLEM -> TaskItemProblemBinding.inflate(inflater, parent, false)
            VIEW_TYPE_FAIL -> TaskItemFailBinding.inflate(inflater, parent, false)
            else -> TaskItemAssignedBinding.inflate(inflater, parent, false)
        }
        return AssignedToTaskViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        val task = assignedTaskList[position]

        return when (task.status) {
            "Zakonczone" -> VIEW_TYPE_SUCCESS
            "Problem" -> VIEW_TYPE_PROBLEM
            "Fail" -> VIEW_TYPE_FAIL
            "wTrakcie"-> VIEW_TYPE_ASSIGNED
            else -> VIEW_TYPE_ASSIGNED
        }
    }

    companion object {
        private const val VIEW_TYPE_SUCCESS = 0
        private const val VIEW_TYPE_PROBLEM = 1
        private const val VIEW_TYPE_FAIL = 2
        private const val VIEW_TYPE_ASSIGNED = 3
    }

    override fun onBindViewHolder(holder: AssignedToTaskViewHolder, position: Int) {
        val task = assignedTaskList[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return assignedTaskList.size
    }

    inner class AssignedToTaskViewHolder(val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            when (binding) {
                is TaskItemSuccessBinding -> {
                    // Bind success task view
                    binding.nameTextView.text = task.nazwaUzytkownika
                }

                is TaskItemProblemBinding -> {
                    // Bind problem task view
                    binding.nameTextView.text = task.nazwaUzytkownika
                }

                is TaskItemFailBinding -> {
                    // Bind fail task view
                    binding.nameTextView.text = task.nazwaUzytkownika
                }
                is TaskItemAssignedBinding->{
                    binding.nameTextView.text=task.nazwaUzytkownika
                }
            }
        }

    }
}