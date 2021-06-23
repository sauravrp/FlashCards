package com.takehome.sauravrp.repository

import androidx.room.Insert
import com.takehome.sauravrp.db.AppDatabase
import com.takehome.sauravrp.di.scopes.DirectoryScope
import com.takehome.sauravrp.viewmodels.FlashCard
import com.takehome.sauravrp.viewmodels.FlashContent
import com.takehome.sauravrp.viewmodels.FlashContentWithLocaleRef
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

    fun getLocales(): Single<List<Locale>> {
        return database.flashCardDao().getLocales()
    }

    fun addLocale(locale: Locale): Completable {
        return database.flashCardDao().insertLocale(locale)
    }

    fun getFlashCardsCount(): Single<Int> {
        return database.flashCardDao().getFlashCardsCount()
    }

    fun getFlashCards(): Single<List<FlashCard>> {
        return database.flashCardDao().getFlashCards()
    }

    fun insertFlashCard(flashCard: FlashCard): Completable =
        database.flashCardDao().insertFlashCard(flashCard)

    fun insertFlashContent(flashContent: Map<Locale, FlashContent>): Completable =
        database.flashCardDao().insertFlashContent(*flashContent.values.toTypedArray()).andThen {
            val refData = flashContent.map { entry ->
                FlashContentWithLocaleRef(
                    localeUUID = entry.key.localeUUID,
                    contentUUID = entry.value.contentUUID
                )
            }
            database.flashCardDao().insertFlashContentWithLocaleRef(*refData.toTypedArray())
        }

}

