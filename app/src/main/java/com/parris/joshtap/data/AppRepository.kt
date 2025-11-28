package com.parris.joshtap.data

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

    suspend fun getCardWithTracksById(cardId: Long): CardWithTracks? = dao.getCardWithTracksById(cardId)

    suspend fun getCardByNfcToken(token: String): CardWithTracks? = dao.getCardWithTracksByNfcToken(token)

    suspend fun getOrCreateTokenForCard(cardId: Long): String {
        val existing = dao.getCardWithTracksById(cardId)
        if (existing != null && !existing.card.nfcToken.isNullOrBlank()) {
            return existing.card.nfcToken!!
        }
        // generate new token and persist
        val newToken = com.parris.joshtap.util.TokenGenerator.generateToken()
        val card = existing?.card ?: dao.listCards().firstOrNull { it.id == cardId }
        if (card != null) {
            // keep existing token field (used elsewhere) if present, otherwise set it to newToken
            val tokenToSet = if (card.token.isBlank()) newToken else card.token
            val updated = card.copy(token = tokenToSet, nfcToken = newToken)
            dao.insertCard(updated)
        }
        return newToken
    }

    suspend fun addTrackToCard(cardId: Long, trackId: Long) {
        val existing = dao.getCardWithTracksById(cardId)
        val ids = existing?.tracks?.map { it.id }?.toMutableList() ?: mutableListOf()
        // avoid duplicates
        if (!ids.contains(trackId)) {
            ids.add(trackId)
            dao.setCardTracks(cardId, ids)
        }
    }

    suspend fun removeTrackFromCard(cardId: Long, trackId: Long) {
        val existing = dao.getCardWithTracksById(cardId)
        val ids = existing?.tracks?.map { it.id }?.filter { it != trackId } ?: emptyList()
        dao.setCardTracks(cardId, ids)
    }
}
