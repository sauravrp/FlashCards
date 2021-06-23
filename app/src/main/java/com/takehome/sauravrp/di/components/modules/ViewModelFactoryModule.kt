package com.takehome.sauravrp.di.components.modules

import com.takehome.sauravrp.di.scopes.DirectoryScope
import com.takehome.sauravrp.repository.FlashCardRepository
import com.takehome.sauravrp.viewmodels.factory.AddCardViewModelFactory
import com.takehome.sauravrp.viewmodels.factory.LocaleViewModelFactory
import com.takehome.sauravrp.viewmodels.factory.SummaryViewModelFactory
import com.takehome.sauravrp.viewmodels.factory.FlashCardViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactoryModule  {

    @Provides
    @DirectoryScope
    fun providesUserDirectoryViewModelFactory(flashCardRepository: FlashCardRepository) : FlashCardViewModelFactory {
        return FlashCardViewModelFactory(flashCardRepository)
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


    @Provides
    @DirectoryScope
    fun providesAddCardViewModelFactory(flashCardRepository: FlashCardRepository) : AddCardViewModelFactory {
        return AddCardViewModelFactory(flashCardRepository)
    }

}