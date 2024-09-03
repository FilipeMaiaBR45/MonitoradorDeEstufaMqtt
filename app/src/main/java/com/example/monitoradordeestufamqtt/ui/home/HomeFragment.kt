package com.example.monitoradordeestufamqtt.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.monitoradordeestufamqtt.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textView3
        val progressBar: ProgressBar = binding.circularProgressIndicator2
        homeViewModel.messages.observe(viewLifecycleOwner) {
            textView.text = it
            //progressBar.progress = it.toInt()
        }

        homeViewModel.connect()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        //homeViewModel.disconnect()
        _binding = null

    }
}