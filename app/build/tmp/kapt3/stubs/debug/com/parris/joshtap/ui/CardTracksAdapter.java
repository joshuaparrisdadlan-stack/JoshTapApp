package com.parris.joshtap.ui;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\"BK\u0012\u000e\b\u0002\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u001c\b\u0002\u0010\u0006\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0018\u00010\u0007\u0012\u0016\b\u0002\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\t\u0018\u00010\u000b\u00a2\u0006\u0002\u0010\rJ\b\u0010\u000e\u001a\u00020\bH\u0016J\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010J\u0016\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u0005J\u0016\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\bJ\u001c\u0010\u0018\u001a\u00020\t2\n\u0010\u0019\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u001a\u001a\u00020\bH\u0016J\u001c\u0010\u001b\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\bH\u0016J\u000e\u0010\u001f\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\bJ\u0014\u0010 \u001a\u00020\t2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050\u0010R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\"\u0010\u0006\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t\u0018\u00010\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\t\u0018\u00010\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/parris/joshtap/ui/CardTracksAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/parris/joshtap/ui/CardTracksAdapter$VH;", "items", "", "Lcom/parris/joshtap/data/TrackEntity;", "onRemoveClick", "Lkotlin/Function2;", "", "", "onStartDrag", "Lkotlin/Function1;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "(Ljava/util/List;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;)V", "getItemCount", "getOrderedIds", "", "", "insertAt", "index", "track", "moveItem", "from", "to", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "removeAt", "submitList", "list", "VH", "app_debug"})
public final class CardTracksAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.parris.joshtap.ui.CardTracksAdapter.VH> {
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.parris.joshtap.data.TrackEntity> items = null;
    @org.jetbrains.annotations.Nullable
    private final kotlin.jvm.functions.Function2<com.parris.joshtap.data.TrackEntity, java.lang.Integer, kotlin.Unit> onRemoveClick = null;
    @org.jetbrains.annotations.Nullable
    private final kotlin.jvm.functions.Function1<androidx.recyclerview.widget.RecyclerView.ViewHolder, kotlin.Unit> onStartDrag = null;
    
    public CardTracksAdapter(@org.jetbrains.annotations.NotNull
    java.util.List<com.parris.joshtap.data.TrackEntity> items, @org.jetbrains.annotations.Nullable
    kotlin.jvm.functions.Function2<? super com.parris.joshtap.data.TrackEntity, ? super java.lang.Integer, kotlin.Unit> onRemoveClick, @org.jetbrains.annotations.Nullable
    kotlin.jvm.functions.Function1<? super androidx.recyclerview.widget.RecyclerView.ViewHolder, kotlin.Unit> onStartDrag) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.parris.joshtap.ui.CardTracksAdapter.VH onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.parris.joshtap.ui.CardTracksAdapter.VH holder, int position) {
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    public final void submitList(@org.jetbrains.annotations.NotNull
    java.util.List<com.parris.joshtap.data.TrackEntity> list) {
    }
    
    public final void removeAt(int index) {
    }
    
    public final void insertAt(int index, @org.jetbrains.annotations.NotNull
    com.parris.joshtap.data.TrackEntity track) {
    }
    
    public final void moveItem(int from, int to) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.Long> getOrderedIds() {
        return null;
    }
    
    public CardTracksAdapter() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0011"}, d2 = {"Lcom/parris/joshtap/ui/CardTracksAdapter$VH;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/parris/joshtap/ui/CardTracksAdapter;Landroid/view/View;)V", "btnRemove", "Landroid/widget/ImageButton;", "getBtnRemove", "()Landroid/widget/ImageButton;", "dragHandle", "Landroid/widget/ImageView;", "getDragHandle", "()Landroid/widget/ImageView;", "tvTitle", "Landroid/widget/TextView;", "getTvTitle", "()Landroid/widget/TextView;", "app_debug"})
    public final class VH extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView tvTitle = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.ImageButton btnRemove = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.ImageView dragHandle = null;
        
        public VH(@org.jetbrains.annotations.NotNull
        android.view.View itemView) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getTvTitle() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.ImageButton getBtnRemove() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.ImageView getDragHandle() {
            return null;
        }
    }
}