package com.reelrewards.app.domain.model

enum class SpinResult(val label: String, val multiplier: Int, val weight: Double) {
    STANDARD("10 Points", 1, 0.40),    // 40%
    BONUS("15 Points!", 15, 0.30),     // 30% (Represented as 1.5x roughly for logic, but let's pass integer)
    MEGA("20 Points!!", 2, 0.30)       // 30% (2x)
}

// Note: Backend handles multiplier as integer for now (1 or 2).
// To support 1.5x cleanly without float issues, we might strictly stick to 1x and 2x or update backend.
// Backend says: const safeMultiplier = Math.min(Math.max(multiplier, 1), 2);
// So passing 1.5 might be truncated or floor'd. Let's stick to 1x and 2x for now to ensure backend compatibility, 
// or simpler: 1x and 2x.

// REVISED PLAN: 6 segments: [1x, 2x, 1x, 2x, 1x, 2x]. 
// Let's keep it simple but make it visually look like a "game of chance".

enum class SpinOutcome(val label: String, val multiplier: Int, val index: Int) {
    SEGMENT_1("10 Points", 1, 0),
    SEGMENT_2("DOUBLE POINTS!", 2, 1),
    SEGMENT_3("10 Points", 1, 2),
    SEGMENT_4("DOUBLE POINTS!", 2, 3),
    SEGMENT_5("10 Points", 1, 4),
    SEGMENT_6("DOUBLE POINTS!", 2, 5)
}

object SpinMechanic {
    fun calculateResult(): SpinOutcome {
        // Uniform randomness for the distinct segments makes it feel fair
        val values = SpinOutcome.values()
        return values[values.indices.random()]
    }
}
