package com.parris.joshtap.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.parris.joshtap.R
import com.parris.joshtap.data.CardEntity

class CardAdapter(
    private var items: List<CardEntity> = emptyList(),
    private val onItemClick: ((CardEntity) -> Unit)? = null
) : RecyclerView.Adapter<CardAdapter.VH>() {
    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvSubtitle: TextView = view.findViewById(R.id.tvSubtitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val c = items[position]
        holder.tvTitle.text = c.name
        holder.tvSubtitle.text = "token=${c.token}"
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(c)
        }
    }

    override fun getItemCount(): Int = items.size

    fun submitList(list: List<CardEntity>) {
        items = list
        notifyDataSetChanged()
    }
    
    // expose for subclasses or tests if needed
    fun getItemAt(position: Int): CardEntity = items[position]
}
