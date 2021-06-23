package com.takehome.sauravrp.di.components

import com.takehome.sauravrp.di.components.modules.ViewModelFactoryModule
import com.takehome.sauravrp.di.scopes.DirectoryScope
import com.takehome.sauravrp.viewmodels.factory.AddCardViewModelFactory
import com.takehome.sauravrp.viewmodels.factory.LocaleViewModelFactory
import com.takehome.sauravrp.viewmodels.factory.SummaryViewModelFactory
import com.takehome.sauravrp.viewmodels.factory.FlashCardViewModelFactory
import dagger.Component

@DirectoryScope
@Component(
        dependencies = [AppComponent::class],
        modules = [ViewModelFactoryModule::class]
)
interface DirectoryComponent {

    fun flashCardViewModelFactory(): FlashCardViewModelFactory
    fun summaryViewModelFactory(): SummaryViewModelFactory
    fun localeViewModelFactory(): LocaleViewModelFactory
    fun addCardViewModelFactory(): AddCardViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): DirectoryComponent
    }
}