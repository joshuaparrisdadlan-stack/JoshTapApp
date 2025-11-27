Privacy & Data Handling

This project collects minimal data for the MVP. Use this doc as a basis for store listing and privacy policies.

- What we collect:
  - Firebase Auth uid (anonymous or email) for account mapping
  - Card and track metadata stored in Firestore
  - Optional audio files uploaded to Firebase Storage
- How we use it:
  - Store user-owned card & track metadata and provide family sharing
  - Diagnostic logs (AppLog) for troubleshooting with user consent

Recommendations:
- Use explicit consent screens before uploading any personal data
- Provide a clear privacy policy URL in the Play Store listing
- Remove any PII from logs before sending
