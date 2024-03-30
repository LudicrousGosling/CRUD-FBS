package com.example.crudfb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.crudfb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val navController = findNavController(R.id.fragmentContainerView)
        //val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.addFragment))
        //setupActionBarWithNavController(navController,appBarConfiguration)


    }
}