package com.takehome.sauravrp.repository

import com.takehome.sauravrp.db.AppDatabase
import com.takehome.sauravrp.di.scopes.DirectoryScope
import com.takehome.sauravrp.viewmodels.Locale
import io.reactivex.rxjava3.core.Completable
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

    fun getLocales(): Single<List<Locale>> {
        return database.flashCardDao().getLocales()
    }

    fun addLocale(locale: Locale) : Completable {
        return database.flashCardDao().insertLocale(locale)
    }
}

