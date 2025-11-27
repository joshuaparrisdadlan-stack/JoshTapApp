package com.parris.yotolite.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CardWithTracks(
    @Embedded val card: CardEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(value = CardTrackJoin::class, parentColumn = "card_id", entityColumn = "track_id")
    )
    val tracks: List<TrackEntity>
)
