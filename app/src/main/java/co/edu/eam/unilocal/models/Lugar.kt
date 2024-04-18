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

    fun obtenerHoraCierre(): String{

        val fecha = Calendar.getInstance()
        val dia = fecha.get(Calendar.DAY_OF_WEEK)

        var mensaje = ""

        for (horario in horarios){
            if (horario.diasSemana.contains(DiaSemana.entries[dia-1])){

                mensaje = "Cierra a las ${horario.horaCierre}:00"
                break

            }
        }

        return mensaje

    }

    fun obtenerHoraApertura(): String{

        val fecha = Calendar.getInstance()
        val dia = fecha.get(Calendar.DAY_OF_WEEK)

        var mensaje = ""
        var pos = 0

        for (horario in horarios){
            pos = horario.diasSemana.indexOf(DiaSemana.entries[dia-1])

            mensaje = if (pos != -1){
                "${horario.diasSemana[pos+1].toString().lowercase()} a las ${horario.horaInicio}:00"
            }else{
                "${horario.diasSemana[0].toString().lowercase()} a las ${horario.horaInicio}:00"
            }
            break
        }

        return mensaje

    }

    fun obtenerCalificacionPromedio(comentarios: ArrayList<Comentario>): Int{

        var promedio = 0

        if (comentarios.size > 0){
            val suma = comentarios.stream().map { c -> c.calificaicon }.reduce{suma, valor -> suma + valor}.get()
            promedio = suma/comentarios.size
        }

        return promedio

    }


}