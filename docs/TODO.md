# YotoLiteMVP Progress & TODO (mapped to prompts)

This file mirrors the in-memory TODO list and maps each item to the original prompt numbers (1–13). It is intended to live in the repo so you can review progress without the assistant tool.

- [x] Prompt 1 — Scaffold Android project
  - Status: Completed — project skeleton, Gradle files, `MainActivity`, three fragments, layouts, `README.md`, and VS Code tasks.
  - Files: `settings.gradle`, `build.gradle`, `app/build.gradle`, `app/src/main/` (manifests, layouts, sources).

- [~] Prompt 2 — Wire Room DB & basic UI
  - Status: In progress
  - Work done:
    - Room entities: `TrackEntity`, `CardEntity`, `CardTrackJoin`
    - DAO: `AppDao`
    - Database: `AppDatabase`
    - Repository: `AppRepository`
    - Application singleton: `MyApplication`
    - Basic fragments & layouts: `PlayFragment`, `LibraryFragment`, `CardsFragment`
    - `PlayerController` stub
  - Next actions:
    - Add `ViewModel`s for Library/Cards — DONE (`LibraryViewModel.kt`, `CardsViewModel.kt`)
    - Hook demo import (`btnImport`) and create-card (`btnCreateCard`) to the repository using coroutines — DONE (implemented in `LibraryFragment.kt` and `CardsFragment.kt`)
    - Add demo audio or tone generator and wire basic playback — DONE (`AudioRepo.kt`, `PlayerController.kt` wired to ExoPlayer)
    - Remaining small tasks:
      - Replace remaining raw `Thread` usage in `CardsFragment` with coroutine/ViewModel flows
      - Add visible lists for Tracks and Cards in the Library/Cards UI
      - Add player strip in `PlayFragment` showing current title/playback controls

- [ ] Prompt 3 — NFC read & write (NDEF URI)
  - Status: Not started
  - Tasks: `NfcHandler.kt`, foreground dispatch, token parser, play/write UI flows, debug screen.

- [ ] Prompt 4 — Media3 ExoPlayer + player UI
  - Status: Not started
  - Tasks: Wire ExoPlayer, background playback/MediaSession, player UI controls, notification handling.

- [ ] Prompt 5 — Onboarding, permissions, export/import
  - Status: Not started

- [ ] Prompt 6 — QA hardening & automated tests
  - Status: Not started

- [ ] Prompt 7 — Cloud Mode (Firebase)
  - Status: Not started

- [ ] Prompt 8 — iOS companion app (CoreNFC)
  - Status: Not started

- [ ] Prompt 9 — Final MVP hardening + release packaging
  - Status: Not started

- [ ] Prompt 10 — Distribution, privacy, branding
  - Status: Not started

- [ ] Prompt 11 — Last-mile reliability fixes
  - Status: Not started

- [ ] Prompt 12 — Build & Deliver + demo dataset
  - Status: Not started

- [ ] Prompt 13 — Family Sharing (Firestore families + invites)
  - Status: Not started

---

Notes:
- The assistant-managed TODO was created in the session state; this file mirrors it so the project contains a persistent copy.
- If you want this file to be updated automatically after each change, I can update it programmatically when I make progress (confirm and I will).

Next recommended action: I can implement the `ViewModel`s and hook the demo import/create-card buttons now so you can test the DB and UI quickly. Reply `go` to proceed or tell me a different next step.
