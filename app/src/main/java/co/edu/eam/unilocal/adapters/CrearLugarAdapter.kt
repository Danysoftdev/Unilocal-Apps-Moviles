package co.edu.eam.unilocal.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.edu.eam.unilocal.fragments.crearlugar.FormularioCrearLugarFragment
import co.edu.eam.unilocal.fragments.crearlugar.HorariosCrearLugarFragment
import co.edu.eam.unilocal.fragments.crearlugar.MapaCrearLugarFragment

class CrearLugarAdapter(var fragment: FragmentActivity): FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> FormularioCrearLugarFragment.newInstance()
            1 -> HorariosCrearLugarFragment.newInstance()
            else -> MapaCrearLugarFragment.newInstance()
        }
    }
}