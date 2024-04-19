package co.edu.eam.unilocal.models

class Usuario (var id:Int, var nombre: String, var nickname: String, var correo: String, var password: String) {

    var favoritos: ArrayList<Lugar> = ArrayList()

    override fun toString(): String {
        return "Usuario(id=$id, nombre='$nombre', nickname='$nickname', correo='$correo', password='$password')"
    }
}