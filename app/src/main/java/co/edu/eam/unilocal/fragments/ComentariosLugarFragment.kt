package co.edu.eam.unilocal.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.adapter.LugarAdapter
import co.edu.eam.unilocal.adapters.ComentarioAdapter
import co.edu.eam.unilocal.databinding.FragmentComentariosLugarBinding
import co.edu.eam.unilocal.models.Comentario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.log
import co.edu.eam.unilocal.R
/*import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid*/
class ComentariosLugarFragment : Fragment() {

    lateinit var binding: FragmentComentariosLugarBinding
    private var codigoLugar: String = ""
    lateinit var listaComentarios: ArrayList<Comentario>
    private  lateinit var adapter: ComentarioAdapter
    private var estrellas: Int = 0
    private var codigoUsuario: String = ""
    private var user: FirebaseUser? = null
    private val SENDGRID_API_KEY = "SG.vv8v9DvTTd-3iZEHDkfAqQ.kcOinDNYJgFDMJ_LKrODdnZJGqvldfMrcXL8qCGi8Mo"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null){
            codigoLugar = requireArguments().getString("codigoLugar","")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listaComentarios = ArrayList()

        binding = FragmentComentariosLugarBinding.inflate(inflater, container, false)
        adapter = ComentarioAdapter(requireContext(), listaComentarios)

        user = FirebaseAuth.getInstance().currentUser

        binding.listaComentarios.layoutManager = LinearLayoutManager(requireContext())
        binding.listaComentarios.adapter = adapter

        if (user != null) {
            cargarComentarios()
            codigoUsuario = user!!.uid
            binding.nombreLayout.visibility = View.VISIBLE
            binding.barraEstrellas.visibility = View.VISIBLE
            binding.separador.visibility = View.VISIBLE

        } else {
            cargarComentarios()
            binding.nombreLayout.visibility = View.GONE
            binding.barraEstrellas.visibility = View.GONE
            binding.separador.visibility = View.GONE

        }

        for (i in 0 until binding.listaEstrellas.childCount){
            (binding.listaEstrellas[i] as TextView).setOnClickListener {presionarEstrella(i)}
        }
        binding.btComentar.setOnClickListener { crearComentario() }

        return binding.root
    }

    private fun cargarComentarios(){
        Firebase.firestore.collection("lugares")
            .document(codigoLugar)
            .collection("comentarios")
            .get()
            .addOnSuccessListener {
                for (document in it){
                    val comentario = document.toObject(Comentario::class.java)
                    comentario.key = document.id
                    listaComentarios.add(comentario)
                }
                adapter.notifyDataSetChanged()
            }
    }

    private fun presionarEstrella(pos:Int){
        estrellas = pos + 1
        borrarSeleccionEstrellas()
        for (i in 0 .. pos){
            (binding.listaEstrellas[i] as TextView).setTextColor(Color.YELLOW)
        }

    }
    fun crearComentario(){
        val texto = binding.txtComentario.text.toString()

        if (texto.isNotEmpty() && estrellas > 0){
            val comentario =Comentario(texto,  codigoUsuario,  estrellas)
            Firebase.firestore.collection("lugares")
                .document(codigoLugar)
                .collection("comentarios")
                .add(comentario)
                .addOnSuccessListener {
                    //enviarCorreo(comentario)
                    limpiarFormulario()
                    Snackbar.make(binding.root,getText(R.string.txt_comentario_enviado) , Snackbar.LENGTH_LONG).show()

                    listaComentarios.add(comentario)
                    adapter.notifyItemInserted(listaComentarios.size-1)
                }
                .addOnFailureListener {
                    Snackbar.make(binding.root, "${it.message}", Snackbar.LENGTH_LONG).show()
                }

        }else{
            Snackbar.make(binding.root, getText(R.string.txt_advertencia_comentario), Snackbar.LENGTH_LONG).show()
        }
    }
   /* private fun enviarCorreo(comentario: Comentario) {
        val sendgrid = SendGrid(SENDGRID_API_KEY)
        val from = Email("lopez.hamilton.5426@eam.edu.co")
        val to = Email("hamilt727@gmail.com")
        val subject = "Nuevo comentario en lugar"
        val content = Content("text/plain", "Se ha creado un nuevo comentario: ${comentario.texto}")
        val mail = Mail(from, subject, to, content)

        val request = Request()
        try {
            request.method = Method.POST
            request.endpoint = "mail/send"
            request.body = mail.build()
            val response = sendgrid.api(request)
            println(response.statusCode)
            println(response.body)
            println(response.headers)
        } catch (ex: Exception) {
            println(ex.message)
        }
    }*/

    private fun limpiarFormulario(){
        binding.txtComentario.setText("")
        borrarSeleccionEstrellas()
        estrellas = 0
    }

    private fun borrarSeleccionEstrellas(){
        for (i in 0 until binding.listaEstrellas.childCount){
            (binding.listaEstrellas[i] as TextView).setTextColor(Color.GRAY)
        }
    }

    companion object{
        fun newInstance(codigoLugar: String): ComentariosLugarFragment{
            val args = Bundle()
            args.putString("codigoLugar", codigoLugar)
            val fragmento = ComentariosLugarFragment()
            fragmento.arguments = args
            return fragmento
        }
    }
}