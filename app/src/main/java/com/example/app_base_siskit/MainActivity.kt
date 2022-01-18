package com.example.app_base_siskit


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.app_base_siskit.feature_login.presentation.login.LoginActivity
import com.example.app_base_siskit.feature_geo_point.presentation.ForegroundServiceLocation
import com.example.app_base_siskit.utils.PermissionHelper
import com.example.app_base_siskit.utils.SharedPrefs
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private  lateinit var toolbar : Toolbar
    @Inject
    lateinit var sharedPrefs: SharedPrefs
    lateinit var drawerLayout:DrawerLayout

    lateinit var mainActivityContext : AppCompatActivity
    lateinit var permissionHelper : PermissionHelper

    private lateinit var appBarConfiguration: AppBarConfiguration


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


        title = ""

        /*
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        //drawer_layout.addDrawerListener(toggle)
        toggle.syncState() */


        name.text =  "¡Hola ${sharedPrefs.getNombre()}!"
        host.text = sharedPrefs.getEmail()
        version_name.text = "v${version}"


        setUpNavigation()

        // solicita permisos
        mainActivityContext = this as AppCompatActivity // no borrar!
        permissionHelper = PermissionHelper(mainActivityContext)
        initPermissionFlow()
        initServiceLocation()

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }


    @SuppressLint("CutPasteId")
    private fun setUpNavigation(){
        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController


        navigationView.setNavigationItemSelectedListener(this)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)

    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        when(id){
            R.id.action_map -> navController.navigate(R.id.mapFragment, null, )
            R.id.action_exit -> goToLoginActivity()

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }




    private fun goToLoginActivity(){
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        finish()
    }

    private fun signOut(){
        //sharedPrefs.clear()
        goToLoginActivity()
    }


    /**
     * Inicia el Servicio de Ubicacion en Primer Plano
     * */
    private fun initServiceLocation(){
        Log.d(TAG,"initService()")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG,"startForegroundService()")
            startForegroundService(Intent(this, ForegroundServiceLocation::class.java))
        } else {
            Log.d(TAG,"startService()")
            startService(Intent(this, ForegroundServiceLocation::class.java))
        }
    }

    /**
     * comienza el flujo de pedido de permisos
     * */
    private fun initPermissionFlow(){
        Log.d("TAG","MainActivity@initPermissionFlow()")

        if(permissionHelper.permissionsAlertDialog != null && permissionHelper.permissionsAlertDialog!!.isShowing()){

        }else{
            permissionHelper.showRequiredPermissionsDialog(
                {
                    permissionHelper.requestAllRequiredPermssions()
                },
                { finish() }
            )
        }
    }

    override fun onResume() {
        Log.d("TAG","onResume()")
        super.onResume()
        // configureList()
        Log.d("TAG","execute -> initPermissionFlow()")

        initPermissionFlow()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("TAG","MainActivity@onRequestPermissionsResult()")
        permissionHelper.onRequestPermissionsResultHandler(requestCode, permissions, grantResults)

    }

}