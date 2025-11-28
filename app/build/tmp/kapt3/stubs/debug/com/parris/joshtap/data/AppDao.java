package com.parris.joshtap.data;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u001b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u001b\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\fJ\u001b\u0010\r\u001a\u0004\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\fJ\u0019\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J\u0019\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0014H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015J\u0019\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0018H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019J\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00100\u001bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001cJ\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00180\u001bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001cJ\'\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00050\u001bH\u0097@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010 \u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006!"}, d2 = {"Lcom/parris/joshtap/data/AppDao;", "", "deleteJoinsForCard", "", "cardId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCardWithTracksById", "Lcom/parris/joshtap/data/CardWithTracks;", "getCardWithTracksByNfcToken", "token", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCardWithTracksByToken", "insertCard", "card", "Lcom/parris/joshtap/data/CardEntity;", "(Lcom/parris/joshtap/data/CardEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertJoin", "join", "Lcom/parris/joshtap/data/CardTrackJoin;", "(Lcom/parris/joshtap/data/CardTrackJoin;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertTrack", "track", "Lcom/parris/joshtap/data/TrackEntity;", "(Lcom/parris/joshtap/data/TrackEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "listCards", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "listTracks", "setCardTracks", "orderedTrackIds", "(JLjava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao
public abstract interface AppDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertTrack(@org.jetbrains.annotations.NotNull
    com.parris.joshtap.data.TrackEntity track, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM tracks ORDER BY display_name ASC")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object listTracks(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.parris.joshtap.data.TrackEntity>> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertCard(@org.jetbrains.annotations.NotNull
    com.parris.joshtap.data.CardEntity card, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM cards ORDER BY created_at DESC")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object listCards(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.parris.joshtap.data.CardEntity>> $completion);
    
    @androidx.room.Transaction
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object setCardTracks(long cardId, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.Long> orderedTrackIds, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM card_track_join WHERE card_id = :cardId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteJoinsForCard(long cardId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertJoin(@org.jetbrains.annotations.NotNull
    com.parris.joshtap.data.CardTrackJoin join, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Transaction
    @androidx.room.Query(value = "SELECT * FROM cards WHERE token = :token LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getCardWithTracksByToken(@org.jetbrains.annotations.NotNull
    java.lang.String token, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.parris.joshtap.data.CardWithTracks> $completion);
    
    @androidx.room.Transaction
    @androidx.room.Query(value = "SELECT * FROM cards WHERE nfc_token = :token LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getCardWithTracksByNfcToken(@org.jetbrains.annotations.NotNull
    java.lang.String token, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.parris.joshtap.data.CardWithTracks> $completion);
    
    @androidx.room.Transaction
    @androidx.room.Query(value = "SELECT * FROM cards WHERE id = :cardId LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getCardWithTracksById(long cardId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.parris.joshtap.data.CardWithTracks> $completion);
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
        
        @androidx.room.Transaction
        @org.jetbrains.annotations.Nullable
        public static java.lang.Object setCardTracks(@org.jetbrains.annotations.NotNull
        com.parris.joshtap.data.AppDao $this, long cardId, @org.jetbrains.annotations.NotNull
        java.util.List<java.lang.Long> orderedTrackIds, @org.jetbrains.annotations.NotNull
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
    }
}