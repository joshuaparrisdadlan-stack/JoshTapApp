package com.parris.joshtap.data

import androidx.room.*

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity): Long

    @Query("SELECT * FROM tracks ORDER BY display_name ASC")
    suspend fun listTracks(): List<TrackEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: CardEntity): Long

    @Query("SELECT * FROM cards ORDER BY created_at DESC")
    suspend fun listCards(): List<CardEntity>

    @Transaction
    suspend fun setCardTracks(cardId: Long, orderedTrackIds: List<Long>) {
        // remove existing joins for card
        deleteJoinsForCard(cardId)
        // insert new joins
        orderedTrackIds.forEachIndexed { index, trackId ->
            insertJoin(CardTrackJoin(cardId = cardId, trackId = trackId, position = index))
        }
    }

    @Query("DELETE FROM card_track_join WHERE card_id = :cardId")
    suspend fun deleteJoinsForCard(cardId: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoin(join: CardTrackJoin)

    @Transaction
    @Query("SELECT * FROM cards WHERE token = :token LIMIT 1")
    suspend fun getCardWithTracksByToken(token: String): CardWithTracks?

    @Transaction
    @Query("SELECT * FROM cards WHERE nfc_token = :token LIMIT 1")
    suspend fun getCardWithTracksByNfcToken(token: String): CardWithTracks?

    @Transaction
    @Query("SELECT * FROM cards WHERE id = :cardId LIMIT 1")
    suspend fun getCardWithTracksById(cardId: Long): CardWithTracks?
}
