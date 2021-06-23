package com.takehome.sauravrp.helpers

import androidx.recyclerview.widget.DiffUtil
import com.takehome.sauravrp.viewmodels.FlashContentWithLocale

class FlashContentDiffResultCallback : DiffUtil.ItemCallback<FlashContentWithLocale>() {
    override fun areItemsTheSame(oldItem: FlashContentWithLocale, newItem: FlashContentWithLocale) = oldItem == newItem
    override fun areContentsTheSame(oldItem: FlashContentWithLocale, newItem: FlashContentWithLocale) = oldItem == newItem
}