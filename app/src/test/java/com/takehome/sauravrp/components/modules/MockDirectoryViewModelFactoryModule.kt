package com.takehome.sauravrp.components.modules

import androidx.lifecycle.ViewModel
import com.takehome.sauravrp.components.scopes.TestScope
import com.takehome.sauravrp.repository.FlashCardRepository
import com.takehome.sauravrp.viewmodels.FlashCardViewModel
import com.takehome.sauravrp.viewmodels.factory.FlashCardViewModelFactory
import dagger.Module
import dagger.Provides
import io.mockk.mockk

@Module
class MockDirectoryViewModelFactoryModule(private val userDirectoryViewModel: FlashCardViewModel) {

    @Provides
    @TestScope
    fun directoryRepository(): FlashCardRepository = mockk()

    @Provides
    @TestScope
    fun providesViewModelFactory(flashCardRepository: FlashCardRepository): FlashCardViewModelFactory {
        return object : FlashCardViewModelFactory(flashCardRepository) {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return userDirectoryViewModel as T
            }
        }
    }
}