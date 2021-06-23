package com.takehome.sauravrp.db

import androidx.room.*
import com.takehome.sauravrp.viewmodels.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Database(
    entities = [FlashCard::class,
        Locale::class,
        FlashContent::class,
        FlashWithContentAndLocalRelationship::class
    ],
    views = [FlashContentWithLocale::class],
    exportSchema = false,
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun flashCardDao(): FlashCardDao
    abstract fun localeDao(): LocaleDao
    abstract fun cardContentDao(): CardContentDao
    abstract fun relationshipDao(): RelationshipDao
}

@Dao
interface LocaleDao {
    @Insert
    fun insertLocale(locale: Locale): Completable

    @Query("SELECT * FROM Locale")
    fun getLocales(): Single<List<Locale>>

    @Query("SELECT COUNT(*) FROM Locale")
    fun getLocalesCount(): Single<Int>
}

@Dao
interface FlashCardDao {
    @Insert
    fun insertFlashCard(flashCard: FlashCard): Completable

    @Query("SELECT * FROM flash_card")
    fun getFlashCards(): Single<List<FlashCard>>

    @Query("SELECT COUNT(*) FROM flash_card")
    fun getFlashCardsCount(): Single<Int>
}

@Dao
interface CardContentDao {

    @Insert
    fun insertContent(vararg flashContent: FlashContent): Completable


    //    @Insert
//    fun insertFlashContentWithLocaleRef(vararg flashContentWithLocaleRef: FlashWithContentAndLocal): Completable
//
    @Query("SELECT FlashContentWithLocale.* FROM flash_content_locale_ref INNER JOIN FlashContentWithLocale ON flash_content_locale_ref.contentID = FlashContentWithLocale.contentID WHERE flash_content_locale_ref.flashID = :flashID")
    fun getFlashCards(flashID: String): Single<List<FlashContentWithLocale>>

//    @Query("SELECT * FROM FlashContentWithLocale INNER JOIN flash_content_locale_ref ON flash_content_locale_ref.flashID = :flashID")
//    fun getFlashCardWithContentsAndLocale(flashID: String): Single<List<FlashContentWithLocale>>
//
//
//
//
//    @Query("SELECT COUNT(*) FROM FlashCard")
//    fun getFlashCardsCount(): Single<Int>

}

@Dao
interface RelationshipDao {
    @Insert
    fun insertRelationship(vararg relationship: FlashWithContentAndLocalRelationship): Completable
}