# Troubleshooting — Common Issues

NFC not working
- Ensure NFC is enabled in system Settings.
- Use `adb logcat` or Android Studio logcat to view tag read errors.
- If tag is not NDEF formatted, use write flow to format (if supported) or use a writable NTAG215.

App crashes on start
- Check `logcat` for stack traces. Run `.\gradlew.bat assembleDebug` locally and inspect logs.

Tests failing
- Run unit tests:
  ```powershell
  .\gradlew.bat testDebugUnitTest --no-daemon
  ```

Build warnings
- Some Kotlin deprecations (e.g., `launchWhenStarted`) are present; they are non-blocking and should be addressed in follow-up cleanup.
