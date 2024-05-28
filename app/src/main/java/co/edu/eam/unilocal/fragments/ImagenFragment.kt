package co.edu.eam.unilocal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.FragmentImagenBinding
import com.bumptech.glide.Glide

class ImagenFragment : Fragment() {

    private var param2: String? = null
    lateinit var binding: FragmentImagenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param2 = it.getString("url_img")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagenBinding.inflate(inflater, container, false)

        Glide.with( this )
            .load(param2)
            .into(binding.imgUrl)

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param2: String) =
            ImagenFragment().apply {
                arguments = Bundle().apply {
                    putString("url_img", param2)
                }
            }
    }
}