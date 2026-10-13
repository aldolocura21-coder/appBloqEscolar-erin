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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvatarCreatorScreen(
    profile: KidProfile,
    onSaveAvatar: (AvatarConfig) -> Unit,
    onPurchaseCosmetic: (String, Int, AvatarConfig) -> Unit,
    onBackClick: () -> Unit
) {
    var avatarConfig by remember { mutableStateOf(profile.avatar) }
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Piel", "Cabello", "Ropa", "Accesorios", "Expresión")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Personalizar Avatar Escolar", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = GoldReward, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "${profile.coins}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SchoolBluePrimary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF8FAFC))
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Avatar Preview Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AvatarView(
                        config = avatarConfig,
                        size = 140.dp,
                        backgroundColor = Color(0xFFEFF6FF)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Vista Previa de ${profile.name}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tabs for customization
            PrimaryTabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = SchoolBluePrimary,
                modifier = Modifier.fillMaxWidth()
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                fontSize = 12.sp,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Tab Content
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                when (selectedTab) {
                    0 -> { // Piel
                        Column {
                            Text(text = "Tono de Piel", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF0F172A))
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                AvatarPalette.skinTones.forEachIndexed { index, color ->
                                    ColorSwatch(
                                        color = color,
                                        isSelected = avatarConfig.skinToneIndex == index,
                                        onClick = { avatarConfig = avatarConfig.copy(skinToneIndex = index) }
                                    )
                                }
                            }
                        }
                    }

                    1 -> { // Cabello (Estilo y Color)
                        Column {
                            Text(text = "Estilo de Cabello", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF0F172A))
                            Spacer(modifier = Modifier.height(10.dp))
                            val styles = listOf("Corto", "Rizado", "Largo", "Coleta", "Flequillo")
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                styles.forEachIndexed { index, name ->
                                    ItemChoiceChip(
                                        title = name,
                                        isSelected = avatarConfig.hairStyleIndex == index,
                                        modifier = Modifier.weight(1f),
                                        onClick = { avatarConfig = avatarConfig.copy(hairStyleIndex = index) }
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(18.dp))

                            Text(text = "Color de Cabello", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF0F172A))
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                AvatarPalette.hairColors.forEachIndexed { index, color ->
                                    ColorSwatch(
                                        color = color,
                                        isSelected = avatarConfig.hairColorIndex == index,
                                        onClick = { avatarConfig = avatarConfig.copy(hairColorIndex = index) }
                                    )
                                }
                            }
                        }
                    }

                    2 -> { // Ropa
                        Column {
                            Text(text = "Vestimenta Escolar y Especial", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF0F172A))
                            Spacer(modifier = Modifier.height(12.dp))
                            val outfits = listOf(
                                Triple(0, "Uniforme Escolar", 0),
                                Triple(1, "Sudadera Urbana", 0),
                                Triple(2, "Bata Científico", 25),
                                Triple(3, "Traje Galáctico", 50)
                            )

                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                outfits.forEach { (index, name, cost) ->
                                    val isUnlocked = cost == 0 || profile.unlockedCosmetics.contains("outfit_$index")
                                    val isSelected = avatarConfig.outfitIndex == index

                                    ShopItemCard(
                                        title = name,
                                        cost = cost,
                                        isUnlocked = isUnlocked,
                                        isSelected = isSelected,
                                        onSelect = { avatarConfig = avatarConfig.copy(outfitIndex = index) },
                                        onBuy = {
                                            onPurchaseCosmetic("outfit_$index", cost, avatarConfig.copy(outfitIndex = index))
                                            avatarConfig = avatarConfig.copy(outfitIndex = index)
                                        }
                                    )
                                }
                            }
                        }
                    }

                    3 -> { // Accesorios
                        Column {
                            Text(text = "Accesorios y Gorras", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF0F172A))
                            Spacer(modifier = Modifier.height(12.dp))
                            val accessories = listOf(
                                Triple(0, "Sin Accesorio", 0),
                                Triple(1, "Gafas Inteligentes", 0),
                                Triple(2, "Birrete de Graduado", 15),
                                Triple(3, "Auriculares Estudio", 30),
                                Triple(4, "Capa de Superhéroe", 45)
                            )

                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                accessories.forEach { (index, name, cost) ->
                                    val isUnlocked = cost == 0 || profile.unlockedCosmetics.contains("acc_$index")
                                    val isSelected = avatarConfig.accessoryIndex == index

                                    ShopItemCard(
                                        title = name,
                                        cost = cost,
                                        isUnlocked = isUnlocked,
                                        isSelected = isSelected,
                                        onSelect = { avatarConfig = avatarConfig.copy(accessoryIndex = index) },
                                        onBuy = {
                                            onPurchaseCosmetic("acc_$index", cost, avatarConfig.copy(accessoryIndex = index))
                                            avatarConfig = avatarConfig.copy(accessoryIndex = index)
                                        }
                                    )
                                }
                            }
                        }
                    }

                    else -> { // Expresión
                        Column {
                            Text(text = "Expresión Facial", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF0F172A))
                            Spacer(modifier = Modifier.height(12.dp))
                            val expressions = listOf("Alegre", "Guiño", "Super Pro")
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                expressions.forEachIndexed { index, name ->
                                    ItemChoiceChip(
                                        title = name,
                                        isSelected = avatarConfig.eyeExpressionIndex == index,
                                        modifier = Modifier.weight(1f),
                                        onClick = { avatarConfig = avatarConfig.copy(eyeExpressionIndex = index) }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Save Button
            Button(
                onClick = {
                    onSaveAvatar(avatarConfig)
                    onBackClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(54.dp)
                    .testTag("save_avatar_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Guardar y Equipar Avatar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun ColorSwatch(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(color)
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected) SchoolBluePrimary else Color(0xFFCBD5E1),
                shape = CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
        }
    }
}

@Composable
private fun ItemChoiceChip(
    title: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) SchoolBluePrimary else Color.White,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSelected) SchoolBluePrimary else Color(0xFFCBD5E1)
        ),
        modifier = modifier.height(44.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(4.dp)) {
            Text(
                text = title,
                color = if (isSelected) Color.White else Color(0xFF334155),
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ShopItemCard(
    title: String,
    cost: Int,
    isUnlocked: Boolean,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onBuy: () -> Unit
) {
    Surface(
        onClick = { if (isUnlocked) onSelect() else onBuy() },
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) SchoolBluePrimary.copy(alpha = 0.08f) else Color.White,
        border = androidx.compose.foundation.BorderStroke(
            1.5.dp,
            if (isSelected) SchoolBluePrimary else Color(0xFFE2E8F0)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isSelected) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = SchoolBluePrimary, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                }
                Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color(0xFF0F172A))
            }

            if (isUnlocked) {
                Text(
                    text = if (isSelected) "Equipado" else "Elegir",
                    color = if (isSelected) SchoolBluePrimary else Color(0xFF64748B),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = GoldReward, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "$cost ⭐", color = GoldReward, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }
    }
}
