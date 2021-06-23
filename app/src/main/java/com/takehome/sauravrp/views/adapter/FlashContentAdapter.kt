package com.takehome.sauravrp.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.takehome.sauravrp.databinding.FlashContentItemViewBinding
import com.takehome.sauravrp.helpers.FlashContentDiffResultCallback
import com.takehome.sauravrp.viewmodels.FlashContentWithLocale

class FlashContentAdapter() :
    ListAdapter<FlashContentWithLocale, FlashContentAdapter.FlashContentViewHolder>(
        FlashContentDiffResultCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashContentViewHolder {
        val itemBinding = FlashContentItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FlashContentViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FlashContentViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class FlashContentViewHolder(private val binding: FlashContentItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FlashContentWithLocale) {
            with(binding) {
                localeName.text = item.localeName
                title.text = item.title
                body.text = item.body
            }
        }
    }
}