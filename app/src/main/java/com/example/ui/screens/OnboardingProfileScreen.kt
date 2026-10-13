package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.model.AvatarConfig
import com.example.model.KidProfile
import com.example.ui.components.AvatarPalette
import com.example.ui.components.AvatarView
import com.example.ui.theme.GoldReward
import com.example.ui.theme.SchoolBlueDark
import com.example.ui.theme.SchoolBluePrimary
import com.example.ui.theme.SuccessGreen

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun OnboardingProfileScreen(
    initialProfile: KidProfile,
    onCompleteOnboarding: (KidProfile) -> Unit
) {
    var name by remember { mutableStateOf(initialProfile.name) }
    var age by remember { mutableIntStateOf(initialProfile.age) }
    var gradeLevel by remember { mutableStateOf(initialProfile.gradeLevel) }
    var avatarConfig by remember { mutableStateOf(initialProfile.avatar) }

    var selectedAvatarTab by remember { mutableIntStateOf(0) }
    val avatarTabs = listOf("Piel", "Cabello", "Uniforme", "Expresión")

    val gradeOptions = listOf(
        "Primaria - 1° Ciclo (1° a 3° Grado)",
        "Primaria - 2° Ciclo (4° a 7° Grado)",
        "Secundaria - Ciclo Básico (1° a 3° Año)",
        "Secundaria - Ciclo Orientado (4° a 6° Año)"
    )

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

            // Welcome Badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White.copy(alpha = 0.15f))
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = GoldReward,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Configuración Inicial Inteligente",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "¡Crea tu Perfil y Avatar!",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Text(
                text = "La IA adaptará las actividades y retos a tu edad y año escolar.",
                fontSize = 13.sp,
                color = Color(0xFFCBD5E1),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Live Avatar Display
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AvatarView(
                        config = avatarConfig,
                        size = 110.dp,
                        backgroundColor = Color(0xFF334155)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (name.isNotBlank()) name else "Estudiante",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 17.sp
                    )
                    Text(
                        text = "$age años • $gradeLevel",
                        fontSize = 12.sp,
                        color = GoldReward
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Student Information Form Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "1. Datos del Estudiante",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF0F172A)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nombre del alumno") },
                        placeholder = { Text("Ej: Alex") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = SchoolBluePrimary) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("onboarding_name_input")
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Edad: $age años",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color(0xFF334155)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Age Chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(6, 7, 8, 9, 10, 11, 12).forEach { a ->
                            Surface(
                                onClick = { age = a },
                                shape = RoundedCornerShape(10.dp),
                                color = if (age == a) SchoolBluePrimary else Color(0xFFF1F5F9),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(38.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "$a",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = if (age == a) Color.White else Color(0xFF334155)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(13, 14, 15, 16).forEach { a ->
                            Surface(
                                onClick = { age = a },
                                shape = RoundedCornerShape(10.dp),
                                color = if (age == a) SchoolBluePrimary else Color(0xFFF1F5F9),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(38.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "$a",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = if (age == a) Color.White else Color(0xFF334155)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "Nivel / Grado Educativo:",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color(0xFF334155)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        gradeOptions.forEach { option ->
                            val isSelected = gradeLevel == option
                            Surface(
                                onClick = { gradeLevel = option },
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) SchoolBluePrimary.copy(alpha = 0.1f) else Color(0xFFF8FAFC),
                                border = androidx.compose.foundation.BorderStroke(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) SchoolBluePrimary else Color(0xFFE2E8F0)
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.School,
                                        contentDescription = null,
                                        tint = if (isSelected) SchoolBluePrimary else Color.Gray,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = option,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) SchoolBluePrimary else Color(0xFF0F172A),
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Avatar Customization Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "2. Personaliza tu Avatar",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF0F172A)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    PrimaryTabRow(
                        selectedTabIndex = selectedAvatarTab,
                        containerColor = Color(0xFFF1F5F9),
                        contentColor = SchoolBluePrimary,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        avatarTabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedAvatarTab == index,
                                onClick = { selectedAvatarTab = index },
                                text = {
                                    Text(
                                        text = title,
                                        fontSize = 11.sp,
                                        fontWeight = if (selectedAvatarTab == index) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    when (selectedAvatarTab) {
                        0 -> { // Piel
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                AvatarPalette.skinTones.forEachIndexed { index, color ->
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .clip(CircleShape)
                                            .background(color)
                                            .border(
                                                width = if (avatarConfig.skinToneIndex == index) 3.dp else 1.dp,
                                                color = if (avatarConfig.skinToneIndex == index) SchoolBluePrimary else Color(0xFFCBD5E1),
                                                shape = CircleShape
                                            )
                                            .clickable { avatarConfig = avatarConfig.copy(skinToneIndex = index) },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (avatarConfig.skinToneIndex == index) {
                                            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                                        }
                                    }
                                }
                            }
                        }

                        1 -> { // Cabello
                            Column {
                                val styles = listOf("Corto", "Rizado", "Largo", "Coleta", "Flequillo")
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    styles.forEachIndexed { index, name ->
                                        Surface(
                                            onClick = { avatarConfig = avatarConfig.copy(hairStyleIndex = index) },
                                            shape = RoundedCornerShape(10.dp),
                                            color = if (avatarConfig.hairStyleIndex == index) SchoolBluePrimary else Color(0xFFF1F5F9),
                                            modifier = Modifier.weight(1f).height(38.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Text(
                                                    text = name,
                                                    fontSize = 10.sp,
                                                    fontWeight = if (avatarConfig.hairStyleIndex == index) FontWeight.Bold else FontWeight.Normal,
                                                    color = if (avatarConfig.hairStyleIndex == index) Color.White else Color(0xFF334155)
                                                )
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    AvatarPalette.hairColors.forEachIndexed { index, color ->
                                        Box(
                                            modifier = Modifier
                                                .size(38.dp)
                                                .clip(CircleShape)
                                                .background(color)
                                                .border(
                                                width = if (avatarConfig.hairColorIndex == index) 3.dp else 1.dp,
                                                color = if (avatarConfig.hairColorIndex == index) SchoolBluePrimary else Color(0xFFCBD5E1),
                                                shape = CircleShape
                                            )
                                                .clickable { avatarConfig = avatarConfig.copy(hairColorIndex = index) },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (avatarConfig.hairColorIndex == index) {
                                                Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        2 -> { // Uniforme
                            val outfits = listOf("Uniforme Escolar", "Sudadera Deportiva")
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                outfits.forEachIndexed { index, name ->
                                    Surface(
                                        onClick = { avatarConfig = avatarConfig.copy(outfitIndex = index) },
                                        shape = RoundedCornerShape(12.dp),
                                        color = if (avatarConfig.outfitIndex == index) SchoolBluePrimary else Color(0xFFF1F5F9),
                                        modifier = Modifier.weight(1f).height(44.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = name,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (avatarConfig.outfitIndex == index) Color.White else Color(0xFF334155)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        else -> { // Expresión
                            val expressions = listOf("Alegre", "Guiño", "Super Pro")
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                expressions.forEachIndexed { index, name ->
                                    Surface(
                                        onClick = { avatarConfig = avatarConfig.copy(eyeExpressionIndex = index) },
                                        shape = RoundedCornerShape(12.dp),
                                        color = if (avatarConfig.eyeExpressionIndex == index) SchoolBluePrimary else Color(0xFFF1F5F9),
                                        modifier = Modifier.weight(1f).height(44.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = name,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (avatarConfig.eyeExpressionIndex == index) Color.White else Color(0xFF334155)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Submit Button
            Button(
                onClick = {
                    val finalProfile = initialProfile.copy(
                        name = if (name.isNotBlank()) name.trim() else "Estudiante",
                        age = age,
                        gradeLevel = gradeLevel,
                        avatar = avatarConfig,
                        isOnboardingCompleted = true
                    )
                    onCompleteOnboarding(finalProfile)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .testTag("complete_onboarding_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
            ) {
                Icon(imageVector = Icons.Default.Stars, contentDescription = null, modifier = Modifier.size(22.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "¡Comenzar con Mi Avatar y Mis Retos! 🚀",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(36.dp))
        }
    }
}
