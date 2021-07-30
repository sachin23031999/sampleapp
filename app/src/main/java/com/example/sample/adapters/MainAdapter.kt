package com.example.sample.adapters

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.sample.R
import com.example.sample.models.Details
import kotlinx.android.synthetic.main.adapter.view.*
import java.util.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var list = mutableListOf<Details>()
    var listener: RvInterface? = null

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image_preview = itemView.image_preview
        val name = itemView.name
        val card = itemView.cardView
        val user_info = itemView.user_info
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter,
            parent, false
        )
        return MainViewHolder(itemView)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val detail = list[position]
        val image_url = detail.image
        Glide.with(holder.itemView.context)
            .load(image_url)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {

                   // holder.image_preview.setImageDrawable(resource)
                    holder.card.background = resource
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    TODO("Not yet implemented")
                }

            })
            .into(holder.image_preview)

        holder.name.text = detail.user_name.toString()
        holder.card.setOnClickListener {
            listener?.onImageClickListener(detail.video_url)
        }
        holder.user_info.setOnClickListener {
            listener?.onUserInfoCLickListener(detail.user_url)
        }

    }

    override fun getItemCount(): Int = list.size

    fun setAdapterData(list: List<Details>) {
        this.list = list as MutableList<Details>
        notifyDataSetChanged()
    }
}