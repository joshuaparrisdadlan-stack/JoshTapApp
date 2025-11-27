package com.parris.yotolite.backup

import android.content.Context
import android.net.Uri
import com.parris.yotolite.util.AppLog
import com.parris.yotolite.data.AppDatabase
import com.parris.yotolite.data.CardEntity
import com.parris.yotolite.data.CardTrackJoin
import com.parris.yotolite.data.TrackEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * BackupManager handles export and import of card + track data.
 * Exports to ZIP format containing JSON metadata.
 */
object BackupManager {
    private const val TAG = "BackupManager"
    private const val METADATA_FILE = "metadata.json"

    data class BackupData(
        val tracks: List<TrackEntity>,
        val cards: List<CardEntity>,
        val cardTrackJoins: List<CardTrackJoin>
    )

    /**
     * Exports all cards and tracks to a ZIP file in app's cache directory.
     */
    suspend fun exportToZip(context: Context, db: AppDatabase): Uri? = withContext(Dispatchers.IO) {
        try {
            val dao = db.appDao()
            val tracks = dao.listTracks()
            val cards = dao.listCards()
            val joins = emptyList<CardTrackJoin>() // Note: could fetch via query if needed

            val data = BackupData(tracks, cards, joins)
            val jsonData = convertToJson(data)

            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
            val zipFile = File(context.cacheDir, "yotolite_backup_$timestamp.zip")

            ZipOutputStream(zipFile.outputStream()).use { zos ->
                val entry = ZipEntry(METADATA_FILE)
                zos.putNextEntry(entry)
                zos.write(jsonData.toByteArray(Charsets.UTF_8))
                zos.closeEntry()
            }

            AppLog.d(TAG, "Export completed: ${zipFile.absolutePath}")
            Uri.fromFile(zipFile)
        } catch (ex: Exception) {
            AppLog.e(TAG, "Export failed", ex)
            null
        }
    }

    /**
     * Imports cards and tracks from a ZIP backup file.
     */
    suspend fun importFromZip(context: Context, zipUri: Uri, db: AppDatabase): Boolean = withContext(Dispatchers.IO) {
        try {
            val inputStream = context.contentResolver.openInputStream(zipUri)
                ?: throw IllegalArgumentException("Cannot open zip file")

            val tempFile = File(context.cacheDir, "temp_backup.zip")
            tempFile.outputStream().use { out ->
                inputStream.copyTo(out)
            }

            val data = ZipFile(tempFile).use { zf ->
                val entry = zf.getEntry(METADATA_FILE)
                    ?: throw IllegalArgumentException("No metadata.json in backup")

                val json = zf.getInputStream(entry)
                    .bufferedReader(Charsets.UTF_8)
                    .use { it.readText() }

                parseJson(json)
            }

            val dao = db.appDao()

            // Insert tracks
            for (track in data.tracks) {
                dao.insertTrack(track)
            }

            // Insert cards
            for (card in data.cards) {
                dao.insertCard(card)
            }

            tempFile.delete()
            AppLog.d(TAG, "Import completed: ${data.tracks.size} tracks, ${data.cards.size} cards")
            true
        } catch (ex: Exception) {
            AppLog.e(TAG, "Import failed", ex)
            false
        }
    }

    private fun convertToJson(data: BackupData): String {
        val root = JSONObject()

        val tracksArray = JSONArray()
        for (track in data.tracks) {
            val obj = JSONObject()
            obj.put("id", track.id)
            obj.put("displayName", track.displayName)
            obj.put("localUri", track.localUri)
            obj.put("durationMs", track.durationMs)
            tracksArray.put(obj)
        }

        val cardsArray = JSONArray()
        for (card in data.cards) {
            val obj = JSONObject()
            obj.put("id", card.id)
            obj.put("name", card.name)
            obj.put("token", card.token)
            obj.put("createdAt", card.createdAt)
            cardsArray.put(obj)
        }

        root.put("tracks", tracksArray)
        root.put("cards", cardsArray)
        root.put("exportDate", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date()))
        root.put("version", 1)

        return root.toString(2)
    }

    private fun parseJson(json: String): BackupData {
        val root = JSONObject(json)

        val tracks = mutableListOf<TrackEntity>()
        val tracksArray = root.getJSONArray("tracks")
        for (i in 0 until tracksArray.length()) {
            val obj = tracksArray.getJSONObject(i)
            tracks.add(
                TrackEntity(
                    id = obj.getLong("id"),
                    displayName = obj.getString("displayName"),
                    localUri = obj.getString("localUri"),
                    durationMs = obj.getLong("durationMs")
                )
            )
        }

        val cards = mutableListOf<CardEntity>()
        val cardsArray = root.getJSONArray("cards")
        for (i in 0 until cardsArray.length()) {
            val obj = cardsArray.getJSONObject(i)
            cards.add(
                CardEntity(
                    id = obj.getLong("id"),
                    name = obj.getString("name"),
                    token = obj.getString("token"),
                    createdAt = obj.getLong("createdAt")
                )
            )
        }

        return BackupData(tracks, cards, emptyList())
    }
}
