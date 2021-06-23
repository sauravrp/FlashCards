package com.takehome.sauravrp.di.components

import com.takehome.sauravrp.di.components.modules.ViewModelFactoryModule
import com.takehome.sauravrp.di.scopes.DirectoryScope
import com.takehome.sauravrp.viewmodels.LocaleViewModelFactory
import com.takehome.sauravrp.viewmodels.SummaryViewModelFactory
import com.takehome.sauravrp.viewmodels.UserDirectoryViewModelFactory
import dagger.Component

@DirectoryScope
@Component(
        dependencies = [AppComponent::class],
        modules = [ViewModelFactoryModule::class]
)
interface DirectoryComponent {

    fun userDirectoryViewModelFactory(): UserDirectoryViewModelFactory
    fun summaryViewModelFactory(): SummaryViewModelFactory

    fun localeViewModelFactory(): LocaleViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): DirectoryComponent
    }
}