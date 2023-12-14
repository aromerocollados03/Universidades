package com.arc.universidades

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


interface ItemClickListener {
    fun onItemClick(universidad: Universidad)
}

class UniAdapter(private val universidades: List<Universidad>, private val itemClickListener: MainActivity) :
    RecyclerView.Adapter<UniViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_uni, parent, false)
        return UniViewHolder(view)
    }

    override fun getItemCount(): Int = universidades.size

    override fun onBindViewHolder(holder: UniViewHolder, position: Int) {
        val item = universidades[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(item)
        }
    }


}