package com.takehome.sauravrp.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.takehome.sauravrp.repository.FlashCardRepository
import com.takehome.sauravrp.viewmodels.AddCardViewModel
import com.takehome.sauravrp.viewmodels.LocaleViewModel

open class AddCardViewModelFactory(
    private val flashCardRepository: FlashCardRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return AddCardViewModel(flashCardRepository) as T
    }
}