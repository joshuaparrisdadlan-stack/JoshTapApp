package com.parris.yotolite.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.parris.yotolite.R
import com.parris.yotolite.data.TrackEntity

class TrackAdapter(private var items: List<TrackEntity> = emptyList()) : RecyclerView.Adapter<TrackAdapter.VH>() {
    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tv: TextView = view.findViewById(R.id.tvItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val t = items[position]
        holder.tv.text = t.displayName
    }

    override fun getItemCount(): Int = items.size

    fun submitList(list: List<TrackEntity>) {
        items = list
        notifyDataSetChanged()
    }
}
