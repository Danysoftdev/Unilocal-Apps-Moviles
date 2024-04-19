package co.edu.eam.unilocal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.adapter.LugarAdapter
import co.edu.eam.unilocal.adapter.TopLugarAdapter
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.FragmentMisLugaresBinding
import co.edu.eam.unilocal.databinding.FragmentTopLugaresBinding
import co.edu.eam.unilocal.models.Lugar


class TopLugaresFragment : Fragment() {
    var listaLugares:ArrayList<Lugar> = ArrayList()
    lateinit var binding: FragmentTopLugaresBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopLugaresBinding.inflate(inflater,container,false)
        listaLugares = Lugares.ordenarPorCorazones()
        val adapter = TopLugarAdapter(listaLugares)

        binding.listaLugares.adapter = adapter
        binding.listaLugares.layoutManager = LinearLayoutManager(requireActivity(),
            LinearLayoutManager.VERTICAL,false)
        return binding.root
    }

}