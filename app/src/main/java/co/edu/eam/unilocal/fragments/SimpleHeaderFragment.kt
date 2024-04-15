package co.edu.eam.unilocal.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.edu.eam.unilocal.databinding.FragmentSimpleHeaderBinding

class SimpleHeaderFragment : Fragment() {

    lateinit var binding: FragmentSimpleHeaderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSimpleHeaderBinding.inflate(inflater, container, false)

        return binding.root
    }

}