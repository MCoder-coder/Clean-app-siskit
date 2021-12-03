package com.example.app_base_siskit


import android.content.Intent
import android.content.pm.PackageInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.app_base_siskit.feature_login.presentation.login.LoginViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout
import com.example.app_base_siskit.feature_login.presentation.login.LoginActivity
import com.example.app_base_siskit.utils.SharedPrefs
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private  lateinit var toolbar : Toolbar
    @Inject
    lateinit var sharedPrefs: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val drawer_layout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val nav_view = findViewById<NavigationView>(R.id.nav_view)
        setSupportActionBar(toolbar)
        nav_view.setNavigationItemSelectedListener(this)
        //titulo appbar package
        title = ""
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val headerView = navigationView.getHeaderView(0)
        //obtengo el numero de version codigo
        val pInfo: PackageInfo =
            this.getPackageManager().getPackageInfo(this.getPackageName(), 0)
        //version name
        val version = pInfo.versionName
        val version_name = headerView.findViewById<TextView>(R.id.version_name)
        val name = headerView.findViewById<TextView>(R.id.name)
        val host = headerView.findViewById<TextView>(R.id.name_host)
        name.text =  "¡Hola ${sharedPrefs.getNombre()}!"
        host.text = sharedPrefs.getEmail()
        //imprimo el name version en el drawble
        version_name.text = "v${version}"
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
                signOut()
                Log.i("Tag", "click")
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun goToLoginActivity(){
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        finish()
    }

    private fun signOut(){
        sharedPrefs.clear()
        goToLoginActivity()
    }

}