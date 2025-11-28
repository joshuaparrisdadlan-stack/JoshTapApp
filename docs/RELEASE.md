# Release Guide (Play Store - 2025)

This file summarizes the steps to prepare and publish an Android release (internal testing / Play Console).

1) Prepare release build

```powershell
.\gradlew.bat clean :app:bundleRelease --no-daemon --stacktrace
# or build APK for sideload
.\gradlew.bat clean :app:assembleRelease --no-daemon --stacktrace
```

2) Signing
- Use `keystore.properties` (ignored by git) with keys or environment variables.
- Configure signing in `app/build.gradle` as usual.

3) AAB vs APK
- For Play Store internal testing, upload the generated AAB (`app\build\outputs\bundle\release\app-release.aab`).

4) Play Console notes (2025)
- Target `targetSdk` should match gradle config (35).
- Use Play App Signing (recommended).
- Provide privacy policy and contact email if using Firebase or cloud features.

5) Smoke tests
- Install generated artifact on device and run acceptance checklist (see `docs/TROUBLESHOOTING.md`).
