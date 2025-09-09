package eu.pl.snk.senseibunny.museomaster.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import eu.pl.snk.senseibunny.museomaster.databinding.CustomTaskDescPopupBinding
import eu.pl.snk.senseibunny.museomaster.databinding.TaskItemBinding
import eu.pl.snk.senseibunny.museomaster.databinding.TaskItemFailBinding
import eu.pl.snk.senseibunny.museomaster.databinding.TaskItemProblemBinding
import eu.pl.snk.senseibunny.museomaster.databinding.TaskItemSuccessBinding
import eu.pl.snk.senseibunny.museomaster.models.Model
import eu.pl.snk.senseibunny.museomaster.models.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class FinishedTaskAdapter (private val FinishedTaskList: ArrayList<Task>) : RecyclerView.Adapter<FinishedTaskAdapter.FinishedTaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinishedTaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = when (viewType) {
            VIEW_TYPE_SUCCESS -> TaskItemSuccessBinding.inflate(inflater, parent, false)
            VIEW_TYPE_PROBLEM -> TaskItemProblemBinding.inflate(inflater, parent, false)
            VIEW_TYPE_FAIL -> TaskItemFailBinding.inflate(inflater, parent, false)
            else -> throw IllegalArgumentException("Invalid view type")
        }
        return FinishedTaskViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        val task = FinishedTaskList[position]

        return when (task.status) {
            "Zakonczone" -> VIEW_TYPE_SUCCESS
            "Problem" -> VIEW_TYPE_PROBLEM
            "Fail" -> VIEW_TYPE_FAIL
            else -> VIEW_TYPE_FAIL
        }
    }

    companion object {
        private const val VIEW_TYPE_SUCCESS = 0
        private const val VIEW_TYPE_PROBLEM = 1
        private const val VIEW_TYPE_FAIL = 2
    }

    override fun onBindViewHolder(holder: FinishedTaskViewHolder, position: Int) {
        val task = FinishedTaskList[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return FinishedTaskList.size
    }

    inner class FinishedTaskViewHolder(val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            when (binding) {
                is TaskItemSuccessBinding -> {
                    // Bind success task view
                    binding.nameTextView.text = task.nazwaUzytkownikaNadajacego
                }
                is TaskItemProblemBinding -> {
                    // Bind problem task view
                    binding.nameTextView.text = task.nazwaUzytkownikaNadajacego
                }
                is TaskItemFailBinding -> {
                    // Bind fail task view
                    binding.nameTextView.text = task.nazwaUzytkownikaNadajacego
                }
            }
        }

    }
}