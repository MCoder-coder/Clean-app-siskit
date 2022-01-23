package com.example.app_base_siskit.feature_geo_contact.presentation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.app_base_siskit.MainActivity
import com.example.app_base_siskit.R
import com.example.app_base_siskit.common.GeoDatabase
import com.example.app_base_siskit.feature_geo_contact.data.local.entity.GeoContactEntity
import com.example.app_base_siskit.feature_geo_contact.domain.entity.UserEntity
import com.example.app_base_siskit.utils.PermissionHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GeoConcatFragment : Fragment() {


    private val viewModel : ContactViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //>viewModel.fetchAllUser()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val actionBar: ActionBar? = (activity as AppCompatActivity).supportActionBar
        actionBar?.elevation = 0f
        actionBar?.title = "Geo Contacto"

        viewModel.fetchAllUser()
        //drawer_layout.addDrawerListener(toggle)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_geocontact, container, false)


    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_add){
            Navigation.findNavController(context as Activity , R.id.nav_host_fragment ).navigate(R.id.mapFragment)
        }

        return super.onOptionsItemSelected(item)
    }
}