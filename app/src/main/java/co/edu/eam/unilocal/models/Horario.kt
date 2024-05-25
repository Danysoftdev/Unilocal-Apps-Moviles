package co.edu.eam.unilocal.models

import co.edu.eam.unilocal.models.DiaSemana

class Horario () {

    var id: Int = 0
    var diasSemana: ArrayList<DiaSemana> = ArrayList()
    var horaInicio: Int = 0
    var horaCierre: Int = 0

    constructor(id:Int, diaSemana:ArrayList<DiaSemana>, horaInicio:Int, horaCierre:Int):this(diaSemana, horaInicio, horaCierre){
        this.id = id
    }

    constructor(diaSemana:ArrayList<DiaSemana>, horaInicio:Int, horaCierre:Int  ):this(){
        this.diasSemana = diaSemana
        this.horaCierre = horaCierre
        this.horaInicio = horaInicio
    }

    override fun toString(): String {
        return "Horario(id=$id, diasSemana=$diasSemana, horaInicio=$horaInicio, horaCierre=$horaCierre)"
    }


}