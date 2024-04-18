package co.edu.eam.unilocal.bd

import co.edu.eam.unilocal.models.Comentario

object Comentarios {

    private val comentarios: ArrayList<Comentario> = ArrayList()

    init {
        comentarios.add(Comentario(1, "Excelente servicio y buen ambiente", 1, 2, 5))
        comentarios.add(Comentario(2, "Muy demorado, no volvería", 4, 1, 2))
        comentarios.add(Comentario(3, "Buena comida mexicana, precios razonables", 3, 3, 4))
        comentarios.add(Comentario(4, "El lugar es bonito, pero muy lentos", 2, 2, 3))
        comentarios.add(Comentario(5, "No volvería, los precios son muy altos", 5, 2, 2))
        comentarios.add(Comentario(6, "Un hotel bien ubicado y con desayuno incluido. Recomendado", 1, 1, 4))
    }

    fun listar(idLugar: Int): ArrayList<Comentario> {
        return comentarios.filter { c -> c.idLugar == idLugar }.toCollection(ArrayList())
    }

    fun crear(comentario: Comentario){
        comentarios.add(comentario)
    }
    fun calcularPromedioCalificacion(idLugar: Int): Double {

        val comentariosLugar = comentarios.filter { c -> c.idLugar == idLugar }


        if (comentariosLugar.isEmpty()) {
            return 0.0
        }

        val totalCalificaciones = comentariosLugar.sumBy { it.calificaicon }

        // Calcular el promedio
        val promedio = totalCalificaciones.toDouble() / comentariosLugar.size

        return promedio
    }

}