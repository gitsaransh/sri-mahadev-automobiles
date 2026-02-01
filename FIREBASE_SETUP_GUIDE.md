# 🔥 Firebase Setup Guide for ReelRewards

To move from "Mock Mode" to "Live Mode", you need to connect your app to a real Firebase Backend.

## Step 1: Create Project
1. Open [Firebase Console](https://console.firebase.google.com/).
2. Click **"Add project"**.
3. Name it: `ReelRewards` (or similar).
4. Disable Google Analytics for now (simplifies setup), or enable it if you prefer.
5. Click **"Create project"**.

## Step 2: Android Setup (`google-services.json`)
1. In the Firebase console, click the **Android Icon** (robot) to add an app.
2. **Package names:** `com.reelrewards.app` (Must match exactly).
3. **App nickname:** `ReelRewards Android`.
4. Click **Register app**.
5. **Download `google-services.json`**.
6. **Move this file to:**
   `c:\Users\Saransh\OneDrive\Documents\ReelsReward\android_app\app\google-services.json`

## Step 3: Backend Setup (`serviceAccountKey.json`)
1. In Firebase Console, click the **Gear Icon** (top left) > **Project Settings**.
2. Go to the **"Service accounts"** tab.
3. Click **"Generate new private key"**.
4. Click **"Generate key"** to download the JSON file.
5. **Rename** the file to: `serviceAccountKey.json`.
6. **Move this file to:**
   `c:\Users\Saransh\OneDrive\Documents\ReelsReward\backend\serviceAccountKey.json`

## Step 4: Enable Features
1. **Authentication:**
   - Go to **Build > Authentication**.
   - Click **Get Started**.
   - Enable **Phone** (for OTP) and **Google** (for Sign-in).
2. **Firestore Database:**
   - Go to **Build > Firestore Database**.
   - Click **Create Database**.
   - Select a location (e.g., `asia-south1` for Mumbai).
   - Start in **Test Mode** (allow all reads/writes for now).

## Step 5: Update Backend Config
1. Open `c:\Users\Saransh\OneDrive\Documents\ReelsReward\backend\.env`.
   - *Note: If clear, rename `.env.example` to `.env`*.
2. Update the `FIREBASE_DATABASE_URL`.
   - You can find this URL in the "Service accounts" tab (where you downloaded the key), or plainly as `https://<YOUR-PROJECT-ID>.firebaseio.com`.

---
**✅ Done?**
Once you have placed both files (`google-services.json` and `serviceAccountKey.json`), let me know by typing **"Files ready"**!
