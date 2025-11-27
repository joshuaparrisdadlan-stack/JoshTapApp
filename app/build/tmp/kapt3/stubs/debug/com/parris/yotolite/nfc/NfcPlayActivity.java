package com.parris.yotolite.nfc;

/**
 * NfcPlayActivity handles NFC read mode.
 * When an NFC tag is scanned, it extracts the token, retrieves the card + tracks,
 * and plays the audio.
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0012\u0010\u000e\u001a\u00020\u000b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0014J\u0010\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0014J\b\u0010\u0012\u001a\u00020\u000bH\u0014J\b\u0010\u0013\u001a\u00020\u000bH\u0014J\u0019\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u0016H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0018"}, d2 = {"Lcom/parris/yotolite/nfc/NfcPlayActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "btnBackHome", "Landroid/widget/Button;", "btnRetry", "lastProcessedToken", "", "tvStatus", "Landroid/widget/TextView;", "handleIntent", "", "intent", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onNewIntent", "onPause", "onResume", "processNfcTag", "tag", "Landroid/nfc/Tag;", "(Landroid/nfc/Tag;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class NfcPlayActivity extends androidx.appcompat.app.AppCompatActivity {
    private android.widget.TextView tvStatus;
    private android.widget.Button btnRetry;
    private android.widget.Button btnBackHome;
    @org.jetbrains.annotations.Nullable
    private java.lang.String lastProcessedToken;
    
    public NfcPlayActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override
    protected void onResume() {
    }
    
    @java.lang.Override
    protected void onPause() {
    }
    
    @java.lang.Override
    protected void onNewIntent(@org.jetbrains.annotations.NotNull
    android.content.Intent intent) {
    }
    
    private final void handleIntent(android.content.Intent intent) {
    }
    
    private final java.lang.Object processNfcTag(android.nfc.Tag tag, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}