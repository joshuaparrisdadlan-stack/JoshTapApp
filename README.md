# JoshTapApp

This repo contains the JoshTapApp Android project scaffold. It provides a minimum starting point for the NFC-driven audio player app.

Quick build (from project root):

```bash
./gradlew :app:assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

What's in this scaffold:

- `app/` — Android app module with `MainActivity` and three fragments (Play, Library, Cards).
- Basic Gradle build files targeting SDK 35 and minSdk 24.
- `PlayerController.kt` stub (Media3 wiring will be added in next steps).
- `.vscode/tasks.json` (build and install tasks)

Next steps:

1. Wire Room database entities, DAO, and repository.
2. Implement Media3 playback and background service.
3. Add NFC read/write helpers.
