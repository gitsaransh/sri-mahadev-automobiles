# ReelsReward Backend 🚀

Phase 1 MVP Backend for India-first rewarded ads app.

## Done Checklist (Hour 4)
- [x] Express scaffold with CORS and JSON support.
- [x] Firebase Admin SDK Integration.
- [x] **RR-06:** Auth Logic (token verification + profile create).
- [x] **RR-07:** Profile & Balance Dashboard APIs.
- [x] **RR-08:** Points Ledger & Video Reward Transaction.
- [x] **RR-10/11/12:** Duel Engine (Invites & Bidirectional Streaks).

## Getting Started
1. Place `serviceAccountKey.json` in this folder.
2. Run `cp .env.example .env` and update the database URL.
3. Run `node index.js`.

## API Endpoints
- `GET /health` - Check if server is alive.
- `POST /api/auth/verify-otp` - Register/Login user.
- `GET /api/user/profile` - Get user data.
- `POST /api/video/watch-complete` - Credit 10 points.
- `POST /api/duel/invite` - Challenge a friend.
- `POST /api/duel/accept` - Accept a challenge and start streak.
