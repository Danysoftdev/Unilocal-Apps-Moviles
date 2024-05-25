package co.edu.eam.unilocal.fragments.crearlugar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Horarios
import co.edu.eam.unilocal.databinding.FragmentHorariosDialogoBinding
import co.edu.eam.unilocal.models.DiaSemana
import co.edu.eam.unilocal.models.Horario
import com.google.android.material.chip.Chip

class HorariosDialogoFragment : DialogFragment() {

    lateinit var binding: FragmentHorariosDialogoBinding
    lateinit var listener: OnHorarioCreadoListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHorariosDialogoBinding.inflate(inflater, container, false)

        binding.agregarHorario.setOnClickListener { agregarHorario() }

        cargarDias()

        return binding.root
    }

    fun agregarHorario(){

        val diasSemana = binding.listaDias.checkedChipIds
        val horaInicio = binding.horaInicio.text.toString()
        val horaCierre = binding.horaCierre.text.toString()

        if( diasSemana.isNotEmpty() && horaInicio.isNotEmpty() && horaCierre.isNotEmpty() ){

            val lista:ArrayList<DiaSemana> = diasSemana.map { index -> DiaSemana.values()[index] }.toCollection(ArrayList())

            val horario = Horarios.agregarHorario( Horario( lista, horaInicio.toInt(), horaCierre.toInt() ) )
            listener.elegirHorario(horario)
            dismiss()
        }

    }

    fun cargarDias(){

        DiaSemana.values().forEach {
            val chip = Chip(requireContext())
            chip.id = it.ordinal
            chip.text = it.name
            chip.isCheckable = true
            binding.listaDias.addView(chip)
        }

    }

    interface OnHorarioCreadoListener{
        fun elegirHorario(horario: Horario)
    }

}