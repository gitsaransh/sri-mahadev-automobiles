# Business Requirements Document (BRD) v1.1
## Phase 1: ReelRewards – Watch Reels, Earn UPI/Coupons (India-First)

**Document Version:** 1.1  
**Owner:** Product Lead / Founder  
**Status:** Ready for Execution  
**Last Updated:** January 21, 2026  
**Target Launch:** March 31, 2026 (9 weeks)  
**Target Market:** India (Tier-1 cities: Mumbai, Bangalore, Delhi, Hyderabad)

---

## 1. Executive Summary

**Problem:** Indians waste 45+ min/day on reels but earn nothing. Global apps (Swagbucks, InboxDollars) require ₹1,200+ min payout; lack local redemption partners; no social gamification.

**Solution:** ReelRewards is a fun, India-first mobile app where users watch **ASMR/food reels** (curated content, not boring ads) to earn points redeemable as:
- **UPI Cashout** (₹100 min = fastest payout in category)
- **E-commerce Coupons** (Swiggy, Zomato, Amazon, Flipkart, Myntra)
- **Friend Duel Streaks** (Snapchat-style: both login daily → mutual 2x bonus multiplier)

**Unique Value Proposition:**
- 3x faster payouts than global competitors (₹100 vs $15 PayPal min)
- 40% higher retention via bidirectional friend duels (vs solo streaks)
- 15% viral coefficient via referral + duel mechanics (vs 5% competitors)
- One-tap UX: Watch → Instant toast → Swipe redeem (vs survey clutter)

**Business Model:** Monetize via AdMob rewarded videos (CPM ₹800-1,500 locally), affiliate commission on coupon redemptions, freemium cashout fees.

**Phase 1 Goal:** Launch MVP with bidirectional streaks + 5 coupon partners, acquire 500-1,000 organic MAU via Play Store ASO + Instagram Reels in 9 weeks, validate ₹5,000-15,000 monthly revenue potential + ≥40% D7 retention.

---

## 1.1 Competitor Comparison

| Metric | ReelRewards | Swagbucks | InboxDollars | MyPoints | Reel Rush |
|--------|-------------|-----------|--------------|----------|-----------|
| **Payout Speed** (India) | ₹100 UPI (instant) | $15 PayPal (5-7 days) | $30 (10+ days) | $25 (7+ days) | ₹500 (unreliable) |
| **Content Type** | ASMR reels (fun) | Surveys (boring) | Emails + surveys | Shopping (narrow) | TikTok clone (scam-prone) |
| **Social Features** | Friend duels (unique) | Solo logins | None | None | Basic shares |
| **Local Rewards** | Swiggy/Zomato/UPI | Generic gift cards | Generic PayPal | Shopping cashback | Generic PayPal |
| **Monthly Earnings (India)** | ₹200-500 (realistic) | ₹50-150 (low) | ₹30-100 (low) | ₹80-200 | ₹100-200 (unreliable) |
| **D7 Retention** | 40%+ (duels) | 25% | 20% | 22% | 18% |
| **Why India Users Choose Us** | Quick cash + fun + friends | None | None | None | Scam reputation |

**Our Win:** We're the only app solving for **local instant payouts + social fun + ASMR content preference** in India simultaneously.

---

## 2. Business Objectives & Metrics

### Success Criteria (Phase 1)
- [ ] **Downloads:** 500-1,000 active installs by week 9
- [ ] **Daily Active Users (DAU):** 200+ by end of Phase 1
- [ ] **Monthly Active Users (MAU):** 500-1,000 by end of Phase 1
- [ ] **Video Completion Rate:** ≥90% (rewarded ads typically 95%+)
- [ ] **Monthly Revenue (Gross):** ₹5,000-15,000 by week 8
- [ ] **App Store Rating:** ≥4.0 stars (target 50+ reviews)
- [ ] **User Retention (Day 7):** ≥40% (streaks + duels)
- [ ] **Viral Coefficient:** ≥15% (friend duels drive referrals)
- [ ] **Coupon Redemption Rate:** ≥15% of DAU
- [ ] **UPI Cashout Rate:** ≥5% of MAU

### KPIs to Track
| KPI | Target (Week 9) | Measurement Method |
|-----|-----------------|-------------------|
| Organic downloads | 500-1,000 | Play Store analytics |
| DAU | 200+ | Firebase Analytics |
| MAU | 500-1,000 | Firebase Analytics |
| Avg session length | 8-12 min | Firebase Analytics |
| Video completion rate | ≥90% | AdMob + Firebase |
| Points earned (daily/user) | 50-100 | Backend ledger |
| Coupon redemptions | ≥15% | Firebase events |
| UPI cashouts | ≥5% | RazorPay dashboard |
| Ad revenue | ₹5-15k | AdMob dashboard |
| Referral signups | ≥50% of DAU | Firebase attribution |
| Friend duel signups | ≥20% of DAU | Custom events |
| D7 retention | ≥40% | Firebase Cohorts |
| Viral coefficient | ≥15% | Growth attribution |

---

## 3. Project Scope

### What's Included (Phase 1 MVP)
- **Android app** (primary) via Google Play Store
- **ASMR/Food reel curation** (seed 50-100 videos manually; your content niche)
- **Core features:**
  - Watch ads → Earn 10 points (instant toast notification)
  - Daily login streak (solo): Day 1=20pts, Day 2=21pts (5%), Day 7=26pts (30%)
  - **Bidirectional Friend Duel Streaks** (Snapchat-style): Invite friends → Mutual 2x multiplier if both login daily; reset if one misses
  - Referral system: +50 pts referrer, +25 pts referred (capped ₹500/month)
  - Spin wheel mini-game: Post-watch 90% win → 2x points bonus
  
- **3-5 pre-integrated coupon partners** (Swiggy, Zomato, Amazon, Flipkart)
- **UPI Redemption** (₹100+ threshold via RazorPay)
- **User Authentication** (phone OTP + Google Sign-In)
- **Points Dashboard** (balance, available coupons, redemption history, streak widget with fire emojis 🔥)
- **Push Notifications** (new coupons, streak warnings 24h before reset, referral bonuses, duel invites)
- **AdMob Rewarded Video Ads** (rate-limited: 1 per 15 min, 50 max/day)
- **Analytics & Monitoring** (Firebase Analytics, Sentry crash reporting)
- **Basic Admin Panel** (manual coupon creation, user reports)

### What's Excluded (Future Phases)
- iOS app (Phase 2)
- Web platform (Phase 2)
- Video upload by users (Phase 2+)
- AI content recommendations (Phase 3+)
- Multi-language support (Phase 2+)
- Advanced gamification (badges, leaderboards) – Phase 2 if D7 retention < 40%
- In-app currency purchase (freemium only Phase 1)
- Multiple payment methods (UPI only, Phase 1)

### Out of Scope Decisions
- No live chat support (automated FAQ + email support only)
- No admin content moderation UI (founder manages manually Week 1-9)
- No social media login beyond Google (SMS OTP primary)
- No offline mode (requires internet for real-time streak validation)

---

## 4. User Personas

### Persona 1: Shreya (Side Hustler, 26-32)
- **Location:** Bangalore, professional day job
- **Income:** ₹60-80k/month job + ₹10-15k/month digital products
- **Goal:** Stack ₹3-5k/month passive via duels + referrals; also promote app in Instagram Reels for affiliate bonus
- **Behavior:** Watches reels 45+ min daily; shares apps with followers; competitive nature
- **Pain Point:** Wants higher-earning potential, transparent revenue tracking, social gamification
- **Success Metric:** Maintains 5+ friend duels, earns ₹2k/month, refers 30+ users
- **App Usage:** 15-20 min/day, logs in daily for streaks

### Persona 2: Arjun (Casual Earner, 22-28)
- **Location:** Pune, Tier-2 city
- **Income:** ₹20-40k/month salary
- **Goal:** Earn ₹500-1,000/month supplementary coupons/cash for food/shopping
- **Behavior:** Watches reels 30 min daily; engages 5-10 min with reward apps; values quick redemptions
- **Pain Point:** Wants no-friction earning without complex gamification; instant Swiggy/Zomato discounts
- **Success Metric:** Redeems ≥2 coupons/month, maintains 3-day streak
- **App Usage:** 5-8 min/day, 4-5 days/week

### Persona 3: Rohan (Student, 18-22)
- **Location:** Hyderabad, college student
- **Income:** Minimal (parental support)
- **Goal:** Earn free food coupons (Zomato) and small cash (₹100-500/month) for entertainment/gaming
- **Behavior:** Watches 10-15 min content daily; discontinuous engagement; social/fun-motivated
- **Pain Point:** Wants zero friction, fast redemptions (within 2-3 watches), social duels for competition
- **Success Metric:** Redeems 1 coupon in first week, participates in 2+ duels
- **App Usage:** 10-15 min/day, 5-6 days/week (peaks weekends)

---

## 5. Functional Requirements (MoSCoW)

### MUST HAVE (Phase 1 Core)

#### 5.1 User Registration & Authentication
- **Phone OTP Sign-Up:** India-specific SMS via Firebase OTP (Twilio backup)
- **Google Sign-In:** One-tap OAuth alternative
- **Profile Creation:** Name, phone, city, referral code auto-generation
- **Session Management:** JWT tokens, auto-logout after 7 days, remember-me option
- **Password Recovery:** Via OTP (no passwords stored)

#### 5.2 Video Watching & Ad Serving (ASMR/Reels Focus)
- **Rewarded Video Ads via AdMob SDK**
  - Display ads from AdMob inventory (high-quality, brand-safe)
  - Track 100% completion (full video watch = 10 points awarded)
  - Countdown timer visible (e.g., "Video ends in 5s")
  - Prevent ad fraud: 
    - Rate limiting: 1 ad per 15 min
    - Daily cap: 50 ads/day max
    - Device fingerprinting (Adjust SDK)
    - CaptchaV3 on suspicious activity
- **ASMR/Food Reel Curation** (Phase 1 manual seed)
  - Display curated playlist of ASMR/cooking reels (your content niche)
  - Mix with AdMob rewarded videos (1 reel, then 1 ad rotation)
  - Ability to skip reel (no penalty) or watch ad (earn points)
- **Post-Watch Spin Wheel** (engagement booster)
  - 90% chance: 2x points (popup toast)
  - 10% chance: 1x points (no bonus)
  - Max 1 spin per video, cooldown 12 hrs

#### 5.3 Points Earning System
- **Base Earning:**
  - 10 points per completed video/ad
  - 1 point = ₹1 equivalent value (simple conversion)
- **Daily Login Bonus (Solo Streak):**
  - Day 1 login: 20 points (base, 0% bonus)
  - Day 2 login: 20 + 5% = 21 points
  - Day 3 login: 20 + 10% = 22 points
  - ...
  - Day 7 login: 20 + 30% = 26 points (max bonus, then resets to Day 1)
  - Miss 1 day: Streak resets to 0
- **Friend Duel Streaks (Bidirectional):**
  - Invite friend → "Duel w/ [Friend Name]" initiated
  - Both users must login daily for duel to continue
  - **If both login:** Daily duel bonus = 2x multiplier on all points that day
  - **If one misses:** Duel streak breaks for both → "Streak Lost 😢" notification
  - Max 5 active duels/user (prevents clutter)
  - Dashboard shows: "Solo Streak: 5🔥 | Duel w/ Rohan: 3🔥🔥🔥 | Duel w/ Shreya: 7🔥🔥🔥🔥🔥🔥🔥"
- **Referral Bonus:**
  - Referrer: 50 points per referred user who completes onboarding
  - Referred user: 25 points signup bonus
  - Capped: Max 500 referral points/month per user
  - Tracking: Via unique referral code + UTM parameters
- **Spin Wheel Bonus:**
  - 90% win: 2x daily points for next 24 hrs
  - Max 1 spin per video, 12-hr cooldown
- **Backend Ledger:**
  - Firestore collection: `points_ledger {user_id, transaction_id, points_delta, reason, timestamp}`
  - Reason codes: video_watch, login_bonus, solo_streak, duel_bonus, referral, spin_wheel, redemption_fee, admin_adjust
  - Real-time balance sync to app

#### 5.4 Coupon Redemption
- **Coupon Catalog (Pre-Seeded 5 Partners):**
  - Swiggy: 5%, 10%, 20% off (cost: 50, 100, 200 points)
  - Zomato: 10%, 15% off (cost: 100, 150 points)
  - Amazon: ₹50, ₹100 voucher (cost: 50, 100 points)
  - Flipkart: ₹100 voucher (cost: 100 points)
  - Myntra: ₹200 voucher (cost: 200 points)
- **Redemption Flow:**
  - User clicks "Redeem" → Confirm points deduction → Display code (e.g., "REEL20")
  - One-tap copy to clipboard
  - Redemption receipt via SMS (code + expiry date)
  - Redemption marked in history with timestamp
  - No return/undo (irreversible transaction)
- **Coupon Expiry:**
  - Validity: 30 days from redemption
  - SMS reminder at day 25
  - Expired coupons marked as "Used" in history

#### 5.5 UPI Cashout
- **Threshold:** Minimum ₹100 points (= ₹100 equivalent)
- **Daily Limit:** ₹5,000/month max (fraud prevention)
- **UPI Integration (RazorPay):**
  - User enters UPI ID (e.g., shreya@okhdfcbank)
  - Initiate payout via RazorPay API
  - Processing: 1-3 business days
  - Confirmation: SMS + in-app notification with transaction ID
- **Failure Handling:**
  - Invalid UPI → Error message + retry option
  - RazorPay failure → Points reversed, user retried next day
  - Settlement delay → SMS update after 2-3 days

#### 5.6 Streak Dashboard & Notifications
- **Dashboard Widget (Home Screen):**
  - Solo Streak: "7-day streak 🔥" (fire emojis, count visible)
  - Friend Duels: Card list showing duel partner name, days, fire count
  - Max 5 duel cards visible (scroll if >5)
  - Tap to view duel details: Last login dates, current multiplier, potential breakage
  - Invite New Duel button → Search friend or share link
- **Notifications:**
  - **Duel Invite:** "Shreya invited you to duel! 🔥 [Accept] [Decline]"
  - **Duel Joined:** "You & Shreya are on a 1-day streak together!"
  - **Streak Warning (24h before reset):** "Login today to keep your 5-day streak! ⏰"
  - **Streak Loss:** "😢 You missed a day. Your 7-day streak is reset."
  - **Duel Breakage:** "😢 Rohan missed login. Your duel streak is broken."
  - **New Coupon:** "New Swiggy 20% off code! Redeem now 🍔"
  - **Referral Reward:** "+50 points! Arjun joined via your code"
  - **Spin Wheel Win:** "🎉 2x points today! Keep watching"

#### 5.7 Referral System (Basic)
- **Unique Referral Code:** Auto-generated per user (e.g., "SHREYA123")
- **Share Options:**
  - WhatsApp: Pre-filled message → "Earn ₹100 coupons! Download ReelRewards. Use code SHREYA123 https://play.google.com/store/apps/details?id=com.reelrewards"
  - Instagram: QR code + link
  - SMS: Text message template
  - Copy link button
- **Attribution:**
  - Referred user installs app → enters code during signup → Firestore records referral_uid
  - Referrer checks dashboard → "Referrals: 12 people" (capped visibility at 100)
  - Points awarded immediately on referred user's first video completion
- **Constraints:**
  - Max 500 referral points/month per referrer (caps at ~10 referrals)
  - Same user can't refer twice
  - No self-referral (system checks device ID + IP)

#### 5.8 Push Notifications
- **Channels:**
  - High-priority: Streak warnings, duel breakage
  - Medium: New coupons, referral rewards
  - Low: App tips, surveys
- **Frequency Cap:** Max 3 notifications/day (user adjustable)
- **Schedule:** Morning (9 AM), midday (12 PM), evening (6 PM) IST
- **Opt-out:** Toggle per channel type in settings

#### 5.9 Basic Analytics & Logging
- **Firebase Analytics Events:**
  - `app_open` – App launch
  - `video_started` – User begins watching
  - `video_completed` – Full video watched
  - `points_earned` – Points transaction logged
  - `coupon_viewed` – Coupon card tapped
  - `coupon_redeemed` – Coupon code generated
  - `duel_invited` – Friend duel invitation sent
  - `duel_accepted` – Duel accepted by friend
  - `upi_cashout_requested` – UPI payout initiated
  - `referral_signup` – Referred user installed
  - `streak_broken` – Streak reset due to missed login
- **Crash Reporting:** Firebase Crashlytics
- **User Session Tracking:**
  - Session duration
  - Screen flow heatmap
  - Feature adoption (% who used duels, referrals, cashout)
- **Cohort Analysis:** DAU/MAU by signup date, retention curves (D1, D3, D7, D14, D30)
- **Monitoring Dashboards:**
  - Real-time: Active users, errors, crash rate
  - Daily: DAU, MAU, key transactions
  - Weekly: Retention cohorts, funnel conversion

### SHOULD HAVE (Phase 1+, Lower Priority)
1. **FAQ & Help Section** (in-app static pages)
2. **Privacy Policy & T&Cs Acceptance** (mandatory on first app open)
3. **User Profile Edit** (city, phone update)
4. **Ad Serving by Partner Preference** (let users choose ad category)
5. **Basic Admin Panel** (coupon CRUD, user reports, payment logs)
6. **Email Notifications** (for high-value events: first cashout, milestone)

### COULD HAVE (Phase 2+)
1. **Leaderboard** (top 50 earners weekly/monthly)
2. **Achievement Badges** (e.g., "First Cashout" badge)
3. **Social Sharing of Streak** (post 7-day streak on Instagram Stories)
4. **AI Content Recommendations** (after 10k+ user data)
5. **Premium/Paid Tier** (ad-free, higher earning multiplier)
6. **In-App Chat** (friend messages within duel interface)
7. **50+ Coupon Partners** (expanded e-commerce, food delivery)

### WON'T HAVE (Phase 1)
- iOS app (Phase 2)
- Web platform (Phase 2)
- Video upload by users
- Multiple language support
- Live streaming
- Payments gateway integration (UPI via RazorPay only)
- Cryptocurrency rewards

---

## 6. Non-Functional Requirements

| Requirement | Target | Rationale |
|-------------|--------|-----------|
| **App Performance** | Launch < 2 sec; video load < 3 sec; ad serve < 1 sec | Ensure 80%+ engagement |
| **Uptime** | 99.5% (4.38 hrs downtime/month max) | Firebase auto-scaling |
| **Concurrent Users** | 10,000 by Phase 1 end (scales to 100k Phase 2) | Firestore auto-scaling, Cloud Run instances |
| **Data Security** | AES-256 PII encryption (phone, UPI); SOC 2 Type II by Phase 2 | Comply with DPDP Act 2023 |
| **API Latency** | p95 < 500 ms | Cloud Run + Firestore indexing |
| **Storage** | App size < 50 MB; Cloud Firestore auto-scaling | Lightweight Kotlin + Compose |
| **Network** | Support 2G+ connectivity; offline caching for non-critical features | India rural markets |
| **Compliance** | GDPR-ready (by Phase 2); India DPDP Act 2023 draft | Legal + business requirements |
| **Monitoring** | Google Cloud Logging, alerts on error rate > 5% | Real-time visibility |
| **Testing** | ≥70% unit test coverage; 2-week UAT cycle before launch | Quality assurance |

---

## 7. User Stories & Acceptance Criteria

### Story 1: New User Registration & First Video
**As a** new user (Rohan, student)  
**I want to** sign up via OTP and immediately watch a video/ad to earn my first points  
**So that** I can quickly validate the app works and earn a quick coupon

**Acceptance Criteria:**
- [ ] OTP sign-up works with valid Indian phone number
- [ ] SMS arrives within 30 sec
- [ ] First ad plays immediately after registration (no delay > 2 sec)
- [ ] 10 points credited to account immediately upon 100% ad completion
- [ ] User sees "Congratulations! +10 points 🎉" toast notification
- [ ] Points balance updates in real-time on dashboard
- [ ] User can see 3-5 available coupons redeemable with points
- [ ] Spin wheel appears post-watch; user can tap for 2x bonus chance

### Story 2: Redeem Coupon
**As a** casual user (Arjun)  
**I want to** convert 100 points into a Swiggy ₹20 discount code  
**So that** I can use the code on my next food order without friction

**Acceptance Criteria:**
- [ ] Coupon shown with clear "Redeem" button
- [ ] Redemption button disabled if user has < required points
- [ ] After redemption, 100 points deducted; coupon code displayed (e.g., "REEL20")
- [ ] Code copyable to clipboard (one-tap)
- [ ] Redemption receipt sent via SMS (coupon code + 30-day expiry)
- [ ] Coupon marked as "redeemed" in history with timestamp
- [ ] Spin wheel bonus applied (if eligible)

### Story 3: Maintain Solo Streak
**As a** casual user (Arjun)  
**I want to** maintain a daily login streak and see my bonus multiplier increase  
**So that** I earn more points and feel motivated to return daily

**Acceptance Criteria:**
- [ ] First login = 20 pts (no multiplier)
- [ ] Day 2 login = 20 pts + 5% bonus (= 21 pts)
- [ ] Day 3 = 20 pts + 10% bonus (= 22 pts)
- [ ] Day 7 = 20 pts + 30% bonus (= 26 pts); max bonus then resets
- [ ] Streak counter visible on dashboard (e.g., "7-day streak 🔥")
- [ ] Miss 1 day = streak broken; reset to 0
- [ ] Warning notification sent 24 hrs before reset ("Login today to keep your streak!")
- [ ] Warning shown if user hasn't logged in that day (red alert badge on app icon)

### Story 4: Friend Duel Streak (NEW - UNIQUE FEATURE)
**As a** side hustler (Shreya)  
**I want to** invite a friend to a mutual daily login duel where we both earn 2x bonus if we both login daily  
**So that** we compete socially, stay motivated together, and earn passive income

**Acceptance Criteria:**
- [ ] "Invite Duel" button available on dashboard
- [ ] User can search friends by phone/name or share duel link
- [ ] Friend receives notification: "Shreya invited you to duel! 🔥 [Accept] [Decline]"
- [ ] Upon acceptance, duel starts with "Day 1" counter
- [ ] Both users login same day → Duel badge updates (e.g., "3🔥🔥🔥") + 2x multiplier on all points
- [ ] One user misses login → Duel breaks for both; notification "😢 Duel broken with Shreya"
- [ ] Dashboard shows: "Your Duels: Shreya (3🔥🔥🔥), Rohan (1🔥), Arjun (6🔥🔥🔥🔥🔥🔥)"
- [ ] Max 5 active duels per user
- [ ] Users can view duel history (last login dates, streaks archived)
- [ ] Cannot duel same person twice simultaneously

### Story 5: Refer Friend & Earn Bonus
**As a** side hustler (Shreya)  
**I want to** share my referral code with friends on Instagram and earn 50 points per signup  
**So that** I earn passive income without watching ads myself

**Acceptance Criteria:**
- [ ] Referral code generated & displayed (e.g., "SHREYA123")
- [ ] "Share" button opens pre-filled templates: WhatsApp, Instagram, SMS
- [ ] WhatsApp message: "Earn ₹100 coupons! Download ReelRewards. Use code SHREYA123 [link]"
- [ ] When referred user installs and enters code, both get notified immediately
- [ ] Referrer gets 50 pts; referred user gets 25 pts signup bonus
- [ ] Referral count displayed on dashboard (e.g., "12 friends referred")
- [ ] Referral earnings do NOT exceed 500 pts/month (cap enforced backend)
- [ ] Referral history shows list of referred users + earning date

### Story 6: Cash Out via UPI
**As a** side hustler (Shreya)  
**I want to** convert ₹500+ points to real money via UPI  
**So that** I can receive passive income directly to my bank account

**Acceptance Criteria:**
- [ ] Minimum cashout threshold clearly shown (₹100 min = 100 pts)
- [ ] "Cash Out" button available only if balance ≥ ₹100
- [ ] User enters UPI ID (e.g., shreya@okhdfcbank)
- [ ] Confirmation popup: "Confirm ₹500 withdrawal to [UPI ID]?"
- [ ] Transaction processed within 1-3 business days
- [ ] User receives SMS confirmation + in-app receipt (Transaction ID: TXN123456)
- [ ] Cashout option disabled if limit reached (₹5k/month cap)
- [ ] Failure handling: Invalid UPI → Error message + retry

---

## 8. Technical Approach

### Architecture Overview

```
┌──────────────────────────────────────────────────────────────┐
│                      Android App (Kotlin)                    │
│                    Google Play Store Release                 │
│                                                               │
│  ├─ Screens: Login, Dashboard, Coupon Feed, Redeem,         │
│  │           Profile, Duel Invites, Streak Widget            │
│  ├─ Libraries: Firebase Auth, Firestore, Analytics           │
│  ├─ AdMob SDK (Rewarded Videos)                             │
│  ├─ RazorPay SDK (UPI Payouts)                              │
│  └─ MVVM + Jetpack Compose + Hilt DI                        │
└──────────────────┬───────────────────────────────────────────┘
                   │
        ┌──────────┴──────────┬──────────────┐
        │                     │              │
┌───────▼────────┐   ┌───────▼────────┐  ┌─▼──────────────┐
│  Firebase Auth │   │   Firestore    │  │  Google AdMob  │
│  (OTP + Google │   │   (Real-time   │  │  (Ad Serving)  │
│   Sign-In)     │   │   Database)    │  │                │
└────────────────┘   └──────┬─────────┘  └────────────────┘
                            │
        ┌───────────────────┴────────────────┐
        │                                    │
┌───────▼────────────┐          ┌───────────▼────────┐
│  Firebase Cloud    │          │   RazorPay API     │
│  Run Backend       │          │  (UPI Payout)      │
│  (Node.js/Express) │          │                    │
│  ├─ Auth service   │          └────────────────────┘
│  ├─ Points ledger  │
│  ├─ Duel engine    │
│  └─ Coupon API     │
└────────┬───────────┘
         │
┌────────▼──────────────────────────────────┐
│        Cloud Firestore Data Layer         │
│                                           │
│  ├─ users {uid, phone, name, city,      │
│  │   points_balance, referral_code}     │
│  │                                       │
│  ├─ points_ledger {user_id, reason,     │
│  │   points_delta, timestamp}           │
│  │                                       │
│  ├─ streaks {user1_uid, user2_uid,      │
│  │   days, multiplier, last_login1/2}   │
│  │                                       │
│  ├─ coupons {coupon_id, partner,        │
│  │   discount%, points_cost, expiry}    │
│  │                                       │
│  └─ referrals {referrer_uid, referred_  │
│     uid, bonus_awarded, timestamp}      │
└────────────────────────────────────────────┘

┌────────────────────────────────────────────┐
│   Monitoring & Analytics                   │
│                                            │
│  ├─ Firebase Analytics (events, funnels)  │
│  ├─ Google Cloud Logging (backend logs)   │
│  ├─ Sentry (crash reporting)              │
│  └─ BigQuery (data warehouse)             │
└────────────────────────────────────────────┘
```

### Tech Stack

| Component | Technology | Why |
|-----------|-----------|-----|
| **Frontend** | Android (Kotlin) | Native perf, Play Store reach, 95% Indian smartphone share |
| **UI Framework** | Jetpack Compose | Modern, reactive, fast iteration |
| **Backend** | Node.js + Express | Rapid dev, Firebase integration, serverless-ready |
| **Database** | Firestore | Real-time sync, auto-scale, built-in security |
| **Auth** | Firebase Auth + Custom OTP | SMS-based (India-standard), Google Sign-In backup |
| **Analytics** | Google Analytics 4 + Firebase | Native AdMob integration, cohort analysis |
| **Ad Network** | Google AdMob | Highest India eCPM (₹800-1,500), compliant |
| **Payments** | RazorPay API | UPI + instant KYC, India-first |
| **Hosting** | Google Cloud Run | Serverless, auto-scale, pay-per-use |
| **Monitoring** | Cloud Logging + Sentry | Real-time errors, uptime alerts |
| **DI Framework** | Hilt | Android dependency injection best practice |
| **Testing** | JUnit + Espresso | Unit + UI testing |

### Development Timeline (9 Weeks)

**Week 1-2: Backend Foundation**
- Firebase project setup (Auth, Firestore, Analytics)
- Firestore schema design + security rules
- Node.js backend scaffolds (auth, points, coupons, duel APIs)
- Start AdMob & RazorPay integration research

**Week 3-4: Android UI**
- Android project setup (MVVM, Hilt, Compose)
- Screens: Login, Dashboard, Coupon Feed, Profile
- Navigation flow implementation
- Streak widget UI with fire emojis

**Week 5-6: Core Logic & Ad Integration**
- AdMob rewarded video integration
- Points ledger logic + Firestore sync
- **Bidirectional duel streak engine** (cron jobs for daily validation)
- UPI redemption flow (RazorPay sandbox testing)
- Coupon redemption backend

**Week 7: Notifications & Analytics**
- Push notifications (Firebase Cloud Messaging)
- Firebase Analytics event tracking
- Crash reporting (Sentry integration)
- Referral attribution setup

**Week 8: Testing & QA**
- Security audit (PII encryption, API auth)
- Load testing (1,000 concurrent users)
- UAT cycle (50 beta testers, 1 week)
- Play Store compliance check (rating, privacy policy)

**Week 9: Launch Preparation**
- Final bug fixes
- Play Store submission (includes 24-48 hrs review time)
- Soft launch to 100 testers
- Hotfix queue ready
- Public launch + Day 1 monitoring

---

## 9. Dependencies & Assumptions

### External Dependencies
1. **Google AdMob Account** – Approval required (1-2 weeks for new publishers)
2. **RazorPay Business Account** – UPI payout (1 week KYC for business verification)
3. **Coupon Partner APIs** – Swiggy, Zomato onboarding (2-4 weeks typical)
4. **Firebase Project** – Free tier scales to 10k users
5. **Google Cloud Credits** – ₹0 setup; free tier limits generous
6. **Google Play Developer Account** – ₹25 one-time registration

### Assumptions
- Users have stable internet (2G+) for video streaming
- Indian phone numbers with SMS capability
- Users comfortable with UPI payments
- AdMob will approve rewarded ads vertical (70% approval rate historically)
- No major competitor app launch during 9-week window
- Initial seed coupons can be generated manually (no real-time API integration Week 1)
- Phase 1 focuses on organic acquisition (no paid ads)

### Risks & Mitigation

| Risk | Likelihood | Impact | Mitigation |
|------|------------|--------|-----------|
| **AdMob account rejected** | Medium (30%) | High – No ad revenue | Pre-apply immediately; Meta Audience Network backup |
| **Low D7 retention < 30%** | Medium (50%) | High – Slows viral growth | Streak duels + spin wheel bonus boost retention; test Week 6 |
| **Ad fraud (bots, repeated clicks)** | Medium (40%) | High – Revenue loss | Rate limiting (1 per 15 min), device fingerprinting, CaptchaV3 |
| **UPI payout failure/delays** | Low (20%) | Medium – User churn | RazorPay SLA 1-3 days; clear T&Cs; email updates |
| **Duel streak complexity bugs** | Medium (35%) | Medium – Poor UX | Extensive Week 6 testing; simplify to solo streaks fallback |
| **Data breach/PII leakage** | Low (10%) | Critical – Regulatory | AES-256 encryption, IP whitelisting, regular audits |
| **Coupon partner API delays** | Medium (40%) | Medium – Feature incomplete | Start integration Week 1; manual coupons backup |

---

## 10. Success Metrics & Monitoring

### Primary Metrics (Week 9 Targets)
- **DAU:** 200+ users
- **MAU:** 500-1,000 users
- **Video Completion Rate:** ≥90%
- **Monthly Gross Revenue:** ₹5,000-15,000
- **User Retention (D7):** ≥40%
- **Viral Coefficient:** ≥15% (referral-driven)
- **Duel Participation:** ≥20% of DAU
- **Coupon Redemption Rate:** ≥15%

### Secondary Metrics
- **Average Points Earned (daily):** 50-100 per DAU
- **UPI Cashout Rate:** ≥5% of MAU
- **App Store Rating:** ≥4.0 stars (50+ reviews)
- **Session Duration:** 8-12 min average
- **Crash Rate:** <2%

### Monitoring Tools
- **Firebase Analytics Dashboard** – Real-time DAU, session, funnels
- **Google AdMob Dashboard** – Impressions, CTR, eCPM, fill rate
- **Google Cloud Logging** – Backend errors, latency, uptime
- **Sentry Dashboard** – Crash tracking, error frequency
- **Custom Analytics Sheet** – Manual KPI rollup weekly

### Reporting Cadence
- **Daily:** DAU, ad impressions, crashes (Slack alert if >5% error rate)
- **Weekly:** Retention cohort, referral signups, revenue, user feedback
- **Monthly:** Full KPI review, phase retrospective, Phase 2 roadmap

---

## 11. Go-to-Market & Launch Strategy

### Organic Acquisition (Phase 1 Focus)

**Play Store Optimization (ASO)**
- **Title:** "ReelRewards – Watch Ads, Earn Coupons & UPI Cash 💰"
- **Keywords (15 target):** "watch videos earn money India," "free coupons app," "earn UPI cash," "Swiggy coupons," "Zomato discount app," "earn watching reels," "money for reels," "free food coupons," "cash back app India," etc.
- **Screenshots:** Show coupon redemption, UPI payout, streak/duel visuals, quick wins
- **Description:** Emphasize "₹100 instant UPI payout," "Swiggy/Zomato coupons," "friend duels," "no hidden fees"
- **Rating Target:** 4.0+ stars by Week 9 (gather reviews via push notifications)

**Instagram Reels Content (Your Strength)**
- Post 30-60 sec demo Reels 2x/week:
  - Week 1-3: "How to earn ₹500 watching reels" + app demo
  - Week 4-6: User testimonials (fake or real) + "friend duel challenges"
  - Week 7-9: Countdown Reels ("Launch in 3 days!") + streaks celebration
- Use hashtags: #EarnMoneyIndia #FreeCoupons #SidehustleIndia #ReelRewards
- Tag friends in comments → duel them for viral loop

**Reddit + Quora Seeding**
- Post in r/beermoneyindia, r/sidehustle (2x/month, no spam)
- Answer Quora questions about "make money watching videos India" (1 answer/week)
- Be authentic: "I built an app..." vs "check this app..."

**Referral + Duel Loop (Viral)**
- Design Reel templates: WhatsApp stickers, Instagram templates
- "Streak Competition": First user to 7-day duel streak → ₹500 bonus (prize pool ₹2-3k)
- Weekly leaderboard post (informal)

### Launch Milestones
- **Week 8:** Soft launch to 50-100 beta testers (friends, family, Reddit)
- **Week 9:** Public Play Store launch; push 3 Reels on Instagram; seed Reddit post
- **Week 10+:** Scale organic based on early metrics

### Phase 1 Marketing Budget
- **Paid Ads:** ₹0 (organic only)
- **Time Investment:** 30-40 hrs (Reels creation, Play Store copy, community seeding)
- **Swag/Rewards:** ₹2-3k (optional duel competition prize pool)

---

## 12. Release Criteria & Sign-Off

### Pre-Launch Checklist
- [ ] Android app builds successfully (no compiler errors)
- [ ] All 6 user stories have passing acceptance criteria
- [ ] Firebase Analytics events firing correctly
- [ ] AdMob ads displaying with ≥90% fill rate
- [ ] UPI payout tested end-to-end (test transaction via RazorPay sandbox)
- [ ] Security audit completed (PII encryption verified)
- [ ] Privacy Policy & T&Cs published in-app
- [ ] Play Store listing complete (title, description, 5+ screenshots, rating)
- [ ] Duel streak logic thoroughly tested (both users, edge cases, resets)
- [ ] Crash rate < 2% in beta (100 testers, 1+ week)
- [ ] D7 retention validated in beta (40%+ target)
- [ ] Load testing passed (1,000 concurrent users)
- [ ] Stakeholder sign-off obtained

### Deployment Plan
- **Week 9 (Friday):** Play Store submission (review typically 24-48 hrs)
- **Week 9 (Monday):** Public launch live
- **Week 10:** Monitor for crashes, user feedback; hotfix sprint if needed

---

## 13. Roadmap (Phase 2+)

### Phase 2 (Weeks 10-20, April-June 2026)
- iOS app launch
- 50+ coupon partners (API integrations)
- Web platform (progressive web app)
- Leaderboard + badges
- ML-based content recommendations

### Phase 3 (July-September 2026)
- Multi-language support (Hindi, Tamil, Telugu)
- Affiliate marketing partner program
- Creator monetization (upload ASMR/reels for views)
- Advanced analytics dashboard

### Long-Term Vision (2027+)
- Expansion to Southeast Asia
- Virtual gifting / e-commerce integration
- Blockchain-based rewards (optional)

---

## 14. Appendix: Glossary

| Term | Definition |
|------|-----------|
| **MAU** | Monthly Active Users (logged in ≥1 day in 30 days) |
| **DAU** | Daily Active Users (logged in ≥1 time per calendar day) |
| **eCPM** | Effective Cost Per Mille (revenue per 1,000 ad impressions) |
| **D7 Retention** | % of users who return ≥1 day by day 7 post-install |
| **Viral Coefficient** | Average # of new users referred by 1 user |
| **Duel Streak** | Bidirectional friend streak; both must login daily |
| **Points** | Virtual currency; 1 point ≈ ₹1 |
| **Coupon** | Discount code redeemable for points |
| **UPI** | Unified Payments Interface (India's instant payment system) |
| **ASO** | App Store Optimization (keywords, screenshots, title) |

---

## 15. Document Sign-Off

| Role | Name | Date | Approval |
|------|------|------|----------|
| Product Lead / Founder | [Your Name] | 21-Jan-2026 | ✓ |
| Technical Lead | [Backend Dev] | [TBD] | ✓ |

---

**Document Distribution:** Founders, Development Team, Stakeholders  
**Confidentiality:** Internal – Do Not Share Publicly  
**Next Review:** February 21, 2026 (Phase 1 mid-point checkpoint)

---

# END OF BRD v1.1