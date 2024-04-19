package co.edu.eam.unilocal.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.edu.eam.unilocal.fragments.ComentariosLugarFragment
import co.edu.eam.unilocal.fragments.InfoLugarFragment
import co.edu.eam.unilocal.fragments.NovedadesLugarFragment

class ViewPagerAdapter(var fragment: FragmentActivity, var codigoLugar: Int, var codigoUsuario: Int): FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {

        return when(position){
            0 -> InfoLugarFragment.newInstance(codigoLugar)
            1 -> ComentariosLugarFragment.newInstance(codigoLugar, codigoUsuario)
            else -> NovedadesLugarFragment.newInstance(codigoLugar)
        }
    }
}