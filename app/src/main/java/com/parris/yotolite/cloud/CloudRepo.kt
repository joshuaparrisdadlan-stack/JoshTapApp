package com.parris.yotolite.cloud

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.parris.yotolite.data.AppDatabase
import com.parris.yotolite.data.CardEntity
import com.parris.yotolite.data.TrackEntity
import kotlinx.coroutines.tasks.await

/**
 * CloudRepo - simple Firestore-backed repository for cards and tracks.
 * Requires adding `google-services.json` to the app and firebase dependencies.
 */
class CloudRepo(private val context: Context) {
    private val TAG = "CloudRepo"
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    suspend fun signInAnonymously(): Boolean {
        return try {
            auth.signInAnonymously().await()
            true
        } catch (ex: Exception) {
            Log.e(TAG, "Anon sign-in failed", ex)
            false
        }
    }

    suspend fun uploadTrack(track: TrackEntity, localUri: Uri?): Boolean {
        try {
            val map = hashMapOf(
                "displayName" to track.displayName,
                "localUri" to track.localUri,
                "durationMs" to track.durationMs
            )
            val doc = firestore.collection("tracks").add(map).await()
            Log.d(TAG, "Uploaded track doc=${doc.id}")

            // Optionally upload file to storage if localUri provided
            if (localUri != null) {
                val ref = storage.reference.child("tracks/${doc.id}/${localUri.lastPathSegment}")
                context.contentResolver.openInputStream(localUri)?.use { stream ->
                    ref.putStream(stream).await()
                    val downloadUrl = ref.downloadUrl.await()
                    firestore.collection("tracks").document(doc.id).set(hashMapOf("remoteUrl" to downloadUrl.toString()), SetOptions.merge()).await()
                }
            }

            return true
        } catch (ex: Exception) {
            Log.e(TAG, "uploadTrack failed", ex)
            return false
        }
    }

    suspend fun uploadCard(card: CardEntity, trackIds: List<Long>): Boolean {
        try {
            val map = hashMapOf(
                "name" to card.name,
                "token" to card.token,
                "createdAt" to card.createdAt,
                "trackIds" to trackIds
            )
            firestore.collection("cards").add(map).await()
            return true
        } catch (ex: Exception) {
            Log.e(TAG, "uploadCard failed", ex)
            return false
        }
    }

    suspend fun syncLocalToCloud(db: AppDatabase): Boolean {
        try {
            // Simple sync: upload all tracks and cards (no conflict resolution)
            val dao = db.appDao()
            val tracks = dao.listTracks()
            val cards = dao.listCards()

            // ensure auth
            if (auth.currentUser == null) {
                val ok = signInAnonymously()
                if (!ok) return false
            }

            // Upload tracks first and collect mapping localId -> remoteDocId
            val trackIdToRemote = mutableMapOf<Long, String>()
            tracks.forEach { t ->
                // try to upload file if available
                val localUri = if (t.localUri.isNullOrEmpty()) null else Uri.parse(t.localUri)
                val success = uploadTrack(t, localUri)
                // Note: uploadTrack currently does not return remote id; we query by unique fields after upload
                // For now we won't enforce mapping but leave the remote to store metadata and remoteUrl if available.
            }

            // Upload cards with track id references
            cards.forEach { c ->
                // fetch the card with tracks from DB to get ordered track ids
                val cw = dao.getCardWithTracksById(c.id)
                val trackIds = cw?.tracks?.map { it.id } ?: emptyList()
                uploadCard(c, trackIds)
            }

            return true
        } catch (ex: Exception) {
            Log.e(TAG, "syncLocalToCloud failed", ex)
            return false
        }
    }

    // Family sharing helpers (simple schema)
    suspend fun createFamily(ownerUid: String, familyName: String): String? {
        return try {
            val map = hashMapOf("owner" to ownerUid, "name" to familyName)
            val doc = firestore.collection("families").add(map).await()
            doc.id
        } catch (ex: Exception) {
            Log.e(TAG, "createFamily failed", ex)
            null
        }
    }

    suspend fun inviteToFamily(familyId: String, email: String): Boolean {
        return try {
            val map = hashMapOf("familyId" to familyId, "email" to email, "createdAt" to System.currentTimeMillis())
            firestore.collection("familyInvites").add(map).await()
            true
        } catch (ex: Exception) {
            Log.e(TAG, "inviteToFamily failed", ex)
            false
        }
    }

    suspend fun joinFamily(userUid: String, familyId: String): Boolean {
        return try {
            val map = hashMapOf("memberUid" to userUid, "joinedAt" to System.currentTimeMillis())
            firestore.collection("families").document(familyId).collection("members").add(map).await()
            true
        } catch (ex: Exception) {
            Log.e(TAG, "joinFamily failed", ex)
            false
        }
    }
}
