package com.takehome.sauravrp.di.components.modules

import com.takehome.sauravrp.di.scopes.DirectoryScope
import com.takehome.sauravrp.repository.FlashCardRepository
import com.takehome.sauravrp.viewmodels.LocaleViewModelFactory
import com.takehome.sauravrp.viewmodels.SummaryViewModelFactory
import com.takehome.sauravrp.viewmodels.UserDirectoryViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactoryModule  {

    @Provides
    @DirectoryScope
    fun providesUserDirectoryViewModelFactory(flashCardRepository: FlashCardRepository) : UserDirectoryViewModelFactory {
        return UserDirectoryViewModelFactory(flashCardRepository)
    }

    @Provides
    @DirectoryScope
    fun providesSummaryViewModelFactory(flashCardRepository: FlashCardRepository) : SummaryViewModelFactory {
        return SummaryViewModelFactory(flashCardRepository)
    }

    @Provides
    @DirectoryScope
    fun providesLocaleViewModelFactory(flashCardRepository: FlashCardRepository) : LocaleViewModelFactory {
        return LocaleViewModelFactory(flashCardRepository)
    }

}