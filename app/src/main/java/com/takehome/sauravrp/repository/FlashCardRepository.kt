package com.takehome.sauravrp.repository

import com.takehome.sauravrp.db.AppDatabase
import com.takehome.sauravrp.di.scopes.DirectoryScope
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@DirectoryScope
class FlashCardRepository @Inject constructor(
    private val database: AppDatabase
) {
    fun getLocaleCount(): Single<Int> {
        return database.flashCardDao().getLocalesCount()
    }

    fun getFlashCardsCount(): Single<Int> {
        return database.flashCardDao().getFlashCardsCount()
    }
}

