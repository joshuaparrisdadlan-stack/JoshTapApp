package com.parris.joshtap.nfc;

/**
 * NfcHandler manages NFC read and write operations for Type 2 tags.
 * - Read mode: Extracts token from NDEF URI and retrieves card + tracks
 * - Write mode: Encodes token as NDEF URI and writes to tag
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004H\u0002J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J=\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0012\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00150\u0015\u00a2\u0006\u0002\u0010\u0018J\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u000f\u001a\u00020\u0010J\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u001b\u0010\u001f\u001a\u0004\u0018\u00010\u00042\u0006\u0010 \u001a\u00020!H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\"J!\u0010#\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020!2\u0006\u0010\f\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010$R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006%"}, d2 = {"Lcom/parris/joshtap/nfc/NfcHandler;", "", "()V", "MIME_TYPE", "", "READ_DEBOUNCE_MS", "", "SCHEME", "TAG", "lastReadAt", "createUriRecord", "Landroid/nfc/NdefRecord;", "token", "disableForegroundDispatch", "", "context", "Landroid/content/Context;", "activity", "Landroid/app/Activity;", "enableForegroundDispatch", "intentFilter", "", "Landroid/content/IntentFilter;", "techList", "(Landroid/content/Context;Landroid/app/Activity;[Landroid/content/IntentFilter;[[Ljava/lang/String;)V", "isNfcAvailable", "", "isNfcSupported", "parseTokenFromNdef", "message", "Landroid/nfc/NdefMessage;", "readTokenFromTag", "tag", "Landroid/nfc/Tag;", "(Landroid/nfc/Tag;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "writeTokenToTag", "(Landroid/nfc/Tag;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class NfcHandler {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String TAG = "NfcHandler";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String SCHEME = "https://yotolite.app/play/";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String MIME_TYPE = "text/plain";
    private static long lastReadAt = 0L;
    private static final long READ_DEBOUNCE_MS = 1000L;
    @org.jetbrains.annotations.NotNull
    public static final com.parris.joshtap.nfc.NfcHandler INSTANCE = null;
    
    private NfcHandler() {
        super();
    }
    
    /**
     * Initializes NFC foreground dispatch for MainActivity.
     * Call from onCreate() and onResume().
     */
    public final void enableForegroundDispatch(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    android.app.Activity activity, @org.jetbrains.annotations.NotNull
    android.content.IntentFilter[] intentFilter, @org.jetbrains.annotations.NotNull
    java.lang.String[][] techList) {
    }
    
    /**
     * Disables NFC foreground dispatch for MainActivity.
     * Call from onPause().
     */
    public final void disableForegroundDispatch(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    android.app.Activity activity) {
    }
    
    /**
     * Parses token from an NFC tag's NDEF message.
     * Extracts from URI record: "https://yotolite.app/play/{token}"
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object readTokenFromTag(@org.jetbrains.annotations.NotNull
    android.nfc.Tag tag, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    /**
     * Writes token to an NFC tag using NDEF format.
     * Encodes as URI record: "https://yotolite.app/play/{token}"
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object writeTokenToTag(@org.jetbrains.annotations.NotNull
    android.nfc.Tag tag, @org.jetbrains.annotations.NotNull
    java.lang.String token, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Parses token from NDEF message.
     * Looks for URI record with scheme "https://yotolite.app/play/{token}"
     */
    private final java.lang.String parseTokenFromNdef(android.nfc.NdefMessage message) {
        return null;
    }
    
    /**
     * Creates an NDEF URI record with the token.
     * URI format: "https://yotolite.app/play/{token}"
     */
    private final android.nfc.NdefRecord createUriRecord(java.lang.String token) {
        return null;
    }
    
    /**
     * Checks if NFC is available on device and enabled.
     */
    public final boolean isNfcAvailable(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return false;
    }
    
    /**
     * Checks if device supports NFC hardware.
     */
    public final boolean isNfcSupported(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return false;
    }
}