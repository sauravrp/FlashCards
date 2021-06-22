package com.takehome.sauravrp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.takehome.sauravrp.repository.FlashCardRepository

open class SummaryViewModelFactory(
    private val flashCardRepository: FlashCardRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return SummaryViewModel(flashCardRepository) as T
    }
}