package com.parris.joshtap.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.parris.joshtap.R
import com.parris.joshtap.data.TrackEntity

class TrackAdapter(
    private var items: List<TrackEntity> = emptyList(),
    private val onItemClick: ((TrackEntity) -> Unit)? = null
) : RecyclerView.Adapter<TrackAdapter.VH>() {
    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tv: TextView = view.findViewById(R.id.tvItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val t = items[position]
        val durationMs = t.durationMs
        val mins = (durationMs / 1000) / 60
        val secs = (durationMs / 1000) % 60
        val dur = if (durationMs > 0) String.format("%d:%02d", mins, secs) else ""
        holder.tv.text = if (dur.isNotEmpty()) "${t.displayName} — $dur" else t.displayName

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(t)
        }
    }

    override fun getItemCount(): Int = items.size

    fun submitList(list: List<TrackEntity>) {
        items = list
        notifyDataSetChanged()
    }
}
