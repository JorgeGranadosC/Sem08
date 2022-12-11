package com.example.sem08.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sem08.R
import com.example.sem08.adapter.LugarAdapter
import com.example.sem08.databinding.FragmentHomeBinding
import com.example.sem08.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

    binding.btAddLugar.setOnClickListener {
        findNavController().navigate(R.id.action_nav_home_to_addLugarFragment)
    }

        //cargar lista de lugares
        val lugarAdapter = LugarAdapter()
        val reciclador = binding.reciclador
        reciclador.adapter = lugarAdapter
        reciclador.layoutManager = LinearLayoutManager(requireContext())

        homeViewModel.obtenerLugares.observe(viewLifecycleOwner){
                lugares -> lugarAdapter.setLugares(lugares)
           // lugarAdapter.setLugares(it)
        }

        return binding.root
    }

       override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}