package com.parris.joshtap;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001\u001cB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\u0012\u0010\u0016\u001a\u00020\u00152\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J\b\u0010\u0019\u001a\u00020\u0015H\u0002J\b\u0010\u001a\u001a\u00020\u0015H\u0002J\b\u0010\u001b\u001a\u00020\u0015H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/parris/joshtap/CardDetailActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/parris/joshtap/ui/CardTracksAdapter;", "btnAdd", "Landroid/widget/Button;", "btnSave", "cardId", "", "lastRemoved", "Lcom/parris/joshtap/CardDetailActivity$RemovedTrack;", "rvAssigned", "Landroidx/recyclerview/widget/RecyclerView;", "touchHelper", "Landroidx/recyclerview/widget/ItemTouchHelper;", "tvCardTitle", "Landroid/widget/TextView;", "tvCardToken", "tvTrackCount", "loadCard", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "saveAndFinish", "showAddDialog", "updateTrackCount", "RemovedTrack", "app_debug"})
public final class CardDetailActivity extends androidx.appcompat.app.AppCompatActivity {
    private long cardId = -1L;
    private android.widget.TextView tvCardTitle;
    private android.widget.TextView tvCardToken;
    private android.widget.TextView tvTrackCount;
    private androidx.recyclerview.widget.RecyclerView rvAssigned;
    private android.widget.Button btnAdd;
    private android.widget.Button btnSave;
    private com.parris.joshtap.ui.CardTracksAdapter adapter;
    private androidx.recyclerview.widget.ItemTouchHelper touchHelper;
    @org.jetbrains.annotations.Nullable
    private com.parris.joshtap.CardDetailActivity.RemovedTrack lastRemoved;
    
    public CardDetailActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loadCard() {
    }
    
    private final void updateTrackCount() {
    }
    
    private final void showAddDialog() {
    }
    
    private final void saveAndFinish() {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, d2 = {"Lcom/parris/joshtap/CardDetailActivity$RemovedTrack;", "", "track", "Lcom/parris/joshtap/data/TrackEntity;", "index", "", "(Lcom/parris/joshtap/data/TrackEntity;I)V", "getIndex", "()I", "getTrack", "()Lcom/parris/joshtap/data/TrackEntity;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
    static final class RemovedTrack {
        @org.jetbrains.annotations.NotNull
        private final com.parris.joshtap.data.TrackEntity track = null;
        private final int index = 0;
        
        public RemovedTrack(@org.jetbrains.annotations.NotNull
        com.parris.joshtap.data.TrackEntity track, int index) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.parris.joshtap.data.TrackEntity getTrack() {
            return null;
        }
        
        public final int getIndex() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.parris.joshtap.data.TrackEntity component1() {
            return null;
        }
        
        public final int component2() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.parris.joshtap.CardDetailActivity.RemovedTrack copy(@org.jetbrains.annotations.NotNull
        com.parris.joshtap.data.TrackEntity track, int index) {
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