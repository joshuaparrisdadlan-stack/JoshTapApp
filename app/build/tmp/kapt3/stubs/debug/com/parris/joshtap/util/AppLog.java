package com.parris.joshtap.util;

/**
 * AppLog: lightweight logger with an in-memory ring buffer for quick diagnostics.
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0006H\u0002J\u0018\u0010\u000f\u001a\u00020\r2\b\b\u0002\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006J$\u0010\u0012\u001a\u00020\r2\b\b\u0002\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00062\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0014J\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00060\u0016J\u0018\u0010\u0017\u001a\u00020\r2\b\b\u0002\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006J$\u0010\u0018\u001a\u00020\r2\b\b\u0002\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00062\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u0018\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\bX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/parris/joshtap/util/AppLog;", "", "()V", "MAX_ENTRIES", "", "TAG", "", "buffer", "", "[Ljava/lang/String;", "idx", "Ljava/util/concurrent/atomic/AtomicInteger;", "add", "", "entry", "d", "tag", "msg", "e", "t", "", "getBufferedLogs", "", "i", "w", "app_debug"})
public final class AppLog {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String TAG = "YotoLite";
    private static final int MAX_ENTRIES = 500;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String[] buffer = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.concurrent.atomic.AtomicInteger idx = null;
    @org.jetbrains.annotations.NotNull
    public static final com.parris.joshtap.util.AppLog INSTANCE = null;
    
    private AppLog() {
        super();
    }
    
    private final void add(java.lang.String entry) {
    }
    
    public final void d(@org.jetbrains.annotations.NotNull
    java.lang.String tag, @org.jetbrains.annotations.NotNull
    java.lang.String msg) {
    }
    
    public final void w(@org.jetbrains.annotations.NotNull
    java.lang.String tag, @org.jetbrains.annotations.NotNull
    java.lang.String msg, @org.jetbrains.annotations.Nullable
    java.lang.Throwable t) {
    }
    
    public final void e(@org.jetbrains.annotations.NotNull
    java.lang.String tag, @org.jetbrains.annotations.NotNull
    java.lang.String msg, @org.jetbrains.annotations.Nullable
    java.lang.Throwable t) {
    }
    
    public final void i(@org.jetbrains.annotations.NotNull
    java.lang.String tag, @org.jetbrains.annotations.NotNull
    java.lang.String msg) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getBufferedLogs() {
        return null;
    }
}