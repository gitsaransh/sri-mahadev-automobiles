# ReelsReward Project Backlog 📂

This file tracks external dependencies and pending infrastructure tasks.

## 🟢 Sprint 6 (Completed) - Android Polish & Backend Mock
- [x] **Android UI Polish:** Fixed NavGraph syntax errors, cleaned up CouponsScreen logic.
- [x] **Code Verification:** Verified Dashboard, Profile, and Login ViewModels against API definitions.
- [x] **Backend Engine:** Node.js server running in **Mock Mode** on localhost:5000.
- [x] **Build Ready:** Created `BUILD_INSTRUCTIONS.md` for Android Studio APK generation.

## 🚀 Sprint 7 (Next) - Integration & Connectivity
**Goal:** Connect the app to "Live" services to replace Mock Data.
- [x] **Real Firebase:** Connected Backend to Live Firestore. `google-services.json` present.
- [x] **AdMob Test Ads:** Verified actual ad loading with Test Unit IDs (on real device).
- [ ] **RazorPay Sandbox:** Implement actual Order Creation API call (replace mock transaction).
- [x] **End-to-End Test:** Verified Signup -> Watch Ad -> Earn Points -> Redeem Coupon loops successfully.

## 🔴 High Priority Backlog (External Blockers)
- [ ] **AdMob Approval:** Check dashboard daily. Status: Applied (Jan 22). 🚩 *Critical Path*
- [ ] **RazorPay Business KYC:** Check for "Active" status for UPI payouts. Status: Applied (Jan 22).
- [ ] **Google Play Console:** Registration (₹1,500 one-time fee) – Required by Week 8.

## 🔵 Upcoming / Remaining
- [ ] **Production Deployment:** Generate Signed APK/AAB for Play Store (requires valid KeyStore).
- [ ] **Backend Cloud Hosting:** Deploy Node.js server to Render/Heroku/AWS.
- [ ] **App-Ads.txt:** Deploy to hosting once AdMob is approved.

---

### 🔑 How to start Sprint 7 (Firebase Integration):
1. Go to **Firebase Console** > Create Project "ReelRewards".
2. **Android:** Add app `com.reelrewards.app` > Download `google-services.json` > Place in `/app`.
3. **Backend:** Project Settings > Service Accounts > Generate Key > Place in `/backend`.
