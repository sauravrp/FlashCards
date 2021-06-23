package com.takehome.sauravrp.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.takehome.sauravrp.databinding.LocaleItemViewBinding
import com.takehome.sauravrp.helpers.LocaleResultDiffCallback
import com.takehome.sauravrp.viewmodels.Locale

class LocaleAdapter() :
        ListAdapter<Locale, LocaleAdapter.LocaleViewHolder>(LocaleResultDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocaleViewHolder {
        val itemBinding = LocaleItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return LocaleViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: LocaleViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class LocaleViewHolder(private val binding: LocaleItemViewBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Locale) {
            with(binding) {
                localeName.text = item.localeName
            }
        }
    }
}