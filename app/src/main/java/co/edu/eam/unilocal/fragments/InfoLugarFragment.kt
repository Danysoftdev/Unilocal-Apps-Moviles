package co.edu.eam.unilocal.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.FragmentInfoLugarBinding
import co.edu.eam.unilocal.models.Lugar

class InfoLugarFragment : Fragment() {

    lateinit var binding: FragmentInfoLugarBinding
    private var lugar: Lugar? = null
    private var codigoLugar: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null){
            codigoLugar = requireArguments().getInt("codigoLugar")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentInfoLugarBinding.inflate(inflater, container, false)

        //Ojo tiene que llegar es el ID
        lugar = Lugares.obtener(2)

        if (lugar != null){

            binding.ubicacionLugar.text = lugar!!.direccion

            var horarios = ""
            for (horario in lugar!!.horarios){
                for (dia in horario.diasSemana){
                    horarios += "$dia: ${horario.horaInicio}:00 - ${horario.horaCierre}:00\n"
                }
            }
            binding.horariosLugar.text = horarios

            var telefonos = ""

            if (lugar!!.telefonos.isNotEmpty()){
                for (tel in lugar!!.telefonos){
                    telefonos += "$tel, "
                }
                telefonos = telefonos.substring(0, telefonos.length-2)
            }
            binding.telefonoLugar.text = telefonos

            binding.descripcionLugar.text = lugar!!.descripcion

        }

        return binding.root
    }

    companion object{
        fun newInstance(codigoLugar: Int): InfoLugarFragment{
            val args = Bundle()
            args.putInt("codigoLugar", codigoLugar)
            val fragmento = InfoLugarFragment()
            fragmento.arguments = args
            return fragmento
        }
    }

}