package com.reelrewards.app.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.reelrewards.app.domain.model.SpinMechanic
import com.reelrewards.app.ui.theme.FireOrange
import com.reelrewards.app.ui.theme.RewardGold
import com.reelrewards.app.ui.theme.SurfaceGrey
import com.reelrewards.app.ui.theme.PremiumBlack
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SpinWheelDialog(
    onDismiss: () -> Unit,
    onSpinComplete: (multiplier: Int) -> Unit
) {
    var isSpinning by remember { mutableStateOf(false) }
    var resultText by remember { mutableStateOf("") }
    val rotation = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    Dialog(onDismissRequest = {}) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PremiumBlack, RoundedCornerShape(24.dp))
                .border(2.dp, SurfaceGrey, RoundedCornerShape(24.dp))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Video Completed!",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Spin to multiply your reward",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // --- THE WHEEL ---
                Box(
                    modifier = Modifier.size(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Outer Rim
                    Box(
                        modifier = Modifier
                            .size(280.dp)
                            .background(Color.White.copy(alpha=0.1f), CircleShape)
                    )
                    
                    Canvas(
                        modifier = Modifier
                            .size(260.dp)
                            .rotate(rotation.value)
                    ) {
                        val radius = size.minDimension / 2
                        val center = Offset(size.width / 2, size.height / 2)
                        
                        // Draw 6 Segments
                        val colors = listOf(FireOrange, RewardGold, FireOrange, RewardGold, FireOrange, RewardGold)
                        val labels = listOf("1x", "2x", "1x", "2x", "1x", "2x")
                        
                        for (i in 0..5) {
                            // Draw Arc
                            drawArc(
                                color = colors[i],
                                startAngle = i * 60f,
                                sweepAngle = 60f,
                                useCenter = true,
                                topLeft = Offset(center.x - radius, center.y - radius),
                                size = Size(radius * 2, radius * 2)
                            )
                        }

                        // Draw Text Labels (Native Canvas)
                        drawIntoCanvas { canvas ->
                            val nativeCanvas = canvas.nativeCanvas
                            nativeCanvas.apply {
                                val paint = android.graphics.Paint().apply {
                                    color = android.graphics.Color.WHITE
                                    textSize = 40f
                                    textAlign = android.graphics.Paint.Align.CENTER
                                    typeface = android.graphics.Typeface.create(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD)
                                    setShadowLayer(4f, 0f, 0f, android.graphics.Color.BLACK)
                                }
                                val paintDark = android.graphics.Paint().apply {
                                    color = android.graphics.Color.BLACK
                                    textSize = 40f
                                    textAlign = android.graphics.Paint.Align.CENTER
                                    typeface = android.graphics.Typeface.create(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD)
                                }
    
                                for (i in 0..5) {
                                    save()
                                    // Rotate to the center of the segment
                                    val angle = (i * 60f) + 30f
                                    rotate(angle, center.x, center.y)
                                    
                                    // Draw text
                                    val textRadius = radius * 0.7f
                                    
                                    drawText(
                                        labels[i],
                                        center.x + textRadius,
                                        center.y + 15f,
                                        if(labels[i] == "2x") paintDark else paint
                                    )
                                    restore()
                                }
                            }
                        }
                    }
                    
                    // Center Hub
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(PremiumBlack, CircleShape)
                            .border(4.dp, Color.White, CircleShape)
                    ) {
                        Text(
                           "RR",
                           color = RewardGold,
                           fontWeight = FontWeight.Bold,
                           modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    // Pointer (Top Center)
                    // Note: Pointer should be OUTSIDE the rotating canvas so it stays fixed
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val path = Path().apply {
                            moveTo(size.width / 2, 10f) // Move slightly down
                            lineTo(size.width / 2 - 20f, -30f)
                            lineTo(size.width / 2 + 20f, -30f)
                            close()
                        }
                        drawPath(path, color = Color.White, style = Fill)
                        // Add detailed triangle
                         val pathInner = Path().apply {
                            moveTo(size.width / 2, 10f)
                            lineTo(size.width / 2 - 15f, -25f)
                            lineTo(size.width / 2 + 15f, -25f)
                            close()
                        }
                        drawPath(pathInner, color = FireOrange, style = Fill)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                if (resultText.isNotEmpty()) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            resultText,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = RewardGold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(containerColor = FireOrange),
                            modifier = Modifier.fillMaxWidth().height(50.dp)
                        ) {
                            Text("Collect Reward", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                } else {
                    Button(
                        onClick = {
                            if (!isSpinning) {
                                isSpinning = true
                                scope.launch {
                                    val result = SpinMechanic.calculateResult()
                                    // Logic: 0 deg is 3 o'clock. Top is 270 (-90).
                                    // Center of index i: i*60 + 30
                                    // Target rotation equation: Current + (360 * 5) + Offset
                                    // We want (FinalRotation + SegmentCenter) % 360 = 270
                                    
                                    val segmentAngle = 60f
                                    val segmentCenter = (result.index * segmentAngle) + (segmentAngle / 2)
                                    
                                    // We rotate CLOCKWISE.
                                    // Landing angle = 270 (Top)
                                    // RotationAmount = (Target - Current) 
                                    // But we animate the CANVAS rotation.
                                    // If canvas rotates X degrees, the visual at Top becomes (270 - X).
                                    // We want SegmentCenter to be at 270.
                                    // So, NewPos = SegmentCenter + Rotation. We want NewPos = 270.
                                    // Rotation = 270 - SegmentCenter.
                                    
                                    val targetRotation = (360f * 5) + (270f - segmentCenter)
                                    
                                    rotation.animateTo(
                                        targetValue = targetRotation,
                                        animationSpec = tween(durationMillis = 3000, easing = FastOutSlowInEasing)
                                    )
                                    
                                    resultText = result.label
                                    onSpinComplete(result.multiplier)
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = FireOrange),
                        enabled = !isSpinning,
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(if (isSpinning) "Spinning..." else "SPIN TO WIN!", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
