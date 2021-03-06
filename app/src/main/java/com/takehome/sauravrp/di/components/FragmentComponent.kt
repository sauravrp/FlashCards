package com.takehome.sauravrp.di.components

import com.takehome.sauravrp.di.scopes.FragmentScope
import com.takehome.sauravrp.views.FlashCardDetailFragment
import com.takehome.sauravrp.views.FlashCardSummaryFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [DirectoryComponent::class])
interface FragmentComponent {
    fun inject(fragment: FlashCardSummaryFragment)
    fun inject(fragment: FlashCardDetailFragment)

    @Component.Factory
    interface Factory {
        fun create(component: DirectoryComponent): FragmentComponent
    }
}