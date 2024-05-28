package co.edu.eam.unilocal.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.adapter.ComentarioLugarAdapter
import co.edu.eam.unilocal.adapters.ComentarioAdapter
import co.edu.eam.unilocal.databinding.ActivityComentariosLugarBinding
import co.edu.eam.unilocal.models.Comentario

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ComentariosLugarActivity : AppCompatActivity() {
    lateinit var binding: ActivityComentariosLugarBinding
    lateinit var listaComentarios: ArrayList<Comentario>
    var codigoLugar: String = ""
    private lateinit var adapter: ComentarioLugarAdapter

    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityComentariosLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listaComentarios = ArrayList()
        codigoLugar = intent.extras!!.getString("codigo", "")
        adapter = ComentarioLugarAdapter(listaComentarios)

        binding.listaComentarios.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        binding.listaComentarios.adapter = adapter

        cargarComentarios()
        if (listaComentarios.isEmpty()) {
            binding.mensajeVacio.visibility = View.VISIBLE
        } else {
            binding.mensajeVacio.visibility = View.GONE

            user = FirebaseAuth.getInstance().currentUser

            if (user != null) {
                cargarComentarios()
            } else {
                binding.mensajeVacio.visibility = View.VISIBLE

            }


        }
    }
        private fun cargarComentarios() {
            Firebase.firestore.collection("lugares")
                .document(codigoLugar)
                .collection("comentarios")
                .get()
                .addOnSuccessListener {
                    for (document in it) {
                        val comentario = document.toObject(Comentario::class.java)
                        comentario.key = document.id
                        listaComentarios.add(comentario)
                    }
                    adapter.notifyDataSetChanged()

                    if (listaComentarios.isEmpty()) {
                        binding.mensajeVacio.visibility = View.VISIBLE
                    } else {
                        binding.mensajeVacio.visibility = View.GONE
                    }

                }
        }
    }
