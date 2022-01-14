package com.example.app_base_siskit.feature_geo_contact.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.app_base_siskit.R
import com.example.app_base_siskit.databinding.FragmentMapBinding
import com.example.app_base_siskit.databinding.FragmentNewClientBinding


class NewClientFragment : Fragment() {

    private var _binding: FragmentNewClientBinding? = null
    private val binding get() = _binding!!
    private val args : NewClientFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNewClientBinding.inflate(inflater , container , false)
        val view = binding.root
        val geoContactLocation = args.cordenadas.myLocation
        binding.cordenadas.text = "LAT: ${geoContactLocation.latitude} ; LON: ${geoContactLocation.longitude} "

        return  view
    }


}