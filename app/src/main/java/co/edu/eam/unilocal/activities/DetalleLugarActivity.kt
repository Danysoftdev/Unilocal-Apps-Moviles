package co.edu.eam.unilocal.activities


import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import co.edu.eam.unilocal.adapters.ViewPagerAdapter
import co.edu.eam.unilocal.databinding.ActivityDetalleLugarBinding
import co.edu.eam.unilocal.models.Categoria
import co.edu.eam.unilocal.models.Lugar
import co.edu.eam.unilocal.models.Usuario
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

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

        val calificacion = 2 //lugar.obtenerCalificacionPromedio(Comentarios.listar(lugar.id))
        for (i in 0..calificacion) {
            (binding.listaEstrellas[i] as TextView).setTextColor(Color.YELLOW)
        }

        binding.cantidadComentarios.text = "2" // "(${Comentarios.obtenerCantidadComentarios(lugar.id).toString()})"
        val categoryPlace = Categoria() //Categorias.obtener(lugar.idCategoria)
        binding.categoriaLugar.text = categoryPlace?.nombre
        binding.estadoHorarioLugar.text = lugar.verificarEstadoHorario()

        if (lugar.verificarEstadoHorario() == "Abierto") {
            binding.estadoHorarioLugar.setTextColor(Color.GREEN)
            binding.horarioLugar.text = lugar.obtenerHoraCierre()
        } else {
            binding.estadoHorarioLugar.setTextColor(Color.RED)
            binding.horarioLugar.text = lugar.obtenerHoraApertura()
        }
    }

    private fun cargarTabs() {
        binding.viewPager.adapter = ViewPagerAdapter(this, codigoLugar)
        TabLayoutMediator(binding.tabsLugar, binding.viewPager) { tab, pos ->
            when (pos) {
                0 -> tab.text = "Información"
                1 -> tab.text = "Comentario"
                2 -> tab.text = "Novedades"
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
                Toast.makeText(this, "Añadido a los lugares favoritos", Toast.LENGTH_LONG).show()
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
            Toast.makeText(this, "Eliminado de los lugares favoritos", Toast.LENGTH_LONG).show()

        }


        /*
        usuario?.let {
            it.favoritos.add(lugar!!)
            Toast.makeText(this, "Añadido a los lugares favoritos", Toast.LENGTH_LONG).show()
        }*/
    }
}
