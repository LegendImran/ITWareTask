package com.developer.code.itwaremachinetest.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.developer.code.itwaremachinetest.databinding.ActivityMainBinding
import com.developer.code.itwaremachinetest.utils.ResultHandler
import com.developer.code.itwaremachinetest.viewModels.AuthentiCationViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    val authVM: AuthentiCationViewModel by viewModels()

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUp()
        addObserver()

    }

    private fun setUp() {
        binding.login.setOnClickListener {
            validateField()?.let {
                Toast.makeText(this,it,Toast.LENGTH_SHORT)
            }?:run{
                authVM.authenticateUser(binding.etUser.text.trim().toString(),binding.etPass.text.trim().toString(),this)
            }
        }

    }

    private fun validateField(): String? {
        if(binding.etUser.text.trim().toString().isNullOrEmpty()){
            return "Please enter valid username"
        }else if(binding.etPass.text.trim().toString().isNullOrEmpty()){
            return "Please enter password"
        }
        return null
    }

    private fun addObserver() {
        authVM.observeAuthResponse().observe(
            this
        ) {
            when(it){
                is ResultHandler.Success ->{
                    it.value?.let { res->
                        if(res.isSuccess){
                            Intent(this, MovieListActivity::class.java).also {
                                it.putExtra("USERNAME", binding.etUser.text.toString().trim())
                                startActivity(it)
                                finishAffinity()
                            }
                        }else{
                            Toast.makeText(this,res.messsage,Toast.LENGTH_SHORT).show()
                        }
                    }
                } 
                is ResultHandler.Failure ->{

                    Toast.makeText(this,it.error?.messsage,Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
}