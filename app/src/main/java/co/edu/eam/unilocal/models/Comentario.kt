package co.edu.eam.unilocal.models

import java.time.LocalDateTime

//La fecha si va, es por pruebas mientras tanto

class Comentario (var id: Int, var texto: String, var idUsuario: Int, var idLugar: Int, var calificaicon: Int, /*var fecha: LocalDateTime*/){

    override fun toString(): String {
        return "Comentario(id=$id, texto='$texto', idUsuario=$idUsuario, idLugar=$idLugar, calificaicon=$calificaicon)"
    }
}