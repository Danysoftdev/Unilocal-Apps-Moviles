package co.edu.eam.unilocal.bd

import co.edu.eam.unilocal.models.Estado
import co.edu.eam.unilocal.models.Horario
import co.edu.eam.unilocal.models.Lugar

object Lugares {

    private val lugares: ArrayList<Lugar> = ArrayList()

    init {
        val horario1 = Horario(1, Horarios.obtenerTodos(), 12, 20)
        val horario2 = Horario(1, Horarios.obtenerEntreSemana(), 9, 20)
        val horario3 = Horario(1, Horarios.obtenerFinSemana(), 14, 23)

        val lugar1 = Lugar(1, "Café ABC", "Excelente café para compartir", 1, Estado.PENDIENTE, 2, "calle 123",73.3434f, -40.4345f, 1,"asd")
        lugar1.horarios.add(horario2)

        val lugar2 = Lugar(2, "Bar City Pub", "Bar en la ciudad de Armenia", 2, Estado.APROBADO, 5, "calle 456",73.3534f, -41.4345f, 1,"asd")
        lugar2.horarios.add(horario1)

        val lugar3 = Lugar(3, "Restaurante Mi Cuate", "Comida mexicana para todos", 3, Estado.PENDIENTE, 3, "calle 789",73.3434f, -40.4345f, 5,"asd")
        lugar3.horarios.add(horario1)

        val lugar4 = Lugar(4, "BBC Norte Pub", "Cervezas BBC muy buenas", 1, Estado.APROBADO, 5, "calle 147",73.3434f, -40.4345f, 3,"asd")
        lugar4.horarios.add(horario3)

        val lugar5 = Lugar(5, "Hotel Barato", "Hotel para ahorrar mucho dinero", 1, Estado.APROBADO, 1, "calle 258",73.3434f, -40.4345f, 4,"asd")
        lugar5.horarios.add(horario1)

        val lugar6 = Lugar(1, "Hostal Hippie", "Alojamiento para todos y todas", 2, Estado.RECHAZADO, 1, "calle 369",73.3434f, -40.4345f, 2,"asd")
        lugar6.horarios.add(horario2)

        lugares.add(lugar1)
        lugares.add(lugar2)
        lugares.add(lugar3)
        lugares.add(lugar4)
        lugares.add(lugar5)
        lugares.add(lugar6)

    }

    fun listar(): ArrayList<Lugar>{
        return lugares
    }

    fun listarPendientes(): ArrayList<Lugar> {
        return lugares.filter { l -> l.estado.name == "PENDIENTE"}.toCollection(ArrayList())
    }

    fun listarAprobados(): ArrayList<Lugar> {
        return lugares.filter { l -> l.estado.name == "APROBADO"}.toCollection(ArrayList())
    }

    fun listarRechazados(): ArrayList<Lugar> {
        return lugares.filter { l -> l.estado.name == "RECHAZADO"}.toCollection(ArrayList())
    }

    fun obtener(id: Int): Lugar?{
        return lugares.firstOrNull { l -> l.id == id }
    }

    fun buscarNombre(nombre: String): ArrayList<Lugar> {
        return lugares.filter { l -> l.nombre.lowercase().contains(nombre.lowercase()) && l.estado.name == "APROBADO"}.toCollection(ArrayList())
    }

    fun crear(lugar: Lugar){
        lugares.add(lugar)
    }

    fun buscarCiudad(idCiudad: Int): ArrayList<Lugar> {
        return lugares.filter { l -> l.idCiudad == idCiudad && l.estado.name == "APROBADO" }.toCollection(ArrayList())
    }

    fun buscarCategoria(idCategoria: Int): ArrayList<Lugar> {
        return lugares.filter { l -> l.idCategoria == idCategoria && l.estado.name == "APROBADO" }.toCollection(ArrayList())
    }
    fun buscarXUsuario(idCreador:Int): ArrayList<Lugar>{
        return lugares.filter { l -> l.idCreador ==idCreador }.toCollection(ArrayList())
    }
    fun ordenarPorCorazones(): ArrayList<Lugar> {
        return lugares.sortedByDescending { it.corazones }.toCollection(ArrayList())
    }
    fun eliminar(idLugar: Int) {
        lugares.removeIf { lugar -> lugar.id == idLugar }
    }


}