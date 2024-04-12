package co.edu.eam.unilocal.bd

import co.edu.eam.unilocal.models.DiaSemana
import co.edu.eam.unilocal.models.Horario

object Horarios {

    val horarios: ArrayList<Horario> = ArrayList()

    fun obtenerTodos(): ArrayList<DiaSemana> {

        val todosDias: ArrayList<DiaSemana> = ArrayList()
        todosDias.addAll(DiaSemana.values())
        return todosDias
    }

    fun obtenerFinSemana(): ArrayList<DiaSemana> {

        val finSemana: ArrayList<DiaSemana> = ArrayList()
        finSemana.add(DiaSemana.VIERNES)
        finSemana.add(DiaSemana.SABADO)
        return finSemana
    }

    fun obtenerEntreSemana(): ArrayList<DiaSemana> {

        val entreSemana: ArrayList<DiaSemana> = ArrayList()
        entreSemana.add(DiaSemana.LUNES)
        entreSemana.add(DiaSemana.MARTES)
        entreSemana.add(DiaSemana.MIERCOLES)
        entreSemana.add(DiaSemana.JUEVES)
        entreSemana.add(DiaSemana.VIERNES)
        return entreSemana
    }
}