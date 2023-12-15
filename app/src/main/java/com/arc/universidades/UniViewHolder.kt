package com.arc.universidades

import androidx.recyclerview.widget.RecyclerView
import com.arc.universidades.databinding.ItemUniBinding

class UniViewHolder(private val binding: ItemUniBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(universidad: Universidad) {
        binding.txUni.text = universidad.name
    }
}