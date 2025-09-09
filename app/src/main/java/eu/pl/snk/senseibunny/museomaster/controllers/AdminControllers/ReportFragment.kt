package eu.pl.snk.senseibunny.museomaster.controllers.AdminControllers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.pl.snk.senseibunny.museomaster.R
import eu.pl.snk.senseibunny.museomaster.adapters.ClientAdapter
import eu.pl.snk.senseibunny.museomaster.adapters.ReportAdapter
import eu.pl.snk.senseibunny.museomaster.databinding.ActivityMainBinding
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentReportBinding
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentUserListBinding
import eu.pl.snk.senseibunny.museomaster.models.Client
import eu.pl.snk.senseibunny.museomaster.models.Model
import eu.pl.snk.senseibunny.museomaster.models.Report
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class ReportFragment : Fragment() {

    private var report: ArrayList<Report> = ArrayList()

    private lateinit var binding: FragmentReportBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReportBinding.inflate(inflater, container, false)

        runBlocking {
            // Launch a coroutine in the IO dispatcher
            withContext(Dispatchers.IO) {
                Model.getInstance(context).setReports()
                report = Model.getInstance(context).reports;
            }
            println(report)
        }

        val adapter= ReportAdapter(report)
        binding.recyclerView.adapter=adapter
        return binding.root
    }

}