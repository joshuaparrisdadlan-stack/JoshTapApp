package com.parris.joshtap.ui

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.parris.joshtap.R
import com.parris.joshtap.data.TrackEntity

class CardTracksAdapter(
    private val items: MutableList<TrackEntity> = mutableListOf(),
    private val onRemoveClick: ((TrackEntity, Int) -> Unit)? = null,
    private val onStartDrag: ((RecyclerView.ViewHolder) -> Unit)? = null
) : RecyclerView.Adapter<CardTracksAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTrackTitle)
        val btnRemove: ImageButton = itemView.findViewById(R.id.btnRemove)
        val dragHandle: ImageView = itemView.findViewById(R.id.dragHandle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_assigned_track, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val t = items[position]
        holder.tvTitle.text = t.displayName ?: t.localUri.toString()
        holder.btnRemove.setOnClickListener {
            val pos = holder.bindingAdapterPosition
            if (pos >= 0 && pos < items.size) {
                onRemoveClick?.invoke(items[pos], pos)
            }
        }

        holder.dragHandle.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                onStartDrag?.invoke(holder)
            }
            false
        }
    }

    override fun getItemCount(): Int = items.size

    fun submitList(list: List<TrackEntity>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun removeAt(index: Int) {
        if (index < 0 || index >= items.size) return
        items.removeAt(index)
        notifyItemRemoved(index)
    }

    fun insertAt(index: Int, track: TrackEntity) {
        val i = index.coerceIn(0, items.size)
        items.add(i, track)
        notifyItemInserted(i)
    }

    fun moveItem(from: Int, to: Int) {
        if (from < 0 || to < 0 || from >= items.size || to >= items.size) return
        val item = items.removeAt(from)
        items.add(to, item)
        notifyItemMoved(from, to)
    }

    fun getOrderedIds(): List<Long> = items.map { it.id }
}
