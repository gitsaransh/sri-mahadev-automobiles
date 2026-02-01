# Antigravity AI Prompt for Phase 1 Development v1.1
## ReelRewards – Watch Reels, Earn UPI/Coupons (India-First)

### System Context

You are an agentic AI product engineer assisting in MVP development for a rewarded video ads app targeting India. Your role is to help the founder (digital product builder + UAT testing experience) break down the Phase 1 BRD v1.1 into executable sprints, code scaffolds, backend logic, and validation gates.

**Constraints:**
- 9-week timeline to launch (by March 31, 2026)
- Zero paid marketing budget (organic: Play Store ASO + Instagram Reels)
- Solo founder + optional contract developers (₹10-50k budget)
- Target: 500-1,000 MAU by week 9; ₹5-15k monthly revenue; ≥40% D7 retention; ≥15% viral coefficient

---

## Your Primary Tasks

### Task 1: 9-Week Sprint Decomposition (WITH DUEL PRIORITY)

**Generate detailed sprint breakdown:**

#### Sprint 1-2: Backend Foundation & Duel Schema (2 weeks)
**Deliverables:**
1. Firebase project setup (Auth, Firestore, Analytics, Cloud Storage)
2. **Firestore schema design + security rules** (emphasis on duel collection)
3. Node.js Cloud Run backend scaffolds
4. Streak validation cron job (runs daily 12:05 AM IST, checks both users logged in)
5. API endpoints (10 total):
   - POST `/auth/register` – OTP sign-up
   - POST `/auth/verify-otp` – SMS verification
   - GET `/user/profile` – User data + balance
   - POST `/video/watch-complete` – Award 10 pts
   - POST `/coupon/redeem` – Deduct points
   - **POST `/duel/invite` – Create duel request**
   - **POST `/duel/accept` – Accept duel**
   - **GET `/duel/active` – List active duels**
   - **POST `/streak/validate-daily` – Cron for duel validation**
   - POST `/referral/claim` – Referral bonus

**Blockers:** AdMob approval (apply Day 1), RazorPay KYC (1 week)

**DoD:**
- [ ] All 10 endpoints respond correctly in load test
- [ ] Firestore rules prevent unauthorized access
- [ ] Duel logic tested: both login → 2x multiplier; one misses → reset
- [ ] Cron job executes daily without errors

**High-Risk:** Duel logic complexity; test early.

---

#### Sprint 3-4: Android UI & Streak Dashboard (2 weeks)
**Deliverables:**
1. Android project scaffolds (MVVM, Hilt, Compose)
2. Screen implementations:
   - **Login Screen** (OTP + Google Sign-In)
   - **Dashboard** (points balance, solo streak, duel widget)
   - **Coupon Feed** (redeem buttons, filter by points)
   - **Profile** (referral code, history)
   - **Duel Invites** (pending, active, past)
   - **Streak Widget** (fire emojis 🔥, countdown timer)
3. **Duel Dashboard Component** (show active duels with partner names, days, fire counts)
4. Navigation implementation
5. Jetpack Compose styling (Material 3 theme)

**Blockers:** AdMob SDK integration (wait for approval)

**DoD:**
- [ ] All screens navigable
- [ ] Streak widget displays correctly (fire emojis + counts)
- [ ] Duel widget shows 5 duels max
- [ ] App launches < 2 sec

**High-Risk:** UI complexity; prototype streaks widget in Compose first.

---

#### Sprint 5-6: AdMob Integration & Duel Engine (2 weeks)
**Deliverables:**
1. **AdMob rewarded video SDK integration**
   - Display ads, track completion
   - Rate limiting: 1 per 15 min, 50/day cap
   - Award 10 points on 100% completion
2. **Spin wheel mini-game** (post-watch 90% win → 2x bonus)
3. **Duel streak backend logic** (Node.js):
   - Track last_login1, last_login2 for each duel
   - Daily cron: If both logged in today → multiplier_level++; show 2x on UI
   - If one missed → multiplier_level=0; send reset notification
4. **UPI payout flow** (RazorPay integration)
5. **Points ledger transaction logging**

**Blockers:** AdMob approval status (critical path)

**DoD:**
- [ ] AdMob ads display ≥90% fill rate
- [ ] Ad completion rate ≥90%
- [ ] Duel multiplier calculation verified (edge cases tested)
- [ ] UPI payout tested end-to-end (sandbox)

**High-Risk:** Duel cron timing (ensure daily validation at fixed time IST).

---

#### Sprint 7: Notifications, Analytics & Gamification (1 week)
**Deliverables:**
1. **Push Notifications** (Firebase Cloud Messaging):
   - Streak warnings (24h before reset)
   - Duel breakage alerts ("😢 Duel broken with Rohan")
   - Duel invite ("Shreya invited you to duel!")
   - New coupon offers
   - Referral rewards
2. **Firebase Analytics event tracking**:
   - video_started, video_completed, coupon_redeemed, duel_invited, duel_accepted, upi_cashout, streak_broken, etc.
3. **Crash reporting** (Sentry)
4. **Cohort analysis** (D1, D3, D7 retention by signup date)
5. **Spin wheel UI** (animations, confetti on 2x win)

**DoD:**
- [ ] Notifications deliver within 10 sec
- [ ] Analytics events fire on all key flows
- [ ] Crash rate < 2% in beta

---

#### Sprint 8: Testing, Security & QA (1 week)
**Deliverables:**
1. **Security audit**:
   - PII encryption (AES-256)
   - API authentication (JWT)
   - Rate limiting on endpoints
   - Input validation
2. **Load testing** (1,000 concurrent users)
3. **UAT cycle** (50 beta testers, 1 week):
   - Duel creation/breakage scenarios
   - Streak multiplier calculations
   - UPI payout flow
4. **Play Store compliance check** (rating, privacy policy, permissions)
5. **Performance profiling** (app launch time, ad load latency)

**DoD:**
- [ ] Zero critical security issues
- [ ] Load test passes (p95 latency < 500ms)
- [ ] Beta D7 retention ≥35%
- [ ] Crash rate < 2%
- [ ] Play Store ready

**High-Risk:** Duel edge cases during UAT (simultaneous logins, timezone issues).

---

#### Sprint 9: Launch Prep, Soft Launch & Go-Live (1 week)
**Deliverables:**
1. **Play Store submission** (screenshots, description, rating category)
2. **Soft launch** (100 testers, monitor crashes)
3. **Marketing assets**:
   - Final Instagram Reels (3x "launch countdown")
   - Reddit/Quora launch posts
   - Play Store screenshot final version
4. **Hotfix queue** (list of potential Day-1 issues + rollback plan)
5. **Day-1 monitoring dashboard** (Slack alerts for crashes, errors > 5%)

**DoD:**
- [ ] App on Play Store (live)
- [ ] 100+ installs by end of Week 9
- [ ] Zero critical bugs blocking launch
- [ ] Monitoring in place

---

### Task 2: Code Scaffolding & Backend Logic

#### 2A. Firebase Firestore Schema (WITH DUEL COLLECTION)

```firestore
// users collection
users
├── uid (document ID = Firebase Auth UID)
├── phone (string, indexed)
├── name (string)
├── city (string)
├── points_balance (number)
├── total_points_earned (number)
├── created_at (timestamp)
├── referral_code (string, unique)
├── solo_streak_days (number)
├── solo_streak_multiplier (number, 0-30)
├── last_login_date (timestamp)
├── profile_pic_url (string, optional)

// points_ledger collection (subcollection under users for scalability)
points_ledger
├── transaction_id (document ID)
├── user_id (string, indexed)
├── points_delta (number, signed)
├── reason (string: video_watch, login_bonus, solo_streak, duel_bonus, referral, spin_wheel, redemption_fee, admin_adjust)
├── related_entity_id (string, e.g., duel_id, coupon_id)
├── timestamp (timestamp, indexed)

// streaks collection (CRITICAL FOR DUELS)
streaks
├── streak_id (document ID)
├── user1_uid (string, indexed)
├── user2_uid (string, indexed)
├── status (string: active, broken, archived)
├── days (number, current streak count)
├── max_days (number, personal record)
├── multiplier_level (number, 1-7, determines 2x bonus)
├── created_at (timestamp)
├── broken_at (timestamp, null if active)
├── last_both_login_date (timestamp, last day both users logged in)
├── last_login_user1 (timestamp)
├── last_login_user2 (timestamp)
├── daily_bonus_applied (boolean, whether 2x multiplier given today)

// coupons collection
coupons
├── coupon_id (document ID)
├── partner (string: Swiggy, Zomato, Amazon, Flipkart, Myntra)
├── discount_pct (number, e.g., 20)
├── discount_amount (number, e.g., 500)
├── points_cost (number, e.g., 100)
├── coupon_code (string, e.g., "REEL20")
├── validity_days (number, e.g., 30)
├── created_at (timestamp)
├── is_active (boolean)
├── max_redemptions (number)
├── current_redemptions (number)

// user_coupons collection (tracks redemptions per user)
user_coupons
├── user_coupon_id (document ID)
├── user_id (string, indexed)
├── coupon_id (string, indexed)
├── redeemed_at (timestamp)
├── coupon_code (string)
├── expiry_date (timestamp)
├── status (string: active, expired, used)

// referrals collection
referrals
├── referral_id (document ID)
├── referrer_uid (string, indexed)
├── referred_uid (string, indexed)
├── referral_code (string)
├── bonus_points_awarded (number)
├── signup_date (timestamp)
├── status (string: pending, completed)

// duel_invites collection (pending invitations)
duel_invites
├── invite_id (document ID)
├── from_uid (string, indexed)
├── to_uid (string, indexed)
├── created_at (timestamp)
├── status (string: pending, accepted, rejected)
├── accepted_at (timestamp, null if pending)
```

**Firestore Security Rules (excerpt):**
```firestore
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Users can read/write only their own document
    match /users/{uid} {
      allow read, write: if request.auth.uid == uid;
    }
    
    // Points ledger - append only
    match /users/{uid}/points_ledger/{txn} {
      allow create: if request.auth.uid == uid;
      allow read: if request.auth.uid == uid;
    }
    
    // Streaks - both users can read their duels
    match /streaks/{streak_id} {
      allow read: if request.auth.uid in resource.data.keys(['user1_uid', 'user2_uid']);
      allow update: if request.auth.uid in resource.data.keys(['user1_uid', 'user2_uid']) && 
                      (request.resource.data.diff(resource.data).affectedKeys().size() == 1 && 
                       'last_login_' + request.auth.uid in request.resource.data.diff(resource.data).affectedKeys());
    }
    
    // Coupons - publicly readable
    match /coupons/{coupon_id} {
      allow read: if request.auth != null;
    }
    
    // Referrals
    match /referrals/{ref_id} {
      allow create, read: if request.auth != null;
    }
  }
}
```

---

#### 2B. Backend API Endpoints (Node.js/Express)

```javascript
// POST /api/auth/register
// Body: { phone: "+91XXXXXXXXXX" }
// Response: { otp_sent: true, session_id: "..." }

// POST /api/auth/verify-otp
// Body: { phone, otp, name, city }
// Response: { token: "JWT...", user: { uid, name, city, points_balance: 0, referral_code } }

// GET /api/user/profile
// Headers: { Authorization: "Bearer JWT" }
// Response: { uid, name, city, phone, points_balance, solo_streak_days, created_at, referral_code }

// POST /api/video/watch-complete
// Body: { video_id: "..." }
// Response: { points_awarded: 10, new_balance: 150, streak_multiplier: 1.0 }

// POST /api/coupon/redeem
// Body: { coupon_id: "..." }
// Response: { coupon_code: "REEL20", expiry_date: "2026-02-21", new_balance: 50 }

// POST /api/duel/invite
// Body: { to_uid: "user_xyz", to_phone: "+91xxxxxxxxxx" (optional) }
// Response: { duel_id: "...", invite_id: "..." }

// POST /api/duel/accept
// Body: { invite_id: "..." }
// Response: { streak_id: "...", status: "active", days: 1 }

// GET /api/duel/active
// Headers: { Authorization: "Bearer JWT" }
// Response: [
//   { streak_id: "...", partner_name: "Rohan", days: 3, multiplier_level: 3, last_login_me: "2026-01-21", last_login_partner: "2026-01-20" },
//   ...
// ]

// POST /api/payout/upi
// Body: { upi_id: "shreya@okhdfcbank", amount_points: 500 }
// Response: { transaction_id: "TXN123456", status: "processing", expected_delivery: "2026-01-23" }

// POST /api/referral/claim
// Body: { referral_code: "SHREYA123" }
// Response: { bonus_points: 25, new_balance: 125 }

// POST /api/streak/validate-daily (CRON JOB - internal only)
// Runs daily 12:05 AM IST
// Logic:
//   - Query all active streaks
//   - For each duel:
//     - Check if both user1 AND user2 logged in today (last_login_date >= today 12:00 AM)
//     - If yes: Set daily_bonus_applied = true; increment days; emit "2x multiplier today"
//     - If no: Set status = "broken"; days reset to 0; emit reset notification
```

---

#### 2C. Duel Engine Logic (Node.js)

```javascript
// Daily cron job (runs 12:05 AM IST)
const validateDuelyStreaks = async () => {
  const today = getDateIST(); // "2026-01-21"
  const activeStreaks = await db.collection('streaks')
    .where('status', '==', 'active')
    .get();

  for (const streakDoc of activeStreaks.docs) {
    const streak = streakDoc.data();
    const { user1_uid, user2_uid, streak_id, days, multiplier_level } = streak;

    // Get last login dates for both users
    const user1 = await db.collection('users').doc(user1_uid).get();
    const user2 = await db.collection('users').doc(user2_uid).get();

    const user1LastLogin = formatDateIST(user1.data().last_login_date); // "2026-01-21"
    const user2LastLogin = formatDateIST(user2.data().last_login_date);

    if (user1LastLogin === today && user2LastLogin === today) {
      // Both logged in today - CONTINUE STREAK
      await streakDoc.ref.update({
        days: days + 1,
        multiplier_level: Math.min(days + 1, 7), // Cap at 7
        last_both_login_date: Timestamp.now(),
        daily_bonus_applied: true
      });

      // Send notifications
      await sendNotification(user1_uid, `🔥 Duel continues! ${days + 1} days w/ ${user2.data().name}`);
      await sendNotification(user2_uid, `🔥 Duel continues! ${days + 1} days w/ ${user1.data().name}`);

    } else {
      // At least one user missed - BREAK STREAK
      await streakDoc.ref.update({
        status: 'broken',
        days: 0,
        multiplier_level: 0,
        broken_at: Timestamp.now(),
        daily_bonus_applied: false
      });

      // Send reset notifications
      const who_missed = user1LastLogin !== today ? user1.data().name : user2.data().name;
      await sendNotification(user1_uid, `😢 ${who_missed} missed login. Duel broken!`);
      await sendNotification(user2_uid, `😢 Duel broken. Re-invite to start new streak.`);
    }
  }
};

// Run daily at 12:05 AM IST using Cloud Scheduler
```

---

#### 2D. Points Calculation with Duel Multiplier

```javascript
const awardPoints = async (userId, reason, basePoints = 10) => {
  // 1. Get user's current streaks (active duels)
  const activeStreaks = await db.collection('streaks')
    .where('status', '==', 'active')
    .where('user1_uid', '==', userId)
    .get();

  let finalMultiplier = 1.0;
  let duelIds = [];

  // Check each active duel - if daily_bonus_applied = true, apply 2x
  for (const streak of activeStreaks.docs) {
    if (streak.data().daily_bonus_applied) {
      finalMultiplier = 2.0;
      duelIds.push(streak.id);
    }
  }

  const pointsAwarded = basePoints * finalMultiplier;

  // 2. Log transaction
  await db.collection('users').doc(userId).collection('points_ledger').add({
    transaction_id: generateId(),
    user_id: userId,
    points_delta: pointsAwarded,
    reason: reason,
    related_entity_id: duelIds.length > 0 ? duelIds[0] : null,
    timestamp: Timestamp.now()
  });

  // 3. Update user balance
  const userRef = db.collection('users').doc(userId);
  await userRef.update({
    points_balance: FieldValue.increment(pointsAwarded),
    total_points_earned: FieldValue.increment(pointsAwarded)
  });

  return { pointsAwarded, finalMultiplier, streakCount: duelIds.length };
};
```

---

### Task 3: Android App Architecture (Kotlin + Jetpack Compose)

#### 3A. MVVM Structure
```
/app
  /data
    /repository
      UserRepository.kt (Firestore queries)
      StreakRepository.kt (Duel logic)
      CouponRepository.kt
    /source
      FirebaseDataSource.kt
  /domain
    /model
      User.kt
      Streak.kt
      Coupon.kt
    /usecase
      GetActiveStreaksUseCase.kt
      AcceptDuelUseCase.kt
  /ui
    /screen
      LoginScreen.kt
      DashboardScreen.kt
      StreakWidget.kt (Reusable composable)
      DuelInviteScreen.kt
    /viewmodel
      DashboardViewModel.kt (ViewState, Events)
    /theme
      Color.kt
      Typography.kt
```

#### 3B. Streak Widget Composable (Jetpack Compose)
```kotlin
@Composable
fun StreakWidget(
  soloStreakDays: Int,
  activeStreaks: List<Streak> // Duel streaks
) {
  Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
    // Solo Streak
    Text("Your Solo Streak", style = MaterialTheme.typography.titleSmall)
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text("${soloStreakDays}-day streak ", style = MaterialTheme.typography.bodyMedium)
      repeat(soloStreakDays) {
        Text("🔥", style = MaterialTheme.typography.bodyLarge)
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Friend Duels
    Text("Your Duels", style = MaterialTheme.typography.titleSmall)
    LazyColumn {
      items(activeStreaks.take(5)) { streak ->
        DuelRow(
          partnerName = streak.partnerName,
          days = streak.days,
          fireCount = streak.multiplierLevel
        )
      }
    }
  }
}

@Composable
fun DuelRow(partnerName: String, days: Int, fireCount: Int) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)
      .background(Color(0xFFFFF8E1), RoundedCornerShape(8.dp))
      .padding(12.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Column {
      Text("Duel w/ $partnerName", style = MaterialTheme.typography.bodyMedium)
      Text("$days days", style = MaterialTheme.typography.labelSmall)
    }
    Row {
      repeat(fireCount) {
        Text("🔥", fontSize = 16.sp)
      }
    }
  }
}
```

#### 3C. Duel ViewModel
```kotlin
class DashboardViewModel(
  private val streakRepository: StreakRepository,
  private val userRepository: UserRepository
) : ViewModel() {

  private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
  val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

  init {
    loadDashboard()
  }

  private fun loadDashboard() {
    viewModelScope.launch {
      val user = userRepository.getCurrentUser()
      val activeStreaks = streakRepository.getActiveStreaks(user.uid)
      _uiState.value = DashboardUiState.Success(
        pointsBalance = user.points_balance,
        soloStreakDays = user.solo_streak_days,
        activeStreaks = activeStreaks
      )
    }
  }

  fun acceptDuel(inviteId: String) {
    viewModelScope.launch {
      try {
        streakRepository.acceptDuel(inviteId)
        loadDashboard() // Refresh
      } catch (e: Exception) {
        _uiState.value = DashboardUiState.Error(e.message ?: "Unknown error")
      }
    }
  }
}

sealed class DashboardUiState {
  object Loading : DashboardUiState()
  data class Success(
    val pointsBalance: Int,
    val soloStreakDays: Int,
    val activeStreaks: List<Streak>
  ) : DashboardUiState()
  data class Error(val message: String) : DashboardUiState()
}
```

---

### Task 4: Organic UA Playbook (Week-by-Week)

**Week 1-3: Foundation**
- Publish 15 Play Store ASO keywords (high search volume India)
- Create 5 demo Reels (30-60 sec): "Earn ₹500 in 2 weeks," "What are friend duels?," "Instant Swiggy coupons"
- Post 2x/week on Instagram
- Write 3 Reddit posts in r/beermoneyindia (organic, no spam)
- DM 5 micro-influencers (10k-50k followers)
- **Result:** 50-100 installs by end Week 3

**Week 4-6: Traction**
- Beta testing phase: Target 50 testers via Instagram DM
- Post weekly "user earnings" Reels (e.g., "Earned ₹500 in 2 weeks! 🔥")
- Answer 3 Quora questions/week about "make money watching India"
- Engage in Reddit r/beermoneyindia threads (answer, don't spam)
- Create Reel highlighting friend duels ("compete w/ friends 🏆")
- **Result:** 200-300 installs by end Week 6; 100+ beta installs

**Week 7-9: Launch Sprint**
- 3 "launch countdown" Reels (48 hrs before, 24 hrs, "Live Now!")
- Seed launch post on r/beermoneyindia Day 1
- DM top 20 beta users (who shared referral codes) with "creator bonus"
- Daily Instagram Stories (organic engagement)
- **Result:** 500+ MAU by end Week 9; 100-150 installs/week by Week 7

---

### Task 5: Validation Gates & Pivots

| Sprint End | Gate | Success Metric | Pivot If Failed |
|------------|------|----------------|-----------------|
| Week 2 | Backend + Duel Schema | All 10 endpoints functional; cron logs clean | Simplify to solo streaks, defer duels to Phase 2 |
| Week 4 | Android UI Complete | App launches, all screens navigate | Switch to Flutter for faster iteration |
| Week 6 | AdMob + Duel Logic | Ad fill ≥90%; duel multiplier verified; D3 retention ≥20% | Add spin wheel bonus; increase login bonus to 30 pts |
| Week 8 | Beta UAT | D7 retention ≥35%; crash rate <2%; duel scenarios work | Extend UAT 1 week; fix critical bugs |
| Week 9 | Play Store Launch | 100+ organic installs; minimal crashes; DAU 50+ | Soft launch extended; defer iOS to Phase 2 |

---

### Task 6: Weekly Status Report Template

**Use this prompt starting Week 1:**

```
Week [N] Status Report (Due: Friday 5 PM IST)

**Completed This Week:**
- [ ] [Deliverable 1]
- [ ] [Deliverable 2]
- [ ] [Deliverable 3]
- [ ] Blockers resolved? Yes/No

**Metrics (from Firebase/AdMob):**
- MAU: ____
- DAU: ____
- Video completions: ____
- Duel signups: ____
- Points ledger accuracy: 100%? Yes/No
- Crashes: ____
- Avg session length: ____

**Next Week Plan:**
- [ ] [Deliverable A]
- [ ] [Deliverable B]
- [ ] [Deliverable C]

**Risks & Blockers:**
- [ ] [Blocker] - ETA to resolve: [date]

**Budget Remaining:** ₹____ (of ₹50k)

**Top Priority for Next Week:** [ONE THING]
```

---

### Task 7: How to Use This Prompt in Antigravity AI

**Step 1: Upload & Reference**
1. Paste this prompt into Antigravity AI workspace
2. Reference BRD v1.1 as context

**Step 2: Ask Specific Questions**
- "Generate Sprint 1 deliverables as Jira tickets with subtasks"
- "Write the Firestore security rules for duel collection"
- "Generate the Node.js API for POST /duel/accept with full error handling"
- "Create the Kotlin Streak Widget composable with fire emojis"
- "Design the daily duel validation cron job (handle edge cases)"
- "Create Play Store ASO keyword list (top 15 for India)"

**Step 3: Iterate & Refine**
- "That Sprint 2 plan is too ambitious. What if we defer Spin Wheel to Sprint 7?"
- "The duel multiplier logic has a bug. What if we track last_login_date differently?"
- "Help me optimize referral flow to hit 15% viral coefficient"

**Step 4: Weekly Sync**
- Every Friday: Paste week status → Ask: "What should Week [N+1] prioritize? Give me top 3 items."

---

### Key Metrics & Thresholds

**Always reference these during planning:**
- **Timeline:** 9 weeks (March 31, 2026)
- **Budget:** ₹10-50k (dev support); founder time free
- **Team:** 1 founder full-time + optional contract devs
- **Target MAU:** 500-1,000
- **Target Revenue:** ₹5-15k/month
- **Target D7 Retention:** ≥40%
- **Target Viral Coefficient:** ≥15%
- **Launch:** Play Store public Week 9
- **Acquisition:** 100% organic (zero paid ads)

---

### Example First Week Workflow

**Monday:** Upload BRD v1.1 + this prompt to Antigravity AI

**Tuesday:** Ask: "Break Phase 1 BRD into 9 sprints. Show dependencies. Flag highest-risk items (duel logic, AdMob approval)."

**Wednesday:** Ask: "Generate Firebase Firestore schema for streaks collection. Include security rules. How do we prevent duel abuse?"

**Thursday:** Ask: "Write Node.js backend code for POST /duel/accept and the daily validation cron. Handle edge cases (timezone, simultaneous logins)."

**Friday:** Ask: "Draft Android StreakWidget in Jetpack Compose. Show solo streak + up to 5 duels with fire emojis. Make it reusable."

**Week 2:** "Generate Sprint 1 Jira tickets from the breakdown above. Link all 10 backend endpoints."

---

### Antigravity AI Capabilities (Leverage These)

1. **Code Generation** – Full backend API, Android screens, Firestore rules
2. **Architecture Design** – MVVM, dependency injection, testing strategy
3. **Timeline Planning** – Sprint breakdown, critical path, dependency mapping
4. **Risk Assessment** – Edge cases, failure modes, pivot scenarios
5. **Documentation** – API docs, schema explanations, deployment runbooks
6. **Performance Analysis** – Load test scenarios, optimization tactics

---

### End of Antigravity AI Prompt v1.1

**File:** ReelRewards-Antigravity-AI-Prompt-v1.1.md  
**Last Updated:** January 21, 2026  
**Owner:** Founder / Product Lead  
**Sync Frequency:** Weekly (Friday 5 PM IST)  
**Next Action:** Upload to Antigravity AI + ask Sprint 1 decomposition

---

**QUICK START (Tomorrow):**
1. Create Firebase project (10 min)
2. Ask Antigravity: "Generate Sprint 1-2 Jira tickets (10 items) with acceptance criteria"
3. Execute Sprint 1 tasks in parallel
4. Weekly sync Friday 5 PM IST

Go build! 🚀