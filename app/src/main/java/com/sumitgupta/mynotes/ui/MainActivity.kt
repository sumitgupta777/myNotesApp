package com.sumitgupta.mynotes.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.sumitgupta.mynotes.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //finding navigation host fragment
        val navController = Navigation.findNavController(this,R.id.fragment)
       NavigationUI.setupActionBarWithNavController(this,navController)

    }

    // to handle the back button in navigation
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this,R.id.fragment),
            null
        )
    }

    override fun onBackPressed() {



        val frag=supportFragmentManager.findFragmentById(R.id.nav_graph)

        when(frag){
            is HomeFragment-> ActivityCompat.finishAffinity(this)

            else->super.onBackPressed()
        }




    }
}
