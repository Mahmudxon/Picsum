package uz.mahmudxon.picsumapplication.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.page_image.view.*
import uz.mahmudxon.picsumapplication.R

class ImagePage : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
    val view =inflater.inflate(R.layout.page_image, container, false)
    val imgUrl =arguments?.getString("IMAGE_URL") ?: R.drawable.place_holder
        Log.d("EEE", arguments.toString())
         Glide.with(requireContext()).load(imgUrl).placeholder(R.drawable.place_holder).into(view.f_img)
         return view
    }
}