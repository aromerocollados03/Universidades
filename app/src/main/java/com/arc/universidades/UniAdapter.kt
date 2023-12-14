package com.arc.universidades

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arc.universidades.R

class UniAdapter(private val message:List<Universidad>):RecyclerView.Adapter<UniViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_uni, parent, false)
        return UniViewHolder(view)
    }

    override fun getItemCount(): Int = message.size

    override fun onBindViewHolder(holder: UniViewHolder, position: Int) {
        val item = message[position]
        holder.bind(item)
    }
}