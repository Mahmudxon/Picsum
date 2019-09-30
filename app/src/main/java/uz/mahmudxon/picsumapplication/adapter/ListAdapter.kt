package uz.mahmudxon.picsumapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item.view.*

import uz.mahmudxon.picsumapplication.model.PicsumData
import android.view.animation.AnimationUtils.loadAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils


class ListAdapter(private val context : Context, var data : ArrayList<PicsumData>) : RecyclerView.Adapter<ListAdapter.ViewHolder>()
{
    var lastpos = 0
    private var listener : ((Int) -> Unit) ?= null
    fun setOnClickListener(pos : (Int) -> Unit)
    {
        listener = pos
    }



    fun adddata(data: ArrayList<PicsumData>)
    {
        val position = this.data.size

        for(a in data)
        this.data.add(a)

        notifyItemRangeInserted(position, data.size)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    = ViewHolder(LayoutInflater.from(context).inflate(uz.mahmudxon.picsumapplication.R.layout.list_item, parent, false))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()

    }

    inner class ViewHolder(val view : View) : RecyclerView.ViewHolder(view)
    {
        fun bind()
        {
            view.apply {
                Glide.with(context)
                    .load(data[adapterPosition].download_url)
                    .placeholder(uz.mahmudxon.picsumapplication.R.drawable.place_holder)
                    .into(img_item)
                setOnClickListener {
                    listener?.invoke(adapterPosition)
                }
            }
            setAnimation(view, adapterPosition)
        }
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastpos) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastpos = position
        }
    }
}