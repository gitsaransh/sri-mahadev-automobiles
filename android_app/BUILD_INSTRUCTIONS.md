# Build Instructions for ReelRewards

Since the **command-line Gradle tools** are not configured in this environment (missing Java/Gradle), you must use **Android Studio** to build the APK.

## 1. Open the Project
1. Launch **Android Studio**.
2. Select **File > Open**.
3. Navigate to: `c:\Users\Saransh\OneDrive\Documents\ReelsReward\android_app`.
4. Click **OK**.

## 2. Sync Gradle
- Android Studio will detect the missing configuration.
- It will automatically create `local.properties` (with your SDK path).
- It will download the necessary Gradle wrapper files.
- Wait for the **"Sync"** process to complete (check the bottom right status bar).

## 3. Build the APK
1. Go to the menu bar: **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
2. Wait for the build to finish.
3. A notification bubble will appear: *"APK(s) generated successfully"*.
4. Click **"locate"** to open the folder containing `app-debug.apk`.

## 4. Install on Device
1. Connect your Android phone via USB.
2. Enable **USB Debugging** in your phone's Developer Options.
3. Run the app directly from Android Studio by clicking the green **Play (Run)** button ▶️ in the toolbar.
   - OR copy the `app-debug.apk` to your phone and install it manually.

## ⚠️ Notes
- The app is currently configured to point to `http://10.0.2.2:5000` (Emulator Localhost).
- If you run on a **Real Device**, you must ensure your phone and laptop are on the same Wi-Fi, and update `AppModule.kt` with your laptop's local IP address (e.g., `http://192.168.1.5:5000/`).
