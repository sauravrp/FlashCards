package com.takehome.sauravrp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.takehome.sauravrp.repository.FlashCardRepository

open class UserDirectoryViewModelFactory(
    private val flashCardRepository: FlashCardRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return FlashCardViewModel(flashCardRepository) as T
    }
}