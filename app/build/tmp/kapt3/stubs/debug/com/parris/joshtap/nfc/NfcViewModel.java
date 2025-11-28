package com.parris.joshtap.nfc;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\rJ\u0006\u0010\u0011\u001a\u00020\rJ\u000e\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0014R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0015"}, d2 = {"Lcom/parris/joshtap/nfc/NfcViewModel;", "Landroidx/lifecycle/ViewModel;", "repo", "Lcom/parris/joshtap/data/AppRepository;", "(Lcom/parris/joshtap/data/AppRepository;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/parris/joshtap/nfc/NfcUiState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "onTag", "", "tag", "Landroid/nfc/Tag;", "reset", "startScan", "startWrite", "cardId", "", "app_debug"})
public final class NfcViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.parris.joshtap.data.AppRepository repo = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.parris.joshtap.nfc.NfcUiState> _state = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.parris.joshtap.nfc.NfcUiState> state = null;
    
    public NfcViewModel(@org.jetbrains.annotations.NotNull
    com.parris.joshtap.data.AppRepository repo) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.parris.joshtap.nfc.NfcUiState> getState() {
        return null;
    }
    
    public final void startWrite(long cardId) {
    }
    
    public final void startScan() {
    }
    
    public final void reset() {
    }
    
    public final void onTag(@org.jetbrains.annotations.NotNull
    android.nfc.Tag tag) {
    }
}