package com.example.app_base_siskit


import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.app_base_siskit.feature_login.presentation.login.LoginViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private val viewModel: LoginViewModel by viewModels()
    private  lateinit var toolbar : Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val drawer_layout = findViewById<DrawerLayout>(R.id.drawer_layout)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

    }

    override fun onBackPressed() {
        val drawer_layout = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val drawer_layout = findViewById<DrawerLayout>(R.id.drawer_layout)
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.action_add -> {

            }

            R.id.action_history -> {

            }

            R.id.action_up -> {

            }

            R.id.action_search_client -> {

            }

            R.id.action_search_article -> {

            }
            R.id.buckup_sql ->{
            }
            R.id.action_sicronizacion ->{

            }
            R.id.action_exit -> {



            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun login(){
    /*    binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val hash = ""
                val loginRequest = LoginRequest( email , password)
                Log.i("TAG" , loginRequest.toString())
                val vm = viewModel.login(loginRequest)
                Log.i("TAG" , vm.toString())

        }*/
    }

}