package com.parris.joshtap.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val token: String,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)
