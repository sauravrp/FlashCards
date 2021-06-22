package com.takehome.sauravrp.di.components

import android.content.Context
import com.takehome.sauravrp.db.AppDatabase
import com.takehome.sauravrp.di.components.modules.DatabaseModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class])
interface AppComponent {
    fun appDatabase(): AppDatabase

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindAppContext(appContext: Context): Builder
        fun build(): AppComponent
    }

}