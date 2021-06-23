package com.takehome.sauravrp.viewmodels

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity
data class FlashCard(
        @PrimaryKey val flashCardUUID: Int,
        val name: String
) : Parcelable


@Parcelize
@Entity
data class Locale(
        @PrimaryKey val localeUUID: String,
        val localeName: String
) : Parcelable

@Parcelize
@Entity
data class FlashContent(
        @PrimaryKey val contentUUID: String,
        val flashID: String,
        val title: String,
        val body: String
) : Parcelable

// room specific relationship entities
@Entity(primaryKeys = ["localeUUID", "contentUUID"])
data class FlashContentWithLocaleRef(
        val localeUUID: String,
        @ColumnInfo(index = true)
        val contentUUID: String
)

data class FlashContentWithLocale(
        @Embedded val content: FlashContent,
        @Relation(
                parentColumn = "contentUUID",
                entityColumn = "localeUUID",
                associateBy = Junction(FlashContentWithLocaleRef::class)
        )
        val locale: Locale
)


//@Entity(primaryKeys = ["flashCardUUID", "contentUUID"])
//data class FlashCardsWithContent(
//        val flashCardUUID: String,
//        @ColumnInfo(index = true)
//        val contentUUID: String
//)

//data class FlashCardsWithContent(
//        @Embedded val flashCard: FlashCard,
//        @Relation(
//                parentColumn = "flashCardUUID",
//                entityColumn = "flashID"
//        )
//        val content: List<FlashContent>
//)

data class FlashCardsWithContentAndLocale(
        @Embedded val flashCard: FlashCard,
        @Relation(
                entity = FlashContent::class,
                parentColumn = "flashCardUUID",
                entityColumn = "flashID"
        )
        val content: List<FlashContentWithLocale>
)

//data class FlashCardWithContents(
//        @Embedded val flashCard: FlashCard,
//        @Relation(
//                entity = FlashContentRelationship::class,
//                parentColumn = "flashCardUUID",
//                entityColumn = "contentUUID"
////                associateBy = Junction(FlashContentRelationship::class)
//        )
//        val contentWithLocale: List<FlashContentWithLocale>
//)