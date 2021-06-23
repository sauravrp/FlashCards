package com.takehome.sauravrp.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.takehome.sauravrp.databinding.FlashCardItemViewBinding
import com.takehome.sauravrp.helpers.FlashCardDiffResultCallback
import com.takehome.sauravrp.viewmodels.FlashCard

class FlashListAdapter(private val flashCardSelectionListener: FlashListAdapter.FlashCardSelectionListener) :
    ListAdapter<FlashCard, FlashListAdapter.FlashCardViewHolder>(FlashCardDiffResultCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashCardViewHolder {
        val itemBinding = FlashCardItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FlashCardViewHolder(itemBinding).apply {
            itemBinding.root.setOnClickListener(this)
        }
    }

    override fun onBindViewHolder(holder: FlashCardViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class FlashCardViewHolder(private val binding: FlashCardItemViewBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(item: FlashCard) {
            with(binding) {
                root.setOnClickListener(this@FlashCardViewHolder)

                name.text = item.name
            }
        }

        override fun onClick(v: View?) {
            flashCardSelectionListener.cardItemSelected(currentList[adapterPosition])
        }
    }

    interface FlashCardSelectionListener {
        fun cardItemSelected(flashCard: FlashCard)
    }
}