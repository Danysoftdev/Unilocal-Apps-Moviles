package co.edu.eam.unilocal.models

import co.edu.eam.unilocal.models.Horario
import java.util.Calendar
import java.util.Date


class Lugar(var id: Int,
            var nombre: String,
            var descripcion: String,
            var idCreador: Int,
            var estado: Boolean,
            var idCategoria: Int,
            var direccion: String,
            var latitud: Float,
            var longitud: Float,
            var idCiudad: Int
) {

    var imagenes: ArrayList<String> = ArrayList()
    var telefonos: List<String> = ArrayList()
    var horarios: ArrayList<Horario> = ArrayList()
    var fecha: Date = Date()
    override fun toString(): String {
        return "Lugar(id=$id, nombre='$nombre', descripcion='$descripcion', idCreador=$idCreador, estado=$estado, idCategoria=$idCategoria, direccion='$direccion', latitud=$latitud, longitud=$longitud, idCiudad=$idCiudad, imagenes=$imagenes, telefonos=$telefonos, horarios=$horarios, fecha=$fecha)"
    }

    fun verificarEstadoHorario(): String {

        val fecha = Calendar.getInstance()
        val dia = fecha.get(Calendar.DAY_OF_WEEK)
        val hora = fecha.get(Calendar.HOUR_OF_DAY)

        var mensaje = "Cerrado"

        for (horario in horarios){
            if (horario.diasSemana.contains(DiaSemana.entries[dia-1]) && hora < horario.horaCierre &&
                hora >= horario.horaInicio){

                mensaje = "Abierto"
                break

            }
        }

        return mensaje

    }

    fun obtenerHoraCierre(): Int{

        val fecha = Calendar.getInstance()
        val dia = fecha.get(Calendar.DAY_OF_WEEK)
        val hora = fecha.get(Calendar.HOUR_OF_DAY)

        var horaCierre = 0

        for (horario in horarios){
            if (horario.diasSemana.contains(DiaSemana.entries[dia-1]) && hora < horario.horaCierre &&
                hora >= horario.horaInicio){

                horaCierre = horario.horaCierre
                break

            }
        }

        return horaCierre

    }

    fun obtenerHoraApertura(): Int{

        val fecha = Calendar.getInstance()
        val dia = fecha.get(Calendar.DAY_OF_WEEK)
        val hora = fecha.get(Calendar.HOUR_OF_DAY)

        var horaApertura = 0

        for (horario in horarios){
            if (horario.diasSemana.contains(DiaSemana.entries[dia-1]) && hora >= horario.horaCierre){

                horaApertura = horario.horaInicio
                break

            }
        }

        return horaApertura

    }


}