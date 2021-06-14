package com.oratakashi.jsonplaceholder.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.oratakashi.jsonplaceholder.data.model.Photos
import com.oratakashi.jsonplaceholder.databinding.AdapterPhotosBinding
import com.oratakashi.viewbinding.core.ViewHolder
import com.oratakashi.viewbinding.core.viewBinding

class MainAdapter : RecyclerView.Adapter<ViewHolder<AdapterPhotosBinding>>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<AdapterPhotosBinding> = viewBinding(parent)

    override fun onBindViewHolder(holder: ViewHolder<AdapterPhotosBinding>, position: Int) {
        with(holder.binding){
            tvTitle.text = data[position].title
            ivImage.load(data[position].thumbnailUrl)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun submitList(data: List<Photos>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    private val data : MutableList<Photos> by lazy {
        ArrayList()
    }
}