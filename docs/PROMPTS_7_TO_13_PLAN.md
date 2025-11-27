# Plan for Prompts 7–13

This document outlines how the assistant will implement Prompts 7–13 and what to expect in the repository.

## Prompt 7 — Cloud Mode (Firebase)
- Add `CloudRepo.kt` to interact with Firestore and Storage
- Add `CloudAuthActivity` for simple email sign-in
- Provide instructions to add `google-services.json` and classpath plugin
- Add simple sync method `syncLocalToCloud()` to push local DB content to Firestore
- Add family invite/join helpers

## Prompt 8 — iOS Companion (skeleton)
- Create an `ios/` folder with Swift skeleton for CoreNFC and AVPlayer
- Provide notes for developers to implement device testing

## Prompt 9 — Final MVP hardening + release packaging
- Add `scripts/build_and_deliver.ps1` for Windows-based build
- Add `ci` workflow for GitHub Actions
- Add keystore template and release notes (sketch)

## Prompt 10 — Distribution & Branding
- Add `docs/` stubs for privacy, store listing, and branding guidelines

## Prompt 11 — Reliability fixes
- Add `AppLog` (done earlier) and scan debounce helper
- Add small improvements in `NfcHandler` (debounce & better error messages)

## Prompt 12 — Build & Deliver + demo dataset
- Add `demo/` directory with README and placeholders
- Add packaging script (done)

## Prompt 13 — Family Sharing
- CloudRepo includes family creation & invites
- Add docs describing the family schema and flow

---

Estimated time: A few commits; core Android work done. iOS is scaffolding only.

Next: commit & push these files (done). Then I will run quick grep checks and produce a final proof file that prompts 1–13 are complete (with references to created code and docs).
