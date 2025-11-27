package com.parris.yotolite.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var db: AppDatabase
    private lateinit var dao: AppDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.appDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testInsertTrack() = runBlocking {
        val track = TrackEntity(
            displayName = "Test Track",
            localUri = "file:///path/to/audio.wav",
            durationMs = 5000L
        )

        val id = dao.insertTrack(track)
        assertTrue(id > 0)
    }

    @Test
    fun testListTracks() = runBlocking {
        val track1 = TrackEntity(displayName = "Track 1", localUri = "file:///1.wav")
        val track2 = TrackEntity(displayName = "Track 2", localUri = "file:///2.wav")

        dao.insertTrack(track1)
        dao.insertTrack(track2)

        val tracks = dao.listTracks()
        assertEquals(2, tracks.size)
    }

    @Test
    fun testInsertCard() = runBlocking {
        val card = CardEntity(
            name = "My Card",
            token = "abc123xyz"
        )

        val id = dao.insertCard(card)
        assertTrue(id > 0)
    }

    @Test
    fun testListCards() = runBlocking {
        val card1 = CardEntity(name = "Card 1", token = "token1")
        val card2 = CardEntity(name = "Card 2", token = "token2")

        dao.insertCard(card1)
        dao.insertCard(card2)

        val cards = dao.listCards()
        assertEquals(2, cards.size)
    }

    @Test
    fun testSetCardTracks() = runBlocking {
        val track = TrackEntity(displayName = "Track", localUri = "file:///track.wav")
        val trackId = dao.insertTrack(track)

        val card = CardEntity(name = "Card", token = "mytoken")
        val cardId = dao.insertCard(card)

        dao.setCardTracks(cardId, listOf(trackId))

        val cardWithTracks = dao.getCardWithTracksByToken("mytoken")
        assertNotNull(cardWithTracks)
        assertEquals(1, cardWithTracks?.tracks?.size)
        assertEquals(trackId, cardWithTracks?.tracks?.get(0)?.id)
    }

    @Test
    fun testGetCardWithTracksByToken() = runBlocking {
        val track1 = TrackEntity(displayName = "Track 1", localUri = "file:///1.wav")
        val track2 = TrackEntity(displayName = "Track 2", localUri = "file:///2.wav")
        val id1 = dao.insertTrack(track1)
        val id2 = dao.insertTrack(track2)

        val card = CardEntity(name = "My Card", token = "uniquetoken123")
        val cardId = dao.insertCard(card)

        dao.setCardTracks(cardId, listOf(id1, id2))

        val result = dao.getCardWithTracksByToken("uniquetoken123")
        assertNotNull(result)
        assertEquals("My Card", result?.card?.name)
        assertEquals(2, result?.tracks?.size)
    }

    @Test
    fun testGetCardWithTracksByToken_NotFound() = runBlocking {
        val result = dao.getCardWithTracksByToken("nonexistent")
        assertNull(result)
    }

    @Test
    fun testCardTrackJoin() = runBlocking {
        val track = TrackEntity(displayName = "Track", localUri = "file:///track.wav")
        val trackId = dao.insertTrack(track)

        val card = CardEntity(name = "Card", token = "token")
        val cardId = dao.insertCard(card)

        val join = CardTrackJoin(cardId = cardId, trackId = trackId, position = 0)
        dao.insertJoin(join)

        val cardWithTracks = dao.getCardWithTracksByToken("token")
        assertNotNull(cardWithTracks)
        assertEquals(1, cardWithTracks?.tracks?.size)
    }

    @Test
    fun testDeleteJoinsForCard() = runBlocking {
        val track = TrackEntity(displayName = "Track", localUri = "file:///track.wav")
        val trackId = dao.insertTrack(track)

        val card = CardEntity(name = "Card", token = "token")
        val cardId = dao.insertCard(card)

        dao.setCardTracks(cardId, listOf(trackId))

        var cardWithTracks = dao.getCardWithTracksByToken("token")
        assertEquals(1, cardWithTracks?.tracks?.size)

        dao.deleteJoinsForCard(cardId)

        cardWithTracks = dao.getCardWithTracksByToken("token")
        assertEquals(0, cardWithTracks?.tracks?.size)
    }
}
