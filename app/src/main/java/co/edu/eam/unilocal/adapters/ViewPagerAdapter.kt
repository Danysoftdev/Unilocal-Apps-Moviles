package co.edu.eam.unilocal.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

import co.edu.eam.unilocal.fragments.ComentariosLugarFragment
import co.edu.eam.unilocal.fragments.InfoLugarFragment

import co.edu.eam.unilocal.fragments.NovedadesLugarFragment

class ViewPagerAdapter(activity: FragmentActivity, private val codigoLugar: String) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InfoLugarFragment.newInstance(codigoLugar)
            1 -> ComentariosLugarFragment.newInstance(codigoLugar)
            2 -> NovedadesLugarFragment.newInstance(codigoLugar)
            else -> throw IllegalStateException("Invalid position: $position")
        }
    }
}

