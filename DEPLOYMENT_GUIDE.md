# Deploying ReelRewards Backend to Railway.app 🚂

This guide explains how to deploy your Node.js backend to the cloud for FREE using Railway.app.

## Prerequisites
- A GitHub account.
- Your project pushed to a GitHub repository (create one if you haven't).

## Step 1: Prepare the Code (Already Done by Agent)
- We created a `.gitignore` file to ensure `serviceAccountKey.json` and `node_modules` are NOT uploaded.
- We modified `index.js` to read the Firebase Key from an Environment Variable (`FIREBASE_CONFIG_JSON`).

## Step 2: Push to GitHub
1.  Initialize git in your `backend` folder if needed:
    ```bash
    cd c:\Users\Saransh\OneDrive\Documents\ReelsReward\backend
    git init
    git add .
    git commit -m "Initial backend commit"
    ```
2.  Create a new repository on GitHub.com named `reels-reward-backend`.
3.  Connect and push:
    ```bash
    git remote add origin https://github.com/YOUR_USERNAME/reels-reward-backend.git
    git push -u origin main
    ```

## Step 3: Deploy on Railway
1.  Go to [Railway.app](https://railway.app/) and login with GitHub.
2.  Click **"New Project"** > **"Deploy from GitHub repo"**.
3.  Select your `reels-reward-backend` repo.
4.  Railway will detect Node.js and start building. **It will fail initially** because the Firebase key is missing. This is normal.

## Step 4: Add Environment Variables
1.  Click on your project in Railway.
2.  Go to the **"Variables"** tab.
3.  Add the following variables:
    *   `PORT`: `5000`
    *   `FIREBASE_DATABASE_URL`: Your actual Firebase DB URL (e.g., `https://your-project.firebaseio.com`).
    *   `FIREBASE_CONFIG_JSON`: **Copy the entire content** of your `serviceAccountKey.json` file and paste it here as the value.

## Step 5: Finalize
1.  Railway will automatically redeploy once variables are saved.
2.  Once green, go to **"Settings"** > **"Domains"** and generate a public domain (e.g., `reels-reward-backend.up.railway.app`).
3.  **Copy this URL.**

## Step 6: Update Android App
1.  Open `android_app/.../di/AppModule.kt`.
2.  Change `.baseUrl("http://10.0.2.2:5000/")` to your new Railway URL:
    ```kotlin
    .baseUrl("https://reels-reward-backend.up.railway.app/")
    ```
3.  Rebuild your APK. 🚀
