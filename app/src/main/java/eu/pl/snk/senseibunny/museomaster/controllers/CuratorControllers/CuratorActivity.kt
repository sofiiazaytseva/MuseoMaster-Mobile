package eu.pl.snk.senseibunny.museomaster.controllers.CuratorControllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import eu.pl.snk.senseibunny.museomaster.R
import eu.pl.snk.senseibunny.museomaster.controllers.utilsControllers.*
import eu.pl.snk.senseibunny.museomaster.databinding.ActivityCuratorBinding

class CuratorActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var fragmentManage: FragmentManager
    private lateinit var binding : ActivityCuratorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCuratorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val toogle = ActionBarDrawerToggle(this,binding.drawerLayout, binding.toolbar,R.string.nav_open,R.string.nav_close)
        binding.drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        fragmentManage = supportFragmentManager
        openFragment(CreateExhibitionFragment());
        binding.navigationDrawer.setNavigationItemSelectedListener(this)

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.taskList -> {openFragment(TaskListFragment())}
            R.id.taskFinished-> {openFragment(EndedTaskListFragment())}
            R.id.taskAssignedTo -> {openFragment(AssignedToListFragment())}
            R.id.AssignTask -> {openFragment(UserSearchFragment())}
            R.id.nav_create_exh -> {openFragment(CreateExhibitionFragment())}
            R.id.nav_exh_list -> {openFragment(ExhibitionsListFragment())}
            R.id.add_exhibit -> {openFragment(AddExhibitFragment())}
            R.id.exhibit_list -> {openFragment(ExhibitListFragment())}
            R.id.search_exhibit -> {openFragment(SearchExhibitFragment())}
            R.id.nav_bugs -> {openFragment(BugFragment())}
            R.id.logout->finish()
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.getOnBackPressedDispatcher().onBackPressed()
        }
    }

    private fun openFragment(fragment: Fragment){
        val fragmentTransaction: FragmentTransaction = fragmentManage.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container_curator, fragment)
        fragmentTransaction.commit()
    }
}