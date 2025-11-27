package com.parris.yotolite.backup;

/**
 * BackupManager handles export and import of card + track data.
 * Exports to ZIP format containing JSON metadata.
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u0016B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0002J#\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ)\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013J\u0010\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0017"}, d2 = {"Lcom/parris/yotolite/backup/BackupManager;", "", "()V", "METADATA_FILE", "", "TAG", "convertToJson", "data", "Lcom/parris/yotolite/backup/BackupManager$BackupData;", "exportToZip", "Landroid/net/Uri;", "context", "Landroid/content/Context;", "db", "Lcom/parris/yotolite/data/AppDatabase;", "(Landroid/content/Context;Lcom/parris/yotolite/data/AppDatabase;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "importFromZip", "", "zipUri", "(Landroid/content/Context;Landroid/net/Uri;Lcom/parris/yotolite/data/AppDatabase;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "parseJson", "json", "BackupData", "app_debug"})
public final class BackupManager {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String TAG = "BackupManager";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String METADATA_FILE = "metadata.json";
    @org.jetbrains.annotations.NotNull
    public static final com.parris.yotolite.backup.BackupManager INSTANCE = null;
    
    private BackupManager() {
        super();
    }
    
    /**
     * Exports all cards and tracks to a ZIP file in app's cache directory.
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object exportToZip(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.parris.yotolite.data.AppDatabase db, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super android.net.Uri> $completion) {
        return null;
    }
    
    /**
     * Imports cards and tracks from a ZIP backup file.
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object importFromZip(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    android.net.Uri zipUri, @org.jetbrains.annotations.NotNull
    com.parris.yotolite.data.AppDatabase db, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    private final java.lang.String convertToJson(com.parris.yotolite.backup.BackupManager.BackupData data) {
        return null;
    }
    
    private final com.parris.yotolite.backup.BackupManager.BackupData parseJson(java.lang.String json) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u00a2\u0006\u0002\u0010\tJ\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0003J\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H\u00c6\u0003J9\u0010\u0011\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H\u00c6\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0015\u001a\u00020\u0016H\u00d6\u0001J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000b\u00a8\u0006\u0019"}, d2 = {"Lcom/parris/yotolite/backup/BackupManager$BackupData;", "", "tracks", "", "Lcom/parris/yotolite/data/TrackEntity;", "cards", "Lcom/parris/yotolite/data/CardEntity;", "cardTrackJoins", "Lcom/parris/yotolite/data/CardTrackJoin;", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getCardTrackJoins", "()Ljava/util/List;", "getCards", "getTracks", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
    public static final class BackupData {
        @org.jetbrains.annotations.NotNull
        private final java.util.List<com.parris.yotolite.data.TrackEntity> tracks = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<com.parris.yotolite.data.CardEntity> cards = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<com.parris.yotolite.data.CardTrackJoin> cardTrackJoins = null;
        
        public BackupData(@org.jetbrains.annotations.NotNull
        java.util.List<com.parris.yotolite.data.TrackEntity> tracks, @org.jetbrains.annotations.NotNull
        java.util.List<com.parris.yotolite.data.CardEntity> cards, @org.jetbrains.annotations.NotNull
        java.util.List<com.parris.yotolite.data.CardTrackJoin> cardTrackJoins) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.parris.yotolite.data.TrackEntity> getTracks() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.parris.yotolite.data.CardEntity> getCards() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.parris.yotolite.data.CardTrackJoin> getCardTrackJoins() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.parris.yotolite.data.TrackEntity> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.parris.yotolite.data.CardEntity> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<com.parris.yotolite.data.CardTrackJoin> component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.parris.yotolite.backup.BackupManager.BackupData copy(@org.jetbrains.annotations.NotNull
        java.util.List<com.parris.yotolite.data.TrackEntity> tracks, @org.jetbrains.annotations.NotNull
        java.util.List<com.parris.yotolite.data.CardEntity> cards, @org.jetbrains.annotations.NotNull
        java.util.List<com.parris.yotolite.data.CardTrackJoin> cardTrackJoins) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
}