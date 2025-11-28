# Architecture Overview

High-level flow

- NFC scan/write → `NfcHandler` (low-level NDEF) → `NfcViewModel` (UI-safe state) → `AppRepository` → `AppDatabase` (Room)
- Playback: `PlayerController` (Media3 ExoPlayer) runs playlists supplied by repository.

Key components
- `NfcViewModel` — central state for scanning and write flows.
- `NfcHandler.kt` — read/write helpers using `Ndef`/`NdefFormatable`.
- `AppDatabase` / `AppDao` / Entities — Room schema (v2 includes `nfcToken` column).
- `AppRepository` — single source for DB operations and token generation.
- `PlayerController` — singleton ExoPlayer wrapper exposing StateFlow for playback state.

Design notes
- Keep NFC operations off the main thread and surface results through `ViewModel` + `StateFlow`.
- Use Room migrations for schema changes (v1 → v2 migration already present).
