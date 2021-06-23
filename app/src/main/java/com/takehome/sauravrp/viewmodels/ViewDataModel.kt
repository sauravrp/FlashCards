package com.takehome.sauravrp.viewmodels

import androidx.room.*

@Entity(tableName = "flash_card")
data class FlashCard(
    @PrimaryKey val flashCardUUID: String,
    val name: String
)


@Entity(tableName = "locale")
data class Locale(
    @PrimaryKey val localeUUID: String,
    val localeName: String
)

@Entity(tableName = "flash_content")
data class FlashContent(
    @PrimaryKey val contentUUID: String,
    val title: String,
    val body: String
)

// room specific relationship entities
@Entity(
    tableName = "flash_content_locale_ref",
    foreignKeys = [
        ForeignKey(
            entity = FlashCard::class,
            parentColumns = ["flashCardUUID"],
            childColumns = ["flashID"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Locale::class,
            parentColumns = ["localeUUID"],
            childColumns = ["localeID"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = FlashContent::class,
            parentColumns = ["contentUUID"],
            childColumns = ["contentID"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class FlashWithContentAndLocalRelationship(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val flashID: String,
    val localeID: String,
    val contentID: String
)

@DatabaseView("SELECT flash_content.contentUUID as contentID, flash_content.title as title, flash_content.body as body, locale.localeUUID as localeID, locale.localeName as localeName FROM flash_content INNER JOIN flash_content_locale_ref ON flash_content.contentUUID = flash_content_locale_ref.contentID INNER JOIN locale ON flash_content_locale_ref.localeID = locale.localeUUID")
data class FlashContentWithLocale(
    val contentID: String,
    val title: String,
    val body: String,
    val localeID: String,
    val localeName: String
)