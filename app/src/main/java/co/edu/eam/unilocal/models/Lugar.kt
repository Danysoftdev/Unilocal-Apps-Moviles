package co.edu.eam.unilocal.models

import co.edu.eam.unilocal.models.Horario
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
            var idCiudad: Int,
            var novedades:String,

) {

    var imagenes: ArrayList<String> = ArrayList()
    var telefonos: List<String> = ArrayList()
    var horarios: ArrayList<Horario> = ArrayList()
    var comentarios:ArrayList<Comentario> = ArrayList()
    var fecha: Date = Date()
    var corazones: Int = 0

    override fun toString(): String {
        return "Lugar(id=$id, nombre='$nombre', descripcion='$descripcion', idCreador=$idCreador, estado=$estado, idCategoria=$idCategoria, direccion='$direccion', latitud=$latitud, longitud=$longitud, idCiudad=$idCiudad, imagenes=$imagenes, telefonos=$telefonos, horarios=$horarios, fecha=$fecha,corazones=$corazones,novedades=$novedades)"
    }


}