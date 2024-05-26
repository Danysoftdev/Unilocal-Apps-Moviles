package co.edu.eam.unilocal.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.adapter.ComentarioLugarAdapter
import co.edu.eam.unilocal.databinding.ActivityComentariosLugarBinding
import co.edu.eam.unilocal.models.Comentario

class ComentariosLugarActivity : AppCompatActivity() {
    lateinit var binding: ActivityComentariosLugarBinding
    lateinit var listaComentarios:ArrayList<Comentario>
    var codigoLugar:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =ActivityComentariosLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listaComentarios = ArrayList()
        codigoLugar = intent.extras!!.getInt("codigo")

        //listaComentarios = Comentarios.listar(codigoLugar)

        if(listaComentarios.isEmpty()){
            binding.mensajeVacio.visibility = View.VISIBLE
        }else{
            binding.mensajeVacio.visibility = View.GONE
            val adapter= ComentarioLugarAdapter(listaComentarios)

            binding.listaComentarios.adapter = adapter
            binding.listaComentarios.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false)
        }



    }
}