package com.takehome.sauravrp.helpers

import androidx.recyclerview.widget.DiffUtil
import com.takehome.sauravrp.viewmodels.Locale

class LocaleResultDiffCallback : DiffUtil.ItemCallback<Locale>() {
    override fun areItemsTheSame(oldItem: Locale, newItem: Locale) = oldItem.localeUUID == newItem.localeUUID
    override fun areContentsTheSame(oldItem: Locale, newItem: Locale) = oldItem == newItem
}