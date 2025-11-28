# Firebase Setup (if used)

This project can optionally use Firebase for Cloud Mode (Auth / Firestore / Storage). If you plan to enable Cloud Mode, follow these steps.

1) Create a Firebase project in the console.
2) Enable Firestore and Storage.
3) Enable Authentication (Anonymous or Email/Password).
4) Add Android app in Firebase console with package `com.parris.joshtap` and download `google-services.json` into `app/` (do NOT commit this file).

Security rules and indexing must be configured in the console. See `docs/RELEASE.md` and the in-repo `/firebase/` folder if present for rule templates.
