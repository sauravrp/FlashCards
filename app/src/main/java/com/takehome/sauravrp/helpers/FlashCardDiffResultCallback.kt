package com.takehome.sauravrp.helpers

import androidx.recyclerview.widget.DiffUtil
import com.takehome.sauravrp.viewmodels.FlashCard

class FlashCardDiffResultCallback : DiffUtil.ItemCallback<FlashCard>() {
    override fun areItemsTheSame(oldItem: FlashCard, newItem: FlashCard) = oldItem.flashCardUUID == newItem.flashCardUUID
    override fun areContentsTheSame(oldItem: FlashCard, newItem: FlashCard) = oldItem == newItem
}