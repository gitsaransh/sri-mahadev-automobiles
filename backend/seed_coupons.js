const admin = require('firebase-admin');
const serviceAccount = require('./serviceAccountKey.json');
require('dotenv').config();

// Connect to REAL Firestore
admin.initializeApp({
    credential: admin.credential.cert(serviceAccount)
});

const db = admin.firestore();

const coupons = [
    { id: "cpn_1", partner: "Amazon", points_cost: 100, description: "₹50 Gift Card", is_active: true, coupon_code: "AMZ50FREE", validity_days: 30, image_url: "amazon_logo.png" },
    { id: "cpn_2", partner: "Swiggy", points_cost: 200, description: "Flat ₹100 Off", is_active: true, coupon_code: "SWIGGY100", validity_days: 15, image_url: "swiggy_logo.png" },
    { id: "cpn_3", partner: "Myntra", points_cost: 500, description: "15% Off Styles", is_active: true, coupon_code: "STYLE15", validity_days: 60, image_url: "myntra_logo.png" },
    { id: "cpn_4", partner: "BookMyShow", points_cost: 150, description: "Buy 1 Get 1", is_active: true, coupon_code: "BOGO150", validity_days: 45, image_url: "bms_logo.png" },
    { id: "cash_1", partner: "UPI Cash", points_cost: 50, description: "Get ₹10 Payout to UPI", is_active: true, coupon_code: "TXN_PENDING", validity_days: 0, image_url: "upi_logo.png" }
];

async function seedCoupons() {
    console.log("🌱 Seeding Coupons to Firestore...");
    const batch = db.batch();

    coupons.forEach(coupon => {
        const ref = db.collection('coupons').doc(coupon.id);
        batch.set(ref, coupon);
    });

    await batch.commit();
    console.log("✅ Seeded 5 coupons successfully!");
    process.exit(0);
}

seedCoupons();
