Firebase setup for JoshTapApp

1) Create a Firebase project in the Firebase console: https://console.firebase.google.com/
2) Add an Android app to the project with package name `com.parris.joshtap`.
3) Download the `google-services.json` file and place it at:
   - `app/google-services.json`
4) In the Firebase console enable the following products:
   - Authentication (Email/password or Anonymous as desired)
   - Firestore
   - Storage
5) When building locally, ensure you have network connectivity; Gradle will resolve the Firebase BOM and KTX libs.
6) Optional: add SHA-1 to Firebase app settings for some auth providers.

Notes:
- Root `build.gradle` already includes the `com.google.gms:google-services` classpath.
- App module applies the `com.google.gms.google-services` plugin and includes the Firebase BOM and KTX dependencies.

Security:
- Before shipping to users, add Firestore security rules to restrict reads/writes to authorized user/family scopes.
- Use Firebase App Check for production hardening.
