package com.example.imageviewer.shared

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue

@Composable
fun Modifier.shimmer(
  durationMillis: Int = 1300,
): Modifier {
  val transition = rememberInfiniteTransition()
  val translateAnimation by transition.animateFloat(
    initialValue = 0f,
    targetValue = 500f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = durationMillis,
        easing = LinearEasing,
      ),
      repeatMode = RepeatMode.Restart,
    ),
  )

  return drawBehind {
    drawRect(
      brush = Brush.linearGradient(
        colors = listOf(
          Color.LightGray.copy(alpha = 0.2f),
          Color.LightGray.copy(alpha = 1.0f),
          Color.LightGray.copy(alpha = 0.2f),
        ),
        start = Offset(x = translateAnimation, y = translateAnimation),
        end = Offset(x = translateAnimation + 100f, y = translateAnimation + 100f),
      )
    )
  }
}
