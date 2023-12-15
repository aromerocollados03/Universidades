package com.arc.universidades

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arc.universidades.databinding.ItemUniBinding

class UniAdapter(private val universidades: List<Universidad>, private val itemClickListener: MainActivity) :
    RecyclerView.Adapter<UniViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemUniBinding.inflate(layoutInflater, parent, false)
        return UniViewHolder(binding)
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
