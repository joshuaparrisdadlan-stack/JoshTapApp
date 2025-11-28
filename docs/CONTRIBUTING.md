# Contributing

Please follow these guidelines when contributing changes:

- Run builds and tests before creating PRs:
  ```powershell
  .\gradlew.bat clean :app:assembleDebug testDebugUnitTest --no-daemon
  ```
- Commit messages: use conventional-style prefixes (e.g., `feat:`, `fix:`, `chore:`, `docs:`).
- Branches: create feature branches, then open a PR targeting `main`.
- Do not commit generated files or build artifacts. Keep `.gitignore` intact.
