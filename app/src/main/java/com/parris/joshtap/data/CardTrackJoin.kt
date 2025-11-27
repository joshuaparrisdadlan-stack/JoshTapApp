package com.parris.joshtap.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "card_track_join", primaryKeys = ["card_id","track_id"]) 
data class CardTrackJoin(
    @ColumnInfo(name = "card_id") val cardId: Long,
    @ColumnInfo(name = "track_id") val trackId: Long,
    val position: Int
)
