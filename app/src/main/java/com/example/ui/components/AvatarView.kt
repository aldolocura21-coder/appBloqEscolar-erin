package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.model.AvatarConfig

object AvatarPalette {
    val skinTones = listOf(
        Color(0xFFFFDFBA), // Claro
        Color(0xFFF1C27D), // Medio Cálido
        Color(0xFFE0AC69), // Dorado
        Color(0xFFC68642), // Bronce
        Color(0xFF8D5524)  // Moreno Oscuro
    )

    val hairColors = listOf(
        Color(0xFF2A2118), // Negro Azabache
        Color(0xFF5D4037), // Castaño Chocolate
        Color(0xFFE5A65E), // Rubio Dorado
        Color(0xFFBF360C), // Pelirrojo / Cobrizo
        Color(0xFF0284C7)  // Fantasía Azul Neón
    )

    val outfitColors = listOf(
        Color(0xFF1E3A8A) to Color(0xFFE2E8F0), // Uniforme Escolar Azul Marino y Blanco
        Color(0xFFE11D48) to Color(0xFF18181B), // Sudadera Urbana Roja y Negra
        Color(0xFF0D9488) to Color(0xFFF8FAFC), // Bata de Científico Menta
        Color(0xFF7C3AED) to Color(0xFFFBBF24)  // Traje Espacial Galáctico Violeta y Dorado
    )
}

@Composable
fun AvatarView(
    config: AvatarConfig,
    size: Dp = 120.dp,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFFEFF6FF)
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val w = this.size.width
            val h = this.size.height

            val skin = AvatarPalette.skinTones.getOrElse(config.skinToneIndex) { AvatarPalette.skinTones[0] }
            val hair = AvatarPalette.hairColors.getOrElse(config.hairColorIndex) { AvatarPalette.hairColors[0] }
            val (outfitMain, outfitAccent) = AvatarPalette.outfitColors.getOrElse(config.outfitIndex) { AvatarPalette.outfitColors[0] }

            // 1. Cape Accessory (Behind body)
            if (config.accessoryIndex == 4) {
                val capePath = Path().apply {
                    moveTo(w * 0.2f, h * 0.65f)
                    lineTo(w * 0.05f, h * 1.0f)
                    lineTo(w * 0.95f, h * 1.0f)
                    lineTo(w * 0.8f, h * 0.65f)
                    close()
                }
                drawPath(capePath, Color(0xFFDC2626))
            }

            // 2. Neck
            drawRect(
                color = skin,
                topLeft = Offset(w * 0.42f, h * 0.58f),
                size = Size(w * 0.16f, h * 0.12f)
            )

            // 3. Body / Torso / Uniform
            val bodyPath = Path().apply {
                moveTo(w * 0.22f, h * 0.72f)
                cubicTo(w * 0.3f, h * 0.65f, w * 0.7f, h * 0.65f, w * 0.78f, h * 0.72f)
                lineTo(w * 0.88f, h * 1.05f)
                lineTo(w * 0.12f, h * 1.05f)
                close()
            }
            drawPath(bodyPath, outfitMain)

            // Uniform Collar / Details
            val collarPath = Path().apply {
                moveTo(w * 0.38f, h * 0.68f)
                lineTo(w * 0.5f, h * 0.82f)
                lineTo(w * 0.62f, h * 0.68f)
                close()
            }
            drawPath(collarPath, outfitAccent)

            // School Tie or Emblem
            if (config.outfitIndex == 0) {
                val tiePath = Path().apply {
                    moveTo(w * 0.47f, h * 0.78f)
                    lineTo(w * 0.53f, h * 0.78f)
                    lineTo(w * 0.55f, h * 0.95f)
                    lineTo(w * 0.5f, h * 1.0f)
                    lineTo(w * 0.45f, h * 0.95f)
                    close()
                }
                drawPath(tiePath, Color(0xFFDC2626))
            }

            // 4. Head (Face Oval)
            drawOval(
                color = skin,
                topLeft = Offset(w * 0.26f, h * 0.22f),
                size = Size(w * 0.48f, h * 0.48f)
            )

            // Ears
            drawCircle(skin, radius = w * 0.055f, center = Offset(w * 0.25f, h * 0.46f))
            drawCircle(skin, radius = w * 0.055f, center = Offset(w * 0.75f, h * 0.46f))

            // 5. Hair
            drawHair(config.hairStyleIndex, hair, w, h)

            // 6. Eyes & Expression
            drawFaceExpression(config.eyeExpressionIndex, skin, w, h)

            // 7. Blush
            drawCircle(Color(0xFFFF8A80).copy(alpha = 0.45f), radius = w * 0.035f, center = Offset(w * 0.35f, h * 0.52f))
            drawCircle(Color(0xFFFF8A80).copy(alpha = 0.45f), radius = w * 0.035f, center = Offset(w * 0.65f, h * 0.52f))

            // 8. Accessories
            drawAccessory(config.accessoryIndex, w, h)
        }
    }
}

private fun DrawScope.drawHair(style: Int, hairColor: Color, w: Float, h: Float) {
    when (style) {
        0 -> { // Corto clásico
            val hairPath = Path().apply {
                moveTo(w * 0.24f, h * 0.34f)
                cubicTo(w * 0.24f, h * 0.12f, w * 0.76f, h * 0.12f, w * 0.76f, h * 0.34f)
                cubicTo(w * 0.7f, h * 0.22f, w * 0.5f, h * 0.2f, w * 0.38f, h * 0.26f)
                close()
            }
            drawPath(hairPath, hairColor)
        }
        1 -> { // Rizado con volumen
            drawCircle(hairColor, radius = w * 0.12f, center = Offset(w * 0.32f, h * 0.22f))
            drawCircle(hairColor, radius = w * 0.13f, center = Offset(w * 0.5f, h * 0.18f))
            drawCircle(hairColor, radius = w * 0.12f, center = Offset(w * 0.68f, h * 0.22f))
            drawCircle(hairColor, radius = w * 0.09f, center = Offset(w * 0.22f, h * 0.32f))
            drawCircle(hairColor, radius = w * 0.09f, center = Offset(w * 0.78f, h * 0.32f))
        }
        2 -> { // Largo
            val hairPath = Path().apply {
                moveTo(w * 0.22f, h * 0.7f)
                lineTo(w * 0.24f, h * 0.32f)
                cubicTo(w * 0.24f, h * 0.12f, w * 0.76f, h * 0.12f, w * 0.76f, h * 0.32f)
                lineTo(w * 0.78f, h * 0.7f)
                lineTo(w * 0.68f, h * 0.65f)
                cubicTo(w * 0.65f, h * 0.28f, w * 0.35f, h * 0.28f, w * 0.32f, h * 0.65f)
                close()
            }
            drawPath(hairPath, hairColor)
        }
        3 -> { // Coleta alta
            drawCircle(hairColor, radius = w * 0.11f, center = Offset(w * 0.72f, h * 0.16f))
            val topHair = Path().apply {
                moveTo(w * 0.24f, h * 0.34f)
                cubicTo(w * 0.24f, h * 0.14f, w * 0.76f, h * 0.14f, w * 0.76f, h * 0.34f)
                cubicTo(w * 0.6f, h * 0.22f, w * 0.4f, h * 0.22f, w * 0.24f, h * 0.34f)
                close()
            }
            drawPath(topHair, hairColor)
        }
        else -> { // Con Flequillo moderno
            val bangs = Path().apply {
                moveTo(w * 0.23f, h * 0.35f)
                cubicTo(w * 0.25f, h * 0.14f, w * 0.75f, h * 0.14f, w * 0.77f, h * 0.35f)
                lineTo(w * 0.65f, h * 0.32f)
                lineTo(w * 0.55f, h * 0.35f)
                lineTo(w * 0.45f, h * 0.31f)
                lineTo(w * 0.35f, h * 0.36f)
                close()
            }
            drawPath(bangs, hairColor)
        }
    }
}

private fun DrawScope.drawFaceExpression(expression: Int, skinColor: Color, w: Float, h: Float) {
    val eyeColor = Color(0xFF1E293B)
    val mouthColor = Color(0xFF991B1B)

    when (expression) {
        0 -> { // Alegre / Sonriente
            drawCircle(eyeColor, radius = w * 0.032f, center = Offset(w * 0.4f, h * 0.45f))
            drawCircle(Color.White, radius = w * 0.012f, center = Offset(w * 0.39f, h * 0.44f))

            drawCircle(eyeColor, radius = w * 0.032f, center = Offset(w * 0.6f, h * 0.45f))
            drawCircle(Color.White, radius = w * 0.012f, center = Offset(w * 0.59f, h * 0.44f))

            // Sonrisa
            val smilePath = Path().apply {
                moveTo(w * 0.42f, h * 0.56f)
                quadraticTo(w * 0.5f, h * 0.63f, w * 0.58f, h * 0.56f)
            }
            drawPath(smilePath, mouthColor, style = Stroke(width = w * 0.02f))
        }
        1 -> { // Concentrado / Guiño
            drawCircle(eyeColor, radius = w * 0.032f, center = Offset(w * 0.4f, h * 0.45f))
            // Guiño ojo derecho
            val wink = Path().apply {
                moveTo(w * 0.56f, h * 0.46f)
                quadraticTo(w * 0.6f, h * 0.42f, w * 0.64f, h * 0.46f)
            }
            drawPath(wink, eyeColor, style = Stroke(width = w * 0.025f))

            // Sonrisa ladeada
            val smilePath = Path().apply {
                moveTo(w * 0.45f, h * 0.57f)
                quadraticTo(w * 0.52f, h * 0.61f, w * 0.57f, h * 0.55f)
            }
            drawPath(smilePath, mouthColor, style = Stroke(width = w * 0.02f))
        }
        else -> { // Inteligente / Determinación
            drawCircle(eyeColor, radius = w * 0.032f, center = Offset(w * 0.4f, h * 0.45f))
            drawCircle(eyeColor, radius = w * 0.032f, center = Offset(w * 0.6f, h * 0.45f))

            // Cejas reflexivas
            drawLine(eyeColor, start = Offset(w * 0.36f, h * 0.39f), end = Offset(w * 0.44f, h * 0.38f), strokeWidth = w * 0.018f)
            drawLine(eyeColor, start = Offset(w * 0.56f, h * 0.38f), end = Offset(w * 0.64f, h * 0.39f), strokeWidth = w * 0.018f)

            // Sonrisa amplia
            val mouthPath = Path().apply {
                moveTo(w * 0.43f, h * 0.56f)
                quadraticTo(w * 0.5f, h * 0.64f, w * 0.57f, h * 0.56f)
                close()
            }
            drawPath(mouthPath, mouthColor, style = Fill)
            drawPath(mouthPath, eyeColor, style = Stroke(width = w * 0.015f))
        }
    }
}

private fun DrawScope.drawAccessory(acc: Int, w: Float, h: Float) {
    when (acc) {
        1 -> { // Gafas Intelectuales (Round smart glasses)
            val glassColor = Color(0xFF1E293B)
            drawCircle(glassColor, radius = w * 0.07f, center = Offset(w * 0.4f, h * 0.45f), style = Stroke(width = w * 0.02f))
            drawCircle(glassColor, radius = w * 0.07f, center = Offset(w * 0.6f, h * 0.45f), style = Stroke(width = w * 0.02f))
            drawLine(glassColor, start = Offset(w * 0.47f, h * 0.45f), end = Offset(w * 0.53f, h * 0.45f), strokeWidth = w * 0.02f)
        }
        2 -> { // Birrete o Gorra escolar
            val capPath = Path().apply {
                moveTo(w * 0.5f, h * 0.08f)
                lineTo(w * 0.78f, h * 0.19f)
                lineTo(w * 0.5f, h * 0.28f)
                lineTo(w * 0.22f, h * 0.19f)
                close()
            }
            drawPath(capPath, Color(0xFF1E3A8A))
            // Borla
            drawLine(Color(0xFFF59E0B), start = Offset(w * 0.5f, h * 0.18f), end = Offset(w * 0.72f, h * 0.28f), strokeWidth = w * 0.018f)
            drawCircle(Color(0xFFF59E0B), radius = w * 0.025f, center = Offset(w * 0.72f, h * 0.29f))
        }
        3 -> { // Auriculares Gamer / Estudio
            val phoneColor = Color(0xFF3B82F6)
            // Diadema
            drawArc(
                color = phoneColor,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(w * 0.22f, h * 0.16f),
                size = Size(w * 0.56f, h * 0.36f),
                style = Stroke(width = w * 0.035f)
            )
            // Almohadillas
            drawRoundRect(
                color = Color(0xFF1E293B),
                topLeft = Offset(w * 0.18f, h * 0.36f),
                size = Size(w * 0.08f, h * 0.16f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.03f, w * 0.03f)
            )
            drawRoundRect(
                color = Color(0xFF1E293B),
                topLeft = Offset(w * 0.74f, h * 0.36f),
                size = Size(w * 0.08f, h * 0.16f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.03f, w * 0.03f)
            )
        }
        else -> {
            // Sin accesorio
        }
    }
}
