package com.takehome.sauravrp.di.components

import com.takehome.sauravrp.di.scopes.ActivityScope
import com.takehome.sauravrp.views.LocaleActivity
import com.takehome.sauravrp.views.SummaryActivity
import com.takehome.sauravrp.views.UserDirectoryActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [DirectoryComponent::class])
interface ActivityComponent {
    fun inject(activity: UserDirectoryActivity)
    fun inject(activity: SummaryActivity)
    fun inject(activity: LocaleActivity)

    @Component.Factory
    interface Factory {
        fun create(component: DirectoryComponent): ActivityComponent
    }
}