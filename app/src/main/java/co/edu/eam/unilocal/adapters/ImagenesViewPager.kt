package co.edu.eam.unilocal.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.edu.eam.unilocal.fragments.ComentariosLugarFragment
import co.edu.eam.unilocal.fragments.ImagenFragment
import co.edu.eam.unilocal.fragments.InfoLugarFragment
import co.edu.eam.unilocal.fragments.NovedadesLugarFragment

class ImagenesViewPager (var fragment: FragmentActivity, private var imagenes: ArrayList<String>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = imagenes.size

    override fun createFragment(position: Int): Fragment {

        when (position){
            position -> return ImagenFragment.newInstance(imagenes[position])
        }

        return Fragment()
    }
}