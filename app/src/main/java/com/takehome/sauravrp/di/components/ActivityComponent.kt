package com.takehome.sauravrp.di.components

import com.takehome.sauravrp.di.scopes.ActivityScope
import com.takehome.sauravrp.views.AddCardActivity
import com.takehome.sauravrp.views.LocaleActivity
import com.takehome.sauravrp.views.SummaryActivity
import com.takehome.sauravrp.views.FlashActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [DirectoryComponent::class])
interface ActivityComponent {
    fun inject(activity: FlashActivity)
    fun inject(activity: SummaryActivity)
    fun inject(activity: LocaleActivity)
    fun inject(activity: AddCardActivity)

    @Component.Factory
    interface Factory {
        fun create(component: DirectoryComponent): ActivityComponent
    }
}