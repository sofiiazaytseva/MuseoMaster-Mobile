package eu.pl.snk.senseibunny.museomaster.controllers.AdminControllers

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import eu.pl.snk.senseibunny.museomaster.R
import eu.pl.snk.senseibunny.museomaster.databinding.FragmentAddUserBinding
import eu.pl.snk.senseibunny.museomaster.models.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.sql.ResultSet


class AddUserFragment : Fragment() {

    private lateinit var binding: FragmentAddUserBinding
    private var permission=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddUserBinding.inflate(inflater, container, false)

        val positions = arrayOf("Pracownik","Pracownik Techniczny","Kurator" )
        val adapter: ArrayAdapter<String?> = ArrayAdapter<String?>(requireContext(), android.R.layout.simple_spinner_item, positions)
        binding.position.adapter=adapter

        val checkBox = binding.permission

        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                permission = 1
            } else {
                permission = 0
            }
        }

        binding.addUser.setOnClickListener{
            runBlocking {
                // Launch a coroutine in the IO dispatcher
                withContext(Dispatchers.IO) {
                    addUser()
                }

            }
        }



        binding.name.addTextChangedListener(){
            updateClientUsername()
        }
        binding.secondName.addTextChangedListener(){
            updateClientUsername()
        }
        return (binding.root)
    }

    fun addUser(){
        var name =binding.name.text.toString()
        var secondName = binding.secondName.text.toString()
        var email = binding.email.text.toString()
        var phone = binding.phone.text.toString()
        val phoneInt = phone.toIntOrNull()
        var age = binding.age.text.toString()
        val ageInt = age.toIntOrNull()
        var spinner = binding.position
        var rola=spinner.selectedItem as String
        val password = binding.password.text.toString()
        var userName =binding.username.text.toString()

        if(permission==1){
            if (rola.equals("Pracownik") || rola.equals("Pracownik Techniczny")) {
                rola = rola.plus("+");
            }
        }

        if (name.isEmpty() || secondName.isEmpty() || email.isEmpty() || password.isEmpty()) {

            activity?.runOnUiThread {
                binding.errorLbl.setTextColor(Color.RED)
                binding.errorLbl.setText("Error empty fields")
                binding.errorLbl.visibility= View.VISIBLE
            }

        } else {
            Model.getInstance(context).getDataBaseDriver().createClient(name, secondName, email, ageInt, permission, rola, phoneInt, userName, password);

            activity?.runOnUiThread {
                binding.errorLbl.visibility= View.VISIBLE
                binding.errorLbl.setTextColor(Color.GREEN)
                binding.errorLbl.setText("User created succefully!")

                binding.name.setText("")
                binding.secondName.setText("")
                binding.email.setText("")
                binding.phone.setText("")
                binding.age.setText("")
                binding.position.setSelection(0)
                binding.password.setText("")
                binding.username.setText("")
            }
        }
    }

    private fun updateClientUsername() {
        val min = 1 // Minimum value of range
        val max = 200 // Maximum value of range
        val x = (Math.random() * (max - min + 1) + min).toInt()

        val name = binding.name.text.toString()
        val secondName = binding.secondName.text.toString()

        if (name.isNotBlank() && secondName.isNotBlank()) {
            val fchar = name.toLowerCase()[0]
            val username = "@" + fchar + secondName + x
            binding.username.setText(username)
        }
    }
}