package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Subject
import com.example.ui.BloqUiState
import com.example.ui.components.AvatarView
import com.example.ui.theme.GoldReward
import com.example.ui.theme.LockRed
import com.example.ui.theme.SchoolBlueDark
import com.example.ui.theme.SchoolBluePrimary
import com.example.ui.theme.SuccessGreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PhoneLockScreen(
    state: BloqUiState,
    onStartChallenge: (Subject?) -> Unit,
    onOpenEmergency: () -> Unit,
    onOpenParentControl: () -> Unit,
    onOpenAvatarCreator: () -> Unit,
    onOpenWeekendHub: () -> Unit,
    onOpenLauncherHelp: () -> Unit,
    isPinned: Boolean = false,
    onTogglePinScreen: () -> Unit = {}
) {
    val timeFormat = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }
    val currentTime = remember { timeFormat.format(Date()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        SchoolBlueDark,
                        Color(0xFF0F172A)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(36.dp))

            // 1. Clock and Lock Indicator
            Text(
                text = currentTime,
                fontSize = 52.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 2.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(LockRed.copy(alpha = 0.2f))
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Bloqueado",
                    tint = LockRed,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (state.isSchoolHoursActive) "Horario Escolar Protegido" else "Bloqueo Educativo Activo",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Banner Especial Día por Medio: Reto Familiar con Adultos
            if (state.isFamilyDay && state.parentSettings.familyActivityEveryOtherDay) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onStartChallenge(Subject.FAMILY)
                        }
                        .testTag("family_day_banner"),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF78350F).copy(alpha = 0.9f)),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, GoldReward)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(GoldReward.copy(alpha = 0.25f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.People,
                                contentDescription = null,
                                tint = GoldReward,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "👨‍👩‍👧‍👦 ¡Hoy Toca Reto Familiar!",
                                    color = GoldReward,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "DÍA POR MEDIO",
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color(0xFFB45309))
                                        .padding(horizontal = 5.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Actividad con adultos para dialogar en familia (+15 monedas ⭐).",
                                color = Color(0xFFFDE68A),
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Button(
                            onClick = {
                                onStartChallenge(Subject.FAMILY)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GoldReward,
                                contentColor = Color(0xFF451A03)
                            ),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(text = "Hacer", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
            }

            // 2. Child Avatar Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("avatar_summary_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B).copy(alpha = 0.9f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(contentAlignment = Alignment.BottomEnd) {
                        AvatarView(
                            config = state.profile.avatar,
                            size = 110.dp,
                            backgroundColor = Color(0xFF334155)
                        )
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(SchoolBluePrimary)
                                .clickable { onOpenAvatarCreator() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Palette,
                                contentDescription = "Editar Avatar",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "¡Hola, ${state.profile.name}!",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Nivel ${state.profile.level} • ${state.profile.gradeLevel}",
                        color = Color(0xFF94A3B8),
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Stats Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatItem(
                            icon = Icons.Default.Whatshot,
                            iconColor = Color(0xFFF97316),
                            label = "Racha",
                            value = "${state.profile.streakDays} días"
                        )
                        StatItem(
                            icon = Icons.Default.Star,
                            iconColor = GoldReward,
                            label = "Monedas",
                            value = "${state.profile.coins}"
                        )
                        StatItem(
                            icon = Icons.Default.Stars,
                            iconColor = SuccessGreen,
                            label = "Retos",
                            value = "${state.profile.totalActivitiesSolved}"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 3. Challenge Selection Card - Reto Aleatorio sin elección de asignatura
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("random_challenge_card"),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B).copy(alpha = 0.95f)),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF38BDF8).copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF0284C7).copy(alpha = 0.25f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🎲", fontSize = 22.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Reto Escolar Aleatorio",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Asignaturas al azar de Argentina",
                                color = Color(0xFF38BDF8),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "El reto selecciona 3 preguntas sorpresa entre todas las asignaturas escolares: Robótica, Informática, Inteligencia Artificial, Idioma Inglés, Matemática, Ed. Física, Lengua y Ciencias.",
                        color = Color(0xFFCBD5E1),
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Chips informativos de materias
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("🤖 Robótica", "💻 Informática", "🧠 IA", "🇬🇧 Inglés").forEach { tag ->
                            Text(
                                text = tag,
                                color = Color(0xFFE2E8F0),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFF334155))
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("📐 Matemática", "⚽ Ed. Física", "📚 Lengua", "🇦🇷 Historia").forEach { tag ->
                            Text(
                                text = tag,
                                color = Color(0xFFE2E8F0),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFF334155))
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Big Action Button: Resolver Reto Aleatorio
                    Button(
                        onClick = { onStartChallenge(null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("start_challenge_button"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SchoolBluePrimary)
                    ) {
                        Text(text = "🎲", fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Resolver Reto Aleatorio (+15 min)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Tarjeta de Seguridad Botón Recientes (3 Palitos)
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A).copy(alpha = 0.85f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF334155))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(LockRed.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = Color(0xFFF87171),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Bloqueo '3 Palitos' (Recientes)",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isPinned) "Pantalla fijada activada. Botón de recientes deshabilitado." else "Fija la pantalla para inhabilitar el botón de recientes.",
                            color = Color(0xFF94A3B8),
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onTogglePinScreen,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isPinned) SuccessGreen else Color(0xFF334155),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = if (isPinned) "Fijado ✓" else "Fijar",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Secondary Quick Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = { onOpenWeekendHub() },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("weekend_hub_button"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldReward)
                ) {
                    Icon(imageVector = Icons.Default.Stars, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Premios", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }

                OutlinedButton(
                    onClick = { onOpenEmergency() },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("emergency_button"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = LockRed)
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Emergencias", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Parent PIN Access & Help
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Acceso para Padres (PIN)",
                    color = Color(0xFF60A5FA),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onOpenParentControl() }
                        .padding(8.dp)
                        .testTag("parent_pin_access_link")
                )
                Text(text = " • ", color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
                Text(
                    text = "Info Bloqueo",
                    color = Color(0xFF94A3B8),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onOpenLauncherHelp() }
                        .padding(8.dp)
                        .testTag("launcher_help_link")
                )
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
private fun StatItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    label: String,
    value: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = value, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
        Text(text = label, color = Color(0xFF94A3B8), fontSize = 11.sp)
    }
}
