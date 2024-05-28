package co.edu.eam.unilocal.activities

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import co.edu.eam.unilocal.adapters.ImagenesViewPager
import co.edu.eam.unilocal.adapters.ViewPagerAdapter
import co.edu.eam.unilocal.databinding.ActivityDetalleLugarBinding
import co.edu.eam.unilocal.fragments.ImagenFragment
import co.edu.eam.unilocal.models.Categoria
import co.edu.eam.unilocal.models.Comentario
import co.edu.eam.unilocal.models.Lugar
import co.edu.eam.unilocal.models.Usuario
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date
import co.edu.eam.unilocal.R
class DetalleLugarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleLugarBinding
    private var lugar: Lugar? = null
    private var codigoLugar: String = ""
    private var usuario: FirebaseUser? = null
    private var esFavorito = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usuario = FirebaseAuth.getInstance().currentUser
        codigoLugar = intent.extras?.getString("codigoLugar").orEmpty()
        usuario?.let {
            Firebase.firestore
                .collection("usuarios")
                .document(it.uid)
                .collection("favoritos")
                .document(codigoLugar)
                .get()
                .addOnSuccessListener { l ->

                    if(l.exists()){
                        esFavorito = true
                    }

                }
        }

        if (usuario != null) {
            binding.btnGuardarLugar.visibility = View.VISIBLE
            binding.btnGuardarLugar.setOnClickListener { guardarLugarFavoritos( esFavorito) }
        } else {
            binding.btnGuardarLugar.visibility = View.GONE
        }

        if (codigoLugar.isNotEmpty()) {
            Firebase.firestore.collection("lugares").document(codigoLugar)
                .get()
                .addOnSuccessListener { document ->
                    lugar = document.toObject(Lugar::class.java)
                    lugar?.key = document.id
                    lugar?.let {

                        cargarInformacionSuperior(it)
                        cargarTabs()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al cargar el lugar", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun cargarInformacionSuperior(lugar: Lugar) {

        binding.nombreLugar.text = lugar.nombre
        binding.estadoHorarioLugar.text = lugar.verificarEstadoHorario()

        if (lugar.verificarEstadoHorario() == "Abierto") {
            binding.estadoHorarioLugar.setTextColor(Color.GREEN)
            binding.horarioLugar.text = lugar.obtenerHoraCierre()
        } else {
            binding.estadoHorarioLugar.setTextColor(Color.RED)
            binding.horarioLugar.text = lugar.obtenerHoraApertura()
        }


        binding.listaImgs.adapter = ImagenesViewPager(this, lugar.imagenes)

        Firebase.firestore.collection("categorias")
            .whereEqualTo("id", lugar.idCategoria)
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    val categoria = document.toObject(Categoria::class.java)
                    categoria.nombre?.let { categoria ->
                        binding.categoriaLugar.text = categoria
                    }
                }
            }
        Firebase.firestore.collection("lugares")
            .document(lugar.key)
            .collection("comentarios")
            .get()
            .addOnSuccessListener { result ->
                val comentarios = ArrayList<Comentario>()
                var promedio = 0.0
                var cantidad = 0
                for (document in result) {
                    val comentario = document.toObject(Comentario::class.java)
                    comentarios.add(comentario)
                    promedio += comentario.calificaicon
                    cantidad++
                }

                val total = if (cantidad > 0) promedio / cantidad else 0.0
                Log.e("total", total.toString())
                val estrellas = total.toInt()
                for (i in 0 until binding.listaEstrellas.childCount) {
                    (binding.listaEstrellas[i] as TextView).setTextColor(
                        if (i < estrellas) Color.YELLOW else Color.GRAY
                    )
                }
                binding.calificacionPromedio.text = total.toString()
                binding.cantidadComentarios.text = "("+comentarios.size.toString()+")"
            }






    }

    private fun cargarTabs() {
        binding.viewPager.adapter = ViewPagerAdapter(this, codigoLugar)
        TabLayoutMediator(binding.tabsLugar, binding.viewPager) { tab, pos ->
            when (pos) {
                0 -> tab.text = getText(R.string.txt_informacion)
                1 -> tab.text = getText(R.string.txt_comentario)
                2 -> tab.text = getText(R.string.txt_novedades)
            }
        }.attach()
    }

    private fun guardarLugarFavoritos(valor:Boolean) {

        val fecha = HashMap<String, Date>()
        fecha.put("fecha", Date())


        if(!valor){
            esFavorito = true
            //  binding.btnFavorito.typeface = typefaceSolid
            // binding.btnFavorito.text = '\uf004'.toString()

            Firebase.firestore
                .collection("usuarios")
                .document(usuario!!.uid)
                .collection("favoritos")
                .document(codigoLugar)
                .set( fecha )

            Firebase.firestore
                .collection("lugares")
                .document(codigoLugar)
                .get()
                .addOnSuccessListener { document ->
                    lugar = document.toObject(Lugar::class.java)
                    lugar?.let {
                        lugar!!.corazones++
                        Firebase.firestore
                            .collection("lugares")
                            .document(codigoLugar)
                            .set(lugar!!)
                    }
                }
            Toast.makeText(this, getText(R.string.txt_anadido_favorito), Toast.LENGTH_LONG).show()
        }else{
            esFavorito = false
            //binding.btnFavorito.typeface = typefaceRegular
            //binding.btnFavorito.text = '\uf004'.toString()

            Firebase.firestore
                .collection("usuarios")
                .document(usuario!!.uid)
                .collection("favoritos")
                .document(codigoLugar)
                .delete()
            Firebase.firestore
                .collection("lugares")
                .document(codigoLugar)
                .get()
                .addOnSuccessListener { document ->
                    lugar = document.toObject(Lugar::class.java)
                    lugar?.let {
                        lugar!!.corazones--
                        Firebase.firestore
                            .collection("lugares")
                            .document(codigoLugar)
                            .set(lugar!!)
                    }
                }
            Toast.makeText(this, getText(R.string.txt_lugar_favorito_eliminado), Toast.LENGTH_LONG).show()

        }



    }
}