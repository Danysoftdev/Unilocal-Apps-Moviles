package co.edu.eam.unilocal.fragments.crearlugar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.FragmentMapaCrearLugarBinding
import co.edu.eam.unilocal.models.Posicion
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapaCrearLugarFragment : Fragment(), OnMapReadyCallback {

    lateinit var binding: FragmentMapaCrearLugarBinding
    lateinit var gMap:GoogleMap
    private var tienePermiso = true
    private val defaultLocation = LatLng(4.550923, -75.6557201)
    var posicion:Posicion? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMapaCrearLugarBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById( R.id.mapa_crear_lugar ) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        gMap.uiSettings.isZoomControlsEnabled = true

        try {
            if (tienePermiso) {
                gMap.isMyLocationEnabled = true
                gMap.uiSettings.isMyLocationButtonEnabled = true
            } else {
                gMap.isMyLocationEnabled = false
                gMap.uiSettings.isMyLocationButtonEnabled = true

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        obtenerUbicacion()

        gMap.setOnMapClickListener {

            if (posicion == null){
                posicion = Posicion()
            }

            posicion!!.lat = it.latitude
            posicion!!.lng = it.longitude

            gMap.clear()
            gMap.addMarker(MarkerOptions().position(it))
        }
    }

    private fun obtenerUbicacion() {
        try {
            if (tienePermiso) {
                val ubicacionActual =
                    LocationServices.getFusedLocationProviderClient(requireActivity()).lastLocation
                ubicacionActual.addOnCompleteListener(requireActivity()) {
                    if (it.isSuccessful) {
                        val ubicacion = it.result
                        if (ubicacion != null) {
                            val latLng = LatLng(ubicacion.latitude, ubicacion.longitude)
                            gMap.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    latLng, 15F)
                            )

                            gMap.addMarker(MarkerOptions().position(latLng).title(getString(R.string.txt_posicion)))
                        }
                    } else {
                        gMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(defaultLocation,
                                15F))
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    companion object{
        fun newInstance():MapaCrearLugarFragment{
            val args = Bundle()
            //args.putInt("id_usuario", codigoUsuario)

            val fragmento = MapaCrearLugarFragment()
            fragmento.arguments = args
            return fragmento
        }
    }

}