package co.edu.eam.unilocal.models

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale

//La fecha si va, es por pruebas mientras tanto

class Comentario (){

    constructor( texto: String,  idUsuario: String,    calificaicon: Int, /*var fecha: LocalDateTime*/): this(){

        this.texto = texto
        this.idUsuario = idUsuario
        this.calificaicon = calificaicon


    }




    var key:String = ""
    var texto: String = ""
    var idUsuario: String= ""
    var calificaicon: Int = 0
     var fecha: Date = Date()
    init {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fecha = dateFormat.format(currentDate)
    }

    override fun toString(): String {
        return "Comentario(texto='$texto', idUsuario=$idUsuario,  calificaicon=$calificaicon)"
    }
}