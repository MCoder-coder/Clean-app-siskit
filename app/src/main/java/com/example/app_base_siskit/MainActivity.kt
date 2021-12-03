package com.example.app_base_siskit


import android.annotation.SuppressLint
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

    @SuppressLint("SetTextI18n", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val drawer_layout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val nav_view = findViewById<NavigationView>(R.id.nav_view)
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val pInfo: PackageInfo = this.packageManager.getPackageInfo(this.packageName, 0)
        val headerView = navigationView.getHeaderView(0)
        val version = pInfo.versionName
        val version_name = headerView.findViewById<TextView>(R.id.version_name)
        val name = headerView.findViewById<TextView>(R.id.name)
        val host = headerView.findViewById<TextView>(R.id.name_host)

        setSupportActionBar(toolbar)
        nav_view.setNavigationItemSelectedListener(this)

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


        name.text =  "¡Hola ${sharedPrefs.getNombre()}!"
        host.text = sharedPrefs.getEmail()
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
        //sharedPrefs.clear()
        goToLoginActivity()
    }

}