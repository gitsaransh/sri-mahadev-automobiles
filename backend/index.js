const express = require('express');
const admin = require('firebase-admin');
const cors = require('cors');
require('dotenv').config();

const path = require('path');

const app = express();
app.use(cors());
app.use(express.json());
app.use(express.static(path.join(__dirname, 'public'))); // Serve Web Preview

// Explicitly serve index.html for root
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'public', 'index.html'));
});

// Initialize Firebase Admin (with Mock Mode fallback)
let serviceAccount;
let db;
let mockMode = false;

try {
    if (process.env.FIREBASE_CONFIG_JSON) {
        // Production: Load from Environment Variable (Railway/Heroku)
        const config = JSON.parse(process.env.FIREBASE_CONFIG_JSON);
        admin.initializeApp({
            credential: admin.credential.cert(config),
            databaseURL: process.env.FIREBASE_DATABASE_URL
        });
    } else {
        // Local: Load from file
        serviceAccount = require('./serviceAccountKey.json');
        admin.initializeApp({
            credential: admin.credential.cert(serviceAccount),
            databaseURL: process.env.FIREBASE_DATABASE_URL
        });
    }
    db = admin.firestore();
    console.log("✅ Firebase Connected (Production Mode)");
} catch (e) {
    console.warn("⚠️ CRITICAL: serviceAccountKey.json missing. Switching to MOCK MODE.");
    mockMode = true;
    // Mock database implementation for local testing
    const mockData = {
        users: { "mock_user_123": { name: "Mock User", points_balance: 500, solo_streak_days: 5, phone_number: "+919999999999", uid: "mock_user_123" } },
        duel_invites: [], // Array of objects
        streaks: {}, // Map of objects
        coupons: [
            { id: "cpn_1", partner: "Amazon", points_cost: 100, description: "₹50 Gift Card", is_active: true, coupon_code: "AMZ50FREE", validity_days: 30, image_url: "amazon_logo.png" },
            { id: "cpn_2", partner: "Swiggy", points_cost: 200, description: "Flat ₹100 Off", is_active: true, coupon_code: "SWIGGY100", validity_days: 15, image_url: "swiggy_logo.png" },
            { id: "cpn_3", partner: "Myntra", points_cost: 500, description: "15% Off Styles", is_active: true, coupon_code: "STYLE15", validity_days: 60, image_url: "myntra_logo.png" },
            { id: "cpn_4", partner: "BookMyShow", points_cost: 150, description: "Buy 1 Get 1", is_active: true, coupon_code: "BOGO150", validity_days: 45, image_url: "bms_logo.png" },
            { id: "cash_1", partner: "UPI Cash", points_cost: 50, description: "Get ₹10 Payout to UPI", is_active: true, coupon_code: "TXN_PENDING", validity_days: 0, image_url: "upi_logo.png" }
        ],
        user_coupons: []
    };

    db = {
        collection: (name) => ({
            doc: (id) => {
                // Auto-generate ID if not provided for adds
                const docId = id || 'mock_doc_' + Math.floor(Math.random() * 100000);
                return {
                    id: docId,
                    get: async () => {
                        let data;
                        if (name === 'users') data = mockData.users[docId];
                        else if (name === 'streaks') data = mockData.streaks[docId];
                        else if (name === 'duel_invites') data = mockData.duel_invites.find(i => i.id === docId);
                        else if (name === 'coupons') data = mockData.coupons.find(c => c.id === docId);

                        return { exists: !!data, data: () => data };
                    },
                    set: async (data) => {
                        if (name === 'users') mockData.users[docId] = data;
                        else if (name === 'streaks') mockData.streaks[docId] = data;
                        else if (name === 'duel_invites') {
                            const existingIndex = mockData.duel_invites.findIndex(i => i.id === docId);
                            if (existingIndex >= 0) mockData.duel_invites[existingIndex] = { ...data, id: docId };
                            else mockData.duel_invites.push({ ...data, id: docId });
                        }
                        else if (name === 'user_coupons') mockData.user_coupons.push(data);
                    },
                    update: async (data) => {
                        // Simple merge for mock
                        if (name === 'users' && mockData.users[docId]) Object.assign(mockData.users[docId], data);
                        else if (name === 'streaks' && mockData.streaks[docId]) Object.assign(mockData.streaks[docId], data);
                        else if (name === 'duel_invites') {
                            const item = mockData.duel_invites.find(i => i.id === docId);
                            if (item) Object.assign(item, data);
                        }
                    },
                    collection: (subname) => ({ doc: () => ({ set: async () => { } }) })
                };
            },
            where: (field, op, val) => ({
                get: async () => {
                    // Very basic mock filter for streaks
                    if (name === 'streaks') {
                        const docs = Object.values(mockData.streaks).filter(s => s[field] === val).map(d => ({ id: d.id, data: () => d }));
                        return { docs, size: docs.length, forEach: (cb) => docs.forEach(cb) };
                    }
                    if (name === 'coupons') {
                        return { docs: mockData.coupons.map(c => ({ id: c.id, data: () => c })), size: mockData.coupons.length, forEach: ((cb) => mockData.coupons.forEach(c => cb({ id: c.id, data: () => c }))) };
                    }
                    return { docs: [], size: 0, forEach: () => { } };
                }
            }),
            get: async () => { // For our mock allInvites call
                if (name === 'duel_invites') {
                    return { docs: mockData.duel_invites.map(d => ({ data: () => d })) };
                }
                return { docs: [] };
            }
        }),
        runTransaction: async (cb) => {
            // Simple mock transaction: just run the callback with the db interface
            // This ignores atomicity but works for partial logic
            await cb(db);
        },
        batch: () => ({ update: () => { }, commit: async () => { } })
    };
}

// --- MIDDLEWARE: AUTH CHECK ---
const authenticateToken = async (req, res, next) => {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];

    if (!token) return res.status(401).send({ error: "Unauthorized" });

    // BYPASS: Allow manual mock tokens for local testing
    if (token.startsWith("mock_")) {
        // Parse the dummy phone number if possible, or use default
        const phonePart = token.replace("mock_token_for_", "");
        req.user = {
            uid: "mock_user_" + phonePart,
            phone_number: phonePart.length > 5 ? phonePart : "+919999999999"
        };
        return next();
    }

    // MOCK MODE AUTH (Global Flag)
    if (mockMode) {
        req.user = { uid: "mock_user_123", phone_number: "+919999999999" };
        return next();
    }

    try {
        const decodedToken = await admin.auth().verifyIdToken(token);
        req.user = decodedToken;
        next();
    } catch (error) {
        res.status(403).send({ error: "Invalid Token" });
    }
};

// --- AUTH & PROFILE (RR-06, RR-07) ---
app.post('/api/auth/verify-otp', authenticateToken, async (req, res) => {
    const { idToken, name, city, referralCode } = req.body;

    try {


        // Use the user context already verified/mapped by the middleware
        const uid = req.user.uid;
        const phone_number = req.user.phone_number;

        const userRef = db.collection('users').doc(uid);
        const userDoc = await userRef.get();

        if (!userDoc.exists) {
            // Create New Profile
            const newUser = {
                uid,
                phone: phone_number || '',
                name: name || 'User',
                city: city || 'Unknown',
                points_balance: 0,
                total_points_earned: 0,
                referral_code: (name ? name.substring(0, 4) : 'RR') + Math.floor(1000 + Math.random() * 9000),
                solo_streak_days: 0,
                created_at: mockMode ? new Date() : admin.firestore.FieldValue.serverTimestamp()
            };
            await userRef.set(newUser);

            if (referralCode) {
                // Referral Logic Placeholder
            }

            return res.status(201).send(newUser);
        }

        // --- STREAK UPDATE LOGIC (CRITICAL FOR RR-13) ---
        // When user logs in, we MUST update their timestamp in any active streaks
        try {
            const streaks1 = await db.collection('streaks').where('status', '==', 'active').where('user1_uid', '==', uid).get();
            const streaks2 = await db.collection('streaks').where('status', '==', 'active').where('user2_uid', '==', uid).get();

            if (!streaks1.empty || !streaks2.empty) {
                const batch = db.batch();

                streaks1.forEach(doc => {
                    batch.update(doc.ref, { last_login_user1: admin.firestore.FieldValue.serverTimestamp() });
                });

                streaks2.forEach(doc => {
                    batch.update(doc.ref, { last_login_user2: admin.firestore.FieldValue.serverTimestamp() });
                });

                await batch.commit();
                console.log(`✅ Updated streak login timestamps for user ${uid}`);
            }
        } catch (streakErr) {
            console.error("Failed to update streak timestamps:", streakErr);
            // Don't block login if this fails
        }

        res.status(200).send(userDoc.data());
    } catch (error) {
        console.error("Auth Error:", error);
        res.status(500).send({ error: "Authentication failed" });
    }
});

app.get('/api/user/profile', authenticateToken, async (req, res) => {
    try {
        const userDoc = await db.collection('users').doc(req.user.uid).get();
        if (!userDoc.exists) return res.status(404).send({ error: "User not found" });
        res.status(200).send(userDoc.data());
    } catch (error) {
        res.status(500).send({ error: "Error fetching profile" });
    }
});

// --- POINTS & VIDEOS (RR-08) ---
app.post('/api/video/watch-complete', authenticateToken, async (req, res) => {
    const { video_id, multiplier = 1 } = req.body; // 'multiplier' here is from Spin Wheel
    const uid = req.user.uid;

    try {
        const userRef = db.collection('users').doc(uid);

        // START TRANSACTION
        await db.runTransaction(async (t) => {
            const userDoc = await t.get(userRef);
            if (!userDoc.exists) throw new Error("User not found");

            // 1. Calculate Duel Bonus (Server-Side verification)
            // Note: In a real high-scale app, we'd cache this on the user profile [active_duel_count]
            // For now, we query.
            const streaks1 = await db.collection('streaks').where('status', '==', 'active').where('user1_uid', '==', uid).get();
            const streaks2 = await db.collection('streaks').where('status', '==', 'active').where('user2_uid', '==', uid).get();
            const activeDuelCount = streaks1.size + streaks2.size;

            const duelMultiplier = activeDuelCount > 0 ? 1.5 : 1.0;

            // 2. Validate Client Multiplier (Spin Wheel)
            // Cap Spin Multiplier to 10x to prevent abuse, but allow standard range
            const spinMultiplier = Math.min(Math.max(multiplier, 1), 10);

            // 3. Calculate Reward
            const basePoints = 10;
            const totalReward = Math.round(basePoints * spinMultiplier * duelMultiplier);

            // 4. Update User Balance
            t.update(userRef, {
                points_balance: admin.firestore.FieldValue.increment(totalReward),
                total_points_earned: admin.firestore.FieldValue.increment(totalReward),
                // Optional: Update simple stats
                active_duels_count: activeDuelCount
            });

            // 5. Log Transaction
            const ledgerRef = userRef.collection('points_ledger').doc();
            t.set(ledgerRef, {
                points_delta: totalReward,
                reason: 'video_watch',
                video_id: video_id || 'manual_trigger',
                breakdown: {
                    base: basePoints,
                    spin_mult: spinMultiplier,
                    duel_mult: duelMultiplier
                },
                timestamp: admin.firestore.FieldValue.serverTimestamp()
            });

            return totalReward;
        });

        // Fetch updated balance for response
        const updatedDoc = await userRef.get();
        res.status(200).send({
            points_awarded: (await userRef.collection('points_ledger').orderBy('timestamp', 'desc').limit(1).get()).docs[0].data().points_delta, // Get exact amount from ledger
            new_balance: updatedDoc.data().points_balance
        });

    } catch (error) {
        console.error("Points Error:", error);
        res.status(500).send({ error: "Failed to award points" });
    }
});

// --- DUELS ENGINE (RR-10, RR-11, RR-12) ---

app.post('/api/duel/invite', authenticateToken, async (req, res) => {
    const { to_phone, to_uid } = req.body;
    const from_uid = req.user.uid;

    try {
        // Fetch sender's name
        const senderDoc = await db.collection('users').doc(from_uid).get();
        const senderName = senderDoc.exists ? senderDoc.data().name : "Unknown User";

        const inviteRef = db.collection('duel_invites').doc();
        const inviteData = {
            id: inviteRef.id, // Ensure ID is in data for mock
            from_uid,
            inviter_name: senderName,
            to_uid: to_uid || null,
            to_phone: to_phone || null,
            status: 'pending',
            created_at: admin.firestore.FieldValue.serverTimestamp()
        };

        await inviteRef.set(inviteData);
        res.status(201).send({ invite_id: inviteRef.id, message: "Invitation sent!" });
    } catch (error) {
        console.error(error);
        res.status(500).send({ error: "Failed to send invitation" });
    }
});

app.post('/api/duel/accept', authenticateToken, async (req, res) => {
    const { invite_id } = req.body;
    const to_uid = req.user.uid;

    try {
        const inviteRef = db.collection('duel_invites').doc(invite_id);
        const inviteDoc = await inviteRef.get();

        if (!inviteDoc.exists || inviteDoc.data().status !== 'pending') {
            return res.status(404).send({ error: "Invite not found or already processed" });
        }

        const { from_uid } = inviteDoc.data();

        // Create the active streak (RR-11)
        const streakId = [from_uid, to_uid].sort().join('_'); // Unique ID for this pair
        const streakRef = db.collection('streaks').doc(streakId);

        await db.runTransaction(async (t) => {
            t.set(streakRef, {
                id: streakId,
                user1_uid: from_uid,
                user2_uid: to_uid,
                status: 'active',
                days: 1,
                multiplier_level: 1,
                last_both_login_date: admin.firestore.FieldValue.serverTimestamp(),
                last_login_user1: admin.firestore.FieldValue.serverTimestamp(),
                last_login_user2: admin.firestore.FieldValue.serverTimestamp(),
                daily_bonus_applied: true,
                created_at: admin.firestore.FieldValue.serverTimestamp()
            });
            t.update(inviteRef, { status: 'accepted', accepted_at: admin.firestore.FieldValue.serverTimestamp() });
        });

        res.status(200).send({ streak_id: streakId, message: "Duel Started! 🔥" });
    } catch (error) {
        console.error("Duel Accept Error:", error);
        res.status(500).send({ error: "Failed to accept duel" });
    }
});

app.get('/api/duel/pending', authenticateToken, async (req, res) => {
    try {
        const uid = req.user.uid;
        const phone = req.user.phone_number;

        // Query by UID or Phone
        // Note: Firestore OR queries are tricky, so we might do two queries or relies on client to be consistent.
        // For simpler MVP, check phone primarily as that's how invites are sent

        // In real Firestore we might need a composite index or separate queries.
        // For Mock DB, we can just filter.
        let invites;
        if (mockMode) {
            // Helper for mock mode filtering since our mock .where() is simple
            const allInvites = await db.collection('duel_invites').get(); // access underlying mock array
            invites = allInvites.docs
                .map(d => d.data())
                .filter(i => (i.to_phone === phone || i.to_uid === uid) && i.status === 'pending');
        } else {
            const invitesSnap = await db.collection('duel_invites')
                .where('to_phone', '==', phone)
                .where('status', '==', 'pending')
                .get();
            invites = invitesSnap.docs.map(doc => ({ id: doc.id, ...doc.data() }));
        }

        res.status(200).send(invites);
    } catch (error) {
        console.error(error);
        res.status(500).send({ error: "Failed to fetch pending requests" });
    }
});

app.get('/api/duel/active', authenticateToken, async (req, res) => {
    try {
        const uid = req.user.uid;
        const streaks1 = await db.collection('streaks').where('status', '==', 'active').where('user1_uid', '==', uid).get();
        const streaks2 = await db.collection('streaks').where('status', '==', 'active').where('user2_uid', '==', uid).get();

        const results = [];
        streaks1.forEach(doc => results.push({ id: doc.id, ...doc.data() }));
        streaks2.forEach(doc => results.push({ id: doc.id, ...doc.data() }));

        res.status(200).send(results);
    } catch (error) {
        res.status(500).send({ error: "Failed to fetch active duels" });
    }
});


// --- COUPON & PAYOUT ENGINE (RR-09) ---
// Razorpay Integration
const Razorpay = require('razorpay');
let razorpay;
if (process.env.RAZORPAY_KEY_ID && process.env.RAZORPAY_KEY_SECRET) {
    razorpay = new Razorpay({
        key_id: process.env.RAZORPAY_KEY_ID,
        key_secret: process.env.RAZORPAY_KEY_SECRET,
    });
    console.log("✅ Razorpay Initialized");
} else {
    console.warn("⚠️ Razorpay Keys missing. Payouts will be MOCKED.");
}

app.get('/api/coupons', authenticateToken, async (req, res) => {
    try {
        const coupons = await db.collection('coupons').where('is_active', '==', true).get();
        const results = [];
        coupons.forEach(doc => results.push({ id: doc.id, ...doc.data() }));
        res.status(200).send(results);
    } catch (error) {
        res.status(500).send({ error: "Failed to fetch coupons" });
    }
});

app.post('/api/coupon/redeem', authenticateToken, async (req, res) => {
    const { coupon_id, upi_id } = req.body;
    const uid = req.user.uid;

    try {
        const userRef = db.collection('users').doc(uid);
        const couponRef = db.collection('coupons').doc(coupon_id);

        await db.runTransaction(async (t) => {
            const userDoc = await t.get(userRef);
            const couponDoc = await t.get(couponRef);

            if (!userDoc.exists || !couponDoc.exists) throw new Error("User or Coupon not found");

            const cost = couponDoc.data().points_cost;
            const balance = userDoc.data().points_balance;
            const partner = couponDoc.data().partner;

            if (balance < cost) throw new Error("Insufficient points");

            // Handle UPI Payout Specifics
            let payoutResult = null;
            if (partner.includes("UPI Cash")) {
                if (!upi_id) throw new Error("UPI ID is required for cashout");

                // --- RAZORPAY X PAYOUT LOGIC (Sandbox/Mock) ---
                if (razorpay) {
                    try {
                        // 1. Create Contact (Mock impl for now, in real RazorpayX you need Contact ID)
                        // const contact = await razorpay.contacts.create(...)

                        // 2. Create Fund Account (Mock)
                        // const fundAccount = await razorpay.fundAccount.create(...)

                        // 3. Create Payout
                        // const payout = await razorpay.payouts.create(...)

                        // For now we just simulate success if keys exist
                        payoutResult = { id: "pout_" + Math.random().toString(36).substring(7), status: "processed" };
                    } catch (rpError) {
                        console.error("Razorpay Error:", rpError);
                        // Fallback to manual processing request
                        payoutResult = { id: "manual_req_" + Date.now(), status: "pending_manual" };
                    }
                } else {
                    payoutResult = { id: "mock_pout_" + Date.now(), status: "mock_success" };
                }
            }

            // Deduct points
            t.update(userRef, { points_balance: admin.firestore.FieldValue.increment(-cost) });

            // Create user coupon / payout record
            const userCouponRef = db.collection('user_coupons').doc();
            const expiry = new Date();
            expiry.setDate(expiry.getDate() + (couponDoc.data().validity_days || 30));

            const redemption = {
                user_id: uid,
                coupon_id: coupon_id,
                coupon_code: payoutResult ? "TXN_DONE" : couponDoc.data().coupon_code,
                partner: partner,
                upi_id: upi_id || null,
                payout_info: payoutResult || null,
                redeemed_at: admin.firestore.FieldValue.serverTimestamp(),
                expiry_date: admin.Timestamp.fromDate(expiry),
                status: 'active'
            };

            t.set(userCouponRef, redemption);

            // Log to points ledger
            const ledgerRef = userRef.collection('points_ledger').doc();
            t.set(ledgerRef, {
                points_delta: -cost,
                reason: 'redemption',
                coupon_id: coupon_id,
                upi_id: upi_id || null,
                timestamp: admin.firestore.FieldValue.serverTimestamp()
            });

            // Should return result outside transaction
            return redemption;
        });

        // Fetch the result we just created (workaround since transaction closure return is tricky in some frameworks, but here it's fine if we just send success)
        // Actually, we'll just send success response.

        let successCode = "CODE123"; // Default
        const couponData = (await couponRef.get()).data();
        if (couponData.partner.includes("UPI")) successCode = "TXN_PENDING";
        else successCode = couponData.coupon_code;

        res.status(200).send({
            message: "Redeemed successfully!",
            code: successCode,
            payout_status: "initiated"
        });

    } catch (error) {
        res.status(400).send({ error: error.message });
    }
});

// --- STREAK VALIDATION CRON (RR-13) ---
// Note: In production, trigger this via Cloud Scheduler or a server-side timer
const validateDailyStreaks = async () => {
    console.log("🔥 Starting Daily Streak Validation...");
    const today = new Date();
    today.setHours(0, 0, 0, 0); // Start of today IST (simulated)

    try {
        const activeStreaks = await db.collection('streaks').where('status', '==', 'active').get();

        const batch = db.batch();

        for (const doc of activeStreaks.docs) {
            const data = doc.data();
            const user1Login = data.last_login_user1.toDate();
            const user2Login = data.last_login_user2.toDate();

            // Check if BOTH logged in recently (within last 24h)
            if (user1Login >= today && user2Login >= today) {
                // CONTINUE STREAK
                batch.update(doc.ref, {
                    days: admin.firestore.FieldValue.increment(1),
                    multiplier_level: Math.min(data.multiplier_level + 1, 7),
                    daily_bonus_applied: true // Ready for today's points
                });
            } else {
                // BREAK STREAK
                batch.update(doc.ref, {
                    status: 'broken',
                    broken_at: admin.firestore.FieldValue.serverTimestamp(),
                    daily_bonus_applied: false
                });
            }
        }

        await batch.commit();
        console.log(`✅ Validated ${activeStreaks.size} streaks.`);
    } catch (error) {
        console.error("Streak Validation Error:", error);
    }
};

// Internal endpoint to trigger cron manually for testing
app.post('/api/admin/trigger-cron', authenticateToken, async (req, res) => {
    // In real app, check for admin role
    await validateDailyStreaks();
    res.status(200).send({ message: "Cron triggered" });
});

// --- CORE HEALTH CHECK ---
app.get('/health', (req, res) => {
    res.status(200).send({ status: 'Online', timestamp: new Date() });
});

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => {
    console.log(`🚀 ReelsReward Engine running on port ${PORT}`);
    console.log(`📍 Health check: http://localhost:${PORT}/health`);
});
