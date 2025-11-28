Release Guide — JoshTapApp

This guide covers producing signed release APKs and basic hardening steps before publishing.

1) Keystore and signing
- Create a keystore (example):

  keytool -genkeypair -v -storetype PKCS12 -keystore release-keystore.jks -alias joshtap -keyalg RSA -keysize 2048 -validity 9125

- Create `keystore.properties` at project root with these values:

  storeFile=release-keystore.jks
  storePassword=your_store_password
  keyAlias=joshtap
  keyPassword=your_key_password

- Update `app/build.gradle` signingConfigs if you add a release signing block.

2) Build
- Debug APK (quick):

  .\scripts\build_and_deliver.ps1 -flavor Debug

- Release APK (signed):

  .\scripts\build_and_deliver.ps1 -flavor Release

3) Hardening checklist (suggested)
- Enable Proguard/R8 rules and test obfuscated release build
- Ensure Firebase security rules are in place for Firestore and Storage
- Remove debug logging or gate it behind a runtime flag
- Validate token parsing end-to-end across Android/iOS

4) CI
- GitHub Actions workflow `android-ci.yml` is provided to build and run unit tests. Update SDK/Gradle versions as needed.

5) Publishing
- Prepare store listing, privacy policy URL, and assets (icons/screenshots)
- Upload completed app bundle to Google Play Console or distribute APK for testing
