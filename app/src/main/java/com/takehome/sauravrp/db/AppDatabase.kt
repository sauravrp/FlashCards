package com.takehome.sauravrp.db

import androidx.room.*
import com.takehome.sauravrp.viewmodels.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Database(entities = [FlashCard::class,
    Locale::class,
    FlashContent::class,
    FlashContentWithLocaleRef::class
], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun flashCardDao(): FlashCardDao
}

@Dao
interface FlashCardDao {
//    @Query("SELECT * FROM FlashCard")
//    fun getAllUsers(): Single<List<FlashCard>>
//
//    @Insert
//    fun insertAllUsers(vararg users: FlashCard): Completable

    @Insert
    fun insertLocale(locale: Locale)

    @Insert
    fun insertFlashCard(flashCard: FlashCard)

    @Transaction
    @Query("SELECT * FROM FlashCard")
    fun getFlashCardWithContentsAndLocale(): Single<List<FlashCardsWithContentAndLocale>>

    @Query("SELECT * FROM FlashCard")
    fun getFlashCards(): Single<List<FlashCard>>

    @Query("SELECT * FROM Locale")
    fun getLocales(): Single<List<Locale>>

    @Query("SELECT COUNT(*) FROM FlashCard")
    fun getFlashCardsCount(): Single<Int>

    @Query("SELECT COUNT(*) FROM Locale")
    fun getLocalesCount(): Single<Int>
}