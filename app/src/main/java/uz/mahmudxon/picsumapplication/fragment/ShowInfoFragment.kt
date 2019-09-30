package uz.mahmudxon.picsumapplication.fragment


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.r0adkll.slidr.Slidr
import com.r0adkll.slidr.model.SlidrConfig
import com.r0adkll.slidr.model.SlidrInterface
import com.r0adkll.slidr.model.SlidrPosition
import kotlinx.android.synthetic.main.fragment_show_info.view.*

import uz.mahmudxon.picsumapplication.R
import uz.mahmudxon.picsumapplication.adapter.ImagePageAdapter
import uz.mahmudxon.picsumapplication.model.PicsumData
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class ShowInfoFragment : Fragment() {
    var slidrInterface : SlidrInterface ?= null
    var pos = 0
    var allData : ArrayList<PicsumData> ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_show_info, container, false)

        if(allData != null)
        {
            view.apply {
                val adapter = ImagePageAdapter(fragmentManager!!, allData!!)
                pager.adapter = adapter
                pager.currentItem = pos
                pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
                    override fun onPageScrollStateChanged(p0: Int) {
                        Log.d("TTT","onPageScrollStateChanged p0=$p0")
                    }

                    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

                    }

                    override fun onPageSelected(position: Int) {
                        Log.d("PPP","onPageScrollStateChanged p0=$position")
                        f_auth.text = allData!![position].author
                        f_width.text = "${allData!![position].width}"
                        f_height.text = "${allData!![position].height}"
                    }

                })
                Log.d("POSS", pos.toString())
               // Glide.with(requireContext()).load(data!!.download_url).placeholder(R.drawable.place_holder).into(f_img)
                f_auth.text = allData!![pos].author
                f_width.text = "${allData!![pos].width}"
                f_height.text = "${allData!![pos].height}"
                f_download.setOnClickListener {
                   // val bitmap = Glide.get(context)
                        // Save the image in external storage and get the uri
//                    val bitmap = BitmapFactory.decodeFile(allData!![pos].download_url)
//                        val uri: Uri = saveImageToExternalStorage(bitmap)

                       /* // Display the external storage saved image in image view
                        image_view_saved.setImageURI(uri)

                        // Show the saved image uri
                        text_view.text = "Saved: $uri"*/
                    }
                }}

        return view
    }


    override fun onResume() {
        super.onResume()
        if(slidrInterface == null)
            slidrInterface = Slidr.replace(view!!.findViewById(R.id.content_container), SlidrConfig.Builder().position(
                SlidrPosition.TOP).build())
    }

}

/*
// Method to save an image to external storage
fun saveImageToExternalStorage(bitmap: Bitmap):Uri{
    // Get the external storage directory path
    val path = Environment.getExternalStorageDirectory().toString()

    // Create a file to save the image
    val file = File(path, "${UUID.randomUUID()}.jpg")

    try {
        // Get the file output stream
        val stream: OutputStream = FileOutputStream(file)

        // Compress the bitmap
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

        // Flush the output stream
        stream.flush()

        // Close the output stream
        stream.close()
    } catch (e: IOException){ // Catch the exception
        e.printStackTrace()
    }

    // Return the saved image path to uri
    return Uri.parse(file.absolutePath)
}
*/
