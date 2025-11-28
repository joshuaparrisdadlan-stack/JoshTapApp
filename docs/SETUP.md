# Setup & Local Development

Run these commands from the project root `C:\Users\joshu\Downloads\Projects\ParrisTapApp\JoshTapApp` in PowerShell.

Prerequisites
- JDK 17
- Android SDK (matching compileSdk / build tools in `app/build.gradle`)
- `adb` on PATH for device installs

Build and test (PowerShell)
```powershell
.\gradlew.bat clean :app:assembleDebug testDebugUnitTest --no-daemon --stacktrace
```

Install to device
```powershell
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

Run unit tests
```powershell
.\gradlew.bat testDebugUnitTest --no-daemon
```

Useful paths
- APK: `app\build\outputs\apk\debug\app-debug.apk`
- Unit test reports: `app\build\reports\tests\testDebugUnitTest/`

Notes
- Always run commands from repo root.
- The repo uses package `com.parris.joshtap` and branding `JoshTap` / `JoshTapApp`.
