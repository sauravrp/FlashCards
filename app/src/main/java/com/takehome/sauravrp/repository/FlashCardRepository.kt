package com.takehome.sauravrp.repository

import com.takehome.sauravrp.db.AppDatabase
import com.takehome.sauravrp.di.scopes.DirectoryScope
import com.takehome.sauravrp.viewmodels.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@DirectoryScope
class FlashCardRepository @Inject constructor(
    private val database: AppDatabase
) {
    fun getLocaleCount(): Single<Int> {
        return database.localeDao().getLocalesCount()
    }

    fun getLocales(): Single<List<Locale>> {
        return database.localeDao().getLocales()
    }

    fun addLocale(locale: Locale): Completable {
        return database.localeDao().insertLocale(locale)
    }

    fun getFlashCardsCount(): Single<Int> {
        return database.flashCardDao().getFlashCardsCount()
    }

    fun getFlashCards(): Single<List<FlashCard>> {
        return database.flashCardDao().getFlashCards()
    }

    fun getFlashCardContents(flashCard: FlashCard): Single<List<FlashContentWithLocale>> {
        return database.cardContentDao().getFlashCards(flashCard.flashCardUUID)
    }

    fun insertFlashCard(flashCard: FlashCard): Completable =
        database.flashCardDao().insertFlashCard(flashCard)

    fun insertFlashContent(
        flashCard: FlashCard,
        flashContent: Map<Locale, FlashContent>
    ): Completable {
        val ref = flashContent.map { entry ->
            FlashWithContentAndLocalRelationship(
                flashID = flashCard.flashCardUUID,
                localeID = entry.key.localeUUID,
                contentID = entry.value.contentUUID
            )
        }

        return database.cardContentDao().insertContent(*flashContent.values.toTypedArray())
            .andThen (
                database.relationshipDao().insertRelationship(*ref.toTypedArray())
            )
    }
}

