package co.edu.eam.unilocal.models

import android.content.ContentValues
import co.edu.eam.unilocal.sqllite.UsuarioContrato

class Usuario () {
    constructor( nombre:String, nickname:String, correo:String, tipo:String):this(){
        this.correo=correo
        this.nombre=nombre
        this.nickname=nickname
        this.tipo=tipo
    }

    var key:String = ""
    var uid:String = ""
    var nombre: String= ""
    var correo: String= ""
    var nickname: String= ""
    var tipo: String = ""
    var favoritos:ArrayList<Lugar> = ArrayList()

    fun toContentValues():ContentValues{

        val values = ContentValues()
        values.put(UsuarioContrato.NOMBRE, nombre )
        values.put(UsuarioContrato.NICKNAME, nickname )
        //values.put(UsuarioContrato.CORREO, correo )
        //values.put(UsuarioContrato.PASSWORD, password )

        return values
    }

    override fun toString(): String {
        return "Usuario( nombre='$nombre', nickname='$nickname', correo='$correo',  tipo='$tipo')"
    }
}