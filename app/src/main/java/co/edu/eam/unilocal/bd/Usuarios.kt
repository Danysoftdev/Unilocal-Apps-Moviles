package co.edu.eam.unilocal.bd

import co.edu.eam.unilocal.models.Usuario

object Usuarios {
/*
    private val usuarios: ArrayList<Usuario> = ArrayList()

    init {
        usuarios.add(Usuario( "Daniela", "dany", "daniela@email.com", "123", "usuario"))
        usuarios.add(Usuario( "Angie", "angie", "angie@email.com", "456", "moderador"))
        usuarios.add(Usuario( "Alejandro", "alejo", "alejandro@email.com", "789", "usuario"))
        usuarios.add(Usuario( "Marcos", "marcos", "marcos@email.com", "147", "moderador"))
        usuarios.add(Usuario( "Maria", "maria", "maria@email.com", "258", "usuario"))
    }

    fun listar(): ArrayList<Usuario> {
        return usuarios
    }

    fun agregar(usuario: Usuario): Boolean{

        val user = usuarios.firstOrNull { u -> u.correo == usuario.correo }

        if( user == null ){
            usuario.id = usuarios.size + 1
            usuarios.add(usuario)
            return true
        }else{
            return false
        }
    }

    fun login(correo: String, password: String): Usuario {
        val usuario = usuarios.first { u -> u.correo == correo && u.password == password }
        return usuario
    }

    fun buscar(id: Int): Usuario {
        return usuarios.first { u -> u.id == id }
    }
    fun eliminarFavorito(idUsuario: Int,idLugar: Int ){
        val usuario = usuarios.first { u -> u.id == idUsuario}

        //if (usuario.favoritos.isNotEmpty()){
            usuario.favoritos.removeIf { it.id == idLugar }

       // }


    }

    fun verificarCorreo(correo: String): Boolean {

        val user = usuarios.firstOrNull { u -> u.correo == correo }

        if (user == null) {
            return false
        } else {
            return true
        }
    }

    fun getNameById(id: Int): String{
        var nombre = ""
        for (usuario: Usuario in usuarios){
            if (usuario.id == id){
                nombre = usuario.nombre
            }
        }
        return nombre
    }

    fun getById(id: Int): Usuario? {

        return usuarios.firstOrNull { u -> u.id == id }

    }

    fun getType(id: Int): String {
        var usuario: Usuario? =  usuarios.firstOrNull { u -> u.id == id }
        if (usuario != null) {
            return usuario.tipo
        }

        return ""
    }
*/

}