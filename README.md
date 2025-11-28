# JoshTapApp

This repo contains the JoshTapApp Android project scaffold. It provides a minimum starting point for the NFC-driven audio player app.

Quick build (from project root):

# JoshTapApp

This repository contains the JoshTap Android app: an NFC-driven audio player that writes and reads tokenized NDEF URIs and maps them to local playlists. The app package is `com.parris.joshtap`.

Primary pointers
- Docs: `docs/` (setup, architecture, release, troubleshooting)
- Build (Windows / PowerShell): `.\gradlew.bat clean :app:assembleDebug testDebugUnitTest --no-daemon`
- Install APK to device:
	```powershell
	adb install -r app\build\outputs\apk\debug\app-debug.apk
	```
- APK path: `app\build\outputs\apk\debug\app-debug.apk`

What this project contains
- `app/` — Android app module (Play, Library, Cards tabs).
- `app/src/main/java/com/parris/joshtap` — app source (NFC, DB, ViewModels, UI).
- Gradle wrapper and CI workflow for Android builds.

Quick verification checklist
- Build and tests: `.\gradlew.bat clean :app:assembleDebug testDebugUnitTest --no-daemon --stacktrace`
- Install: `adb install -r app\build\outputs\apk\debug\app-debug.apk`
- Smoke test: create demo tones → create card → write NTAG215 → tap to play.

If you want to jump into the docs, open `docs/DOCS_INDEX.md` which lists and describes each doc and whether it needs updates.

Contributing
- See `docs/CONTRIBUTING.md` for commit style, testing, and PR expectations.

License & ownership
- This repo is maintained by Josh. Keep branding references to `JoshTap` / `JoshTapApp` / `joshtap.app` only.
