package eu.pl.snk.senseibunny.museomaster.adapters

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import eu.pl.snk.senseibunny.museomaster.databinding.CustomReportPopupBinding
import eu.pl.snk.senseibunny.museomaster.databinding.CustomTaskDescPopupBinding
import eu.pl.snk.senseibunny.museomaster.databinding.ReportListItemBinding
import eu.pl.snk.senseibunny.museomaster.databinding.TaskItemBinding
import eu.pl.snk.senseibunny.museomaster.models.Model
import eu.pl.snk.senseibunny.museomaster.models.Report
import eu.pl.snk.senseibunny.museomaster.models.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class    TaskAdapter(private val taskList: ArrayList<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class TaskViewHolder(val itemBinding: TaskItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.details.setOnClickListener {
                val task = taskList[adapterPosition]
                showPopup(task)
            }
            itemBinding.success.setOnClickListener() {
                if (taskList.isNotEmpty()) {


                    val task = taskList[adapterPosition]
                    task.status="Zakonczone"
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        taskList.removeAt(position)
                        notifyItemRemoved(position)

                        runBlocking {
                            // Launch a coroutine in the IO dispatcher
                            withContext(Dispatchers.IO) {
                                Model.getInstanceWC().getDataBaseDriver()
                                    .setAssignedTask("Zakonczone", task.getIdZadania());//in db

                            }

                        }

                        Model.getInstanceWC().removeTask(task);//in app

                    }

                }
            }
            itemBinding.problem.setOnClickListener() {
                if (taskList.isNotEmpty()) {


                    val task = taskList[adapterPosition]
                    task.status="Problem"
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        taskList.removeAt(position)
                        notifyItemRemoved(position)

                        runBlocking {
                            // Launch a coroutine in the IO dispatcher
                            withContext(Dispatchers.IO) {
                                Model.getInstanceWC().getDataBaseDriver()
                                    .setAssignedTask("Problem", task.getIdZadania());//in db

                            }

                        }

                        Model.getInstanceWC().removeTask(task);//in app

                    }

                }
            }

            itemBinding.failure.setOnClickListener() {
                if (taskList.isNotEmpty()) {


                    val task = taskList[adapterPosition]
                    val position = adapterPosition
                    task.status="Fail"
                    if (position != RecyclerView.NO_POSITION) {
                        taskList.removeAt(position)
                        notifyItemRemoved(position)

                        runBlocking {
                            // Launch a coroutine in the IO dispatcher
                            withContext(Dispatchers.IO) {
                                Model.getInstanceWC().getDataBaseDriver()
                                    .setAssignedTask("Fail", task.getIdZadania());//in db

                            }

                        }

                        Model.getInstanceWC().removeTask(task);//in app

                    }

                }
            }
        }
            fun bind(task: Task) {
                itemBinding.nameTextView.text = task.nazwaUzytkownikaNadajacego
            }

            fun showPopup(task: Task) {
                val popupBinding: CustomTaskDescPopupBinding = CustomTaskDescPopupBinding.inflate(
                    LayoutInflater.from(itemBinding.root.context)
                )
                val popupView: View = popupBinding.root
                val popupWindow = PopupWindow(
                    popupView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true
                )


                // Set the focusable property to true
                popupWindow.isFocusable = true

                // Set an OnTouchListener to consume touch events
                popupView.setOnTouchListener { _, _ -> true }

                popupBinding.name.text = "Username: " + task.nazwaUzytkownikaNadajacego
                popupBinding.desc.text = "Description: " + task.opis

                popupBinding.closeButton.setOnClickListener {
                    popupWindow.dismiss()
                }


                // Show the popup window in the center of the screen
                popupWindow.showAtLocation(itemBinding.root, Gravity.CENTER, 0, 0)
            }

    }
}