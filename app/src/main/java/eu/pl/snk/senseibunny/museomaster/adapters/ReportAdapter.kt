package eu.pl.snk.senseibunny.museomaster.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import eu.pl.snk.senseibunny.museomaster.adapters.ReportAdapter.ReportViewHolder
import eu.pl.snk.senseibunny.museomaster.databinding.CustomReportPopupBinding
import eu.pl.snk.senseibunny.museomaster.databinding.CustomUserPopupBinding
import eu.pl.snk.senseibunny.museomaster.databinding.ReportListItemBinding
import eu.pl.snk.senseibunny.museomaster.databinding.UserListItemBinding
import eu.pl.snk.senseibunny.museomaster.models.Client
import eu.pl.snk.senseibunny.museomaster.models.Model
import eu.pl.snk.senseibunny.museomaster.models.Report
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class ReportAdapter(private val reportList: ArrayList<Report>) : RecyclerView.Adapter<ReportViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = ReportListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val report = reportList[position]
        holder.bind(report)
    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    inner class ReportViewHolder(val itemBinding: ReportListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private val titleTextView: TextView = itemBinding.nameTextView


        init {
            itemBinding.otherIcon.setOnClickListener {
                val report = reportList[adapterPosition]
                showPopup(report)
            }
        }
        fun bind(report: Report) {
            titleTextView.text = report.nazwaUzytkownika
        }

        fun showPopup(report: Report) {
            val popupBinding: CustomReportPopupBinding = CustomReportPopupBinding.inflate(LayoutInflater.from(itemBinding.root.context))
            val popupView: View = popupBinding.root
            val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)


            // Set the focusable property to true
            popupWindow.isFocusable = true

            // Set an OnTouchListener to consume touch events
            popupView.setOnTouchListener { _, _ -> true }

            popupBinding.name.text="Username: "+ report.nazwaUzytkownika
            popupBinding.desc.text="Description: "+ report.opis

            popupBinding.closeButton.setOnClickListener {
                popupWindow.dismiss()
            }

            popupBinding.deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {

                    reportList.removeAt(position)
                    notifyItemRemoved(position)
                    popupWindow.dismiss()
                }
                runBlocking {
                    // Launch a coroutine in the IO dispatcher
                    withContext(Dispatchers.IO) {
                        Model.getInstanceWC().dataBaseDriver.deleteReport(report.idReportu)

                    }

                }
            }

            // Show the popup window in the center of the screen
            popupWindow.showAtLocation(itemBinding.root, Gravity.CENTER, 0, 0)
        }
    }
}