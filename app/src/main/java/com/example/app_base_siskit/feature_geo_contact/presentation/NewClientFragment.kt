package com.example.app_base_siskit.feature_geo_contact.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.app_base_siskit.R
import com.example.app_base_siskit.common.GeoDatabase
import com.example.app_base_siskit.databinding.FragmentMapBinding
import com.example.app_base_siskit.databinding.FragmentNewClientBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewClientFragment : Fragment() {


    private val viewModel : ContactViewModel by activityViewModels()
    private var _binding: FragmentNewClientBinding? = null
    private val binding get() = _binding!!
    private val args : NewClientFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


       // viewModel.fetchAllUser()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNewClientBinding.inflate(inflater , container , false)
        val view = binding.root
       // val geoContactLocation = args.cordenadas.myLocation
     //   binding.cordenadas.text = "LAT: ${geoContactLocation.latitude} ; LON: ${geoContactLocation.longitude} "


        switchsEvents()
      //  viewModel.fetchAllUser()

        return  view
    }


    /*
    * Defino los eventos de los switchs que muestran mas campos
    * */
    private fun switchsEvents(){

        // SWITCH VENTA POTENCIAL
        binding.checkVentaPotencial.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // Muestro el Layout
                binding.layoutVentaPotencial.visibility = View.VISIBLE
            } else {
                // Oculto el Layout
                binding.layoutVentaPotencial.visibility = View.GONE
                // Limpio los campos
                binding.ventaDescripcion.setText("")
                binding.ventaMontoAproximado.setText("")
                binding.ventaVendedorId.setSelection(0) //(adapter.getPosition(myItem))
            }
        }


        // SWITCH RECLAMO
        binding.checkReclamo.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // Muestro el Layout
                binding.layoutReclamo.visibility = View.VISIBLE
            } else {
                // Oculto el Layout
                binding.layoutReclamo.visibility = View.GONE
                // Limpio los campos
                binding.reclamoDescripcion.setText("")
                binding.reclamoUsuarioId.setSelection(0) //(adapter.getPosition(myItem))
            }
        }

    }



}