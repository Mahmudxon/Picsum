package uz.mahmudxon.picsumapplication.adapter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import uz.mahmudxon.picsumapplication.fragment.ImagePage
import uz.mahmudxon.picsumapplication.model.PicsumData

@Suppress("DEPRECATION")
class ImagePageAdapter(fm: FragmentManager, val allData: ArrayList<PicsumData>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        Log.d("WWW", getItemPosition(allData[position]).toString())
        val d = allData[position]
        val fragment = ImagePage()
        val bundle = Bundle()
        bundle.putString("IMAGE_URL", d.download_url)
        bundle.putString("AUTHOR", d.author)
        bundle.putInt("HEIGHT", d.height)
        bundle.putInt("WIDTH", d.width)
        bundle.putString("URL", d.url)
        fragment.arguments = bundle
        return fragment
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }


    override fun getCount() = allData.size
}