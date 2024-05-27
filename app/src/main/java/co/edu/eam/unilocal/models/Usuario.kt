package co.edu.eam.unilocal.models

class Usuario () {

    var id:Int =0
    var nombre:String = ""
    var nickname: String = ""
    var correo: String = ""
    var password: String = ""
    var tipo:String = ""
    var favoritos:ArrayList<Lugar> = ArrayList()

    constructor(var id:Int, var nombre: String, var nickname: String, var correo: String, var password: String, var tipo: String){

    }

    override fun toString(): String {
        return "Usuario(id=$id, nombre='$nombre', nickname='$nickname', correo='$correo', password='$password', tipo='$tipo')"
    }
}