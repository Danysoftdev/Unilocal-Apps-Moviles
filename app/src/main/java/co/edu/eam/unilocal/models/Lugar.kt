package co.edu.eam.unilocal.models

import android.content.ContentValues
import co.edu.eam.unilocal.models.Horario
import co.edu.eam.unilocal.sqllite.LugarContrato
import java.util.Calendar
import java.util.Date


class Lugar() {

    var key: String=""
    var nombre: String = ""
    var descripcion: String = ""
    var idCreador: String = ""
    var estado: Estado = Estado.PENDIENTE
    var idCategoria: Int = 0
    var direccion: String = ""
    var posicion: Posicion = Posicion()
    var idCiudad: Int = 0
    var novedades:String = ""

    constructor( nombre: String, descripcion: String, idCreador: String, estado: Estado, idCategoria: Int, direccion: String, posicion: Posicion, idCiudad: Int, novedades: String):this(){

        this.nombre = nombre
        this.descripcion = descripcion
        this.idCreador = idCreador
        this.estado = estado
        this.idCategoria = idCategoria
        this.direccion = direccion
        this.posicion = posicion
        this.idCiudad = idCiudad
        this.novedades = novedades
    }

    constructor(  descripcion: String, idCreador: String, estado: Estado, idCategoria: Int, direccion: String, idCiudad: Int, novedades: String):this(){

        this.nombre = nombre
        this.descripcion = descripcion
        this.idCreador = idCreador
        this.estado = estado
        this.idCategoria = idCategoria
        this.direccion = direccion
        this.idCiudad = idCiudad
        this.novedades = novedades
    }

    constructor(nombre: String, descripcion: String, idCreador: String, estado: Estado, idCategoria: Int, direccion: String, idCiudad: Int, novedades: String) : this() {
        this.nombre = nombre
        this.descripcion = descripcion
        this.idCreador = idCreador
        this.estado = estado
        this.idCategoria = idCategoria
        this.direccion = direccion
        this.idCiudad = idCiudad
        this.novedades = novedades

    }

    var imagenes: ArrayList<String> = ArrayList()
    var telefonos: List<String> = ArrayList()
    var horarios: ArrayList<Horario> = ArrayList()
    var comentarios:ArrayList<Comentario> = ArrayList()
    var fecha: Date = Date()
    var corazones: Int = 0


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
        var pos: Int = 0

        for (horario in horarios){
            pos = horario.diasSemana.indexOf(DiaSemana.entries[dia-3])

            mensaje = if (pos != -1){
                "Abre el ${horario.diasSemana[pos+1].toString().lowercase()} a las ${horario.horaInicio}:00"
            }else{
                "Abre el ${horario.diasSemana[0].toString().lowercase()} a las ${horario.horaInicio}:00"
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

    fun toContentValues(): ContentValues {

        val values = ContentValues()
        values.put(LugarContrato.NOMBRE, nombre )
        values.put(LugarContrato.DESCRIPCION, descripcion )
        values.put(LugarContrato.LAT, posicion.lat )
        values.put(LugarContrato.LNG, posicion.lng )
        values.put(LugarContrato.DIRECCION, direccion )
        values.put(LugarContrato.CATEGORIA, idCategoria )
        values.put(LugarContrato.ID_CREADOR, idCreador )
        values.put(LugarContrato.KEY_FIREBASE, key )

        return values
    }



    override fun toString(): String {
        return "Lugar( nombre='$nombre', descripcion='$descripcion', idCreador=$idCreador, estado=$estado, idCategoria=$idCategoria, direccion='$direccion', posicion=$posicion, idCiudad=$idCiudad, imagenes=$imagenes, telefonos=$telefonos, horarios=$horarios, fecha=$fecha,corazones=$corazones,novedades=$novedades)"
    }


}