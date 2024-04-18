package co.edu.eam.unilocal.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.FragmentNovedadesLugarBinding

class NovedadesLugarFragment : Fragment() {

    lateinit var binding: FragmentNovedadesLugarBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNovedadesLugarBinding.inflate(inflater, container, false)

        return binding.root
    }
}