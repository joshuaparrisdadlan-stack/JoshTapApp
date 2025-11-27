package com.parris.yotolite.data

class AppRepository(private val db: AppDatabase) {
    private val dao = db.appDao()

    suspend fun insertTrack(displayName: String, localUri: String, durationMs: Long = 0L): Long {
        val t = TrackEntity(displayName = displayName, localUri = localUri, durationMs = durationMs)
        return dao.insertTrack(t)
    }

    suspend fun listTracks(): List<TrackEntity> = dao.listTracks()

    suspend fun insertCard(name: String, token: String): Long {
        val c = CardEntity(name = name, token = token)
        return dao.insertCard(c)
    }

    suspend fun listCards(): List<CardEntity> = dao.listCards()

    suspend fun setCardTracks(cardId: Long, orderedTrackIds: List<Long>) = dao.setCardTracks(cardId, orderedTrackIds)

    suspend fun getCardWithTracksByToken(token: String): CardWithTracks? = dao.getCardWithTracksByToken(token)
}
