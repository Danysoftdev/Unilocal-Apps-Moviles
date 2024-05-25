package co.edu.eam.unilocal.models

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

//La fecha si va, es por pruebas mientras tanto

class Comentario (var id: Int, var texto: String, var idUsuario: Int, var idLugar: Int, var calificaicon: Int, /*var fecha: LocalDateTime*/){

    val fecha: String

    init {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        fecha = dateFormat.format(currentDate)
    }

    override fun toString(): String {
        return "Comentario(id=$id, texto='$texto', idUsuario=$idUsuario, idLugar=$idLugar, calificaicon=$calificaicon)"
    }
}