package com.example.tugaspertemuan13

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tugaspertemuan13.databinding.ItemClassBinding

typealias OnClickClass = (Clazz) -> Unit

class ClazzAdapter(private val onClickClass: OnClickClass, private val onDelete: (Clazz) -> Unit, private val onEdit: (Clazz) -> Unit) :
    ListAdapter<Clazz, ClazzAdapter.ItemClassViewHolder>(ClazzDiffCallback()) {

    inner class ItemClassViewHolder(private val binding: ItemClassBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Clazz) {
            with(binding) {
                studentMajor.text = data.major
                studentName.text = data.name
                studentGpa.text = data.gpa
                studentClassOf.text = data.class_of

                btnDelete.setOnClickListener {
                    onDelete(data)
                }

                btnEdit.setOnClickListener {
                    onEdit(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemClassViewHolder {
        val binding = ItemClassBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ItemClassViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemClassViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ClazzDiffCallback : DiffUtil.ItemCallback<Clazz>() {
        //        menentukan perubahan item mana yang diperlukan untuk diperbarui di dalam RecyclerView.
        override fun areItemsTheSame(oldItem: Clazz, newItem: Clazz): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Clazz, newItem: Clazz): Boolean {
            return oldItem == newItem
        }
    }
}