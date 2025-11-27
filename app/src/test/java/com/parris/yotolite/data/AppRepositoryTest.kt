package com.parris.yotolite.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.robolectric.RobolectricTestRunner
import org.junit.runner.RunWith

@RunWith(RobolectricTestRunner::class)
class AppRepositoryTest {
    private lateinit var db: AppDatabase
    private lateinit var repo: AppRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repo = AppRepository(db)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun addTrackToCard_insertsJoin() = runBlocking {
        val trackId = repo.insertTrack("Test Track", "content://test/1")
        val cardId = repo.insertCard("Test Card", "tokentest")

        // add track to card
        repo.addTrackToCard(cardId, trackId)

        val cardWith = repo.getCardWithTracksById(cardId)
        assertNotNull(cardWith)
        assertEquals(1, cardWith!!.tracks.size)
        assertEquals(trackId, cardWith.tracks[0].id)
    }
}
