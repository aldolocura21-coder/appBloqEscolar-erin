package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.ParentSettings
import com.example.model.SchoolSchedule
import com.example.ui.theme.LockRed
import com.example.ui.theme.SchoolBlueDark
import com.example.ui.theme.SchoolBluePrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun ParentalControlDialog(
    parentSettings: ParentSettings,
    studentGradeLevel: String = "Primaria Superior (4°-6°)",
    onUpdateGradeLevel: (String) -> Unit = {},
    isAuthenticated: Boolean,
    pinError: String?,
    onVerifyPin: (String) -> Boolean,
    onDirectUnlock: (Int) -> Unit,
    onSaveSettings: (ParentSettings) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var enteredPin by remember { mutableStateOf("") }
    var authenticatedState by remember { mutableStateOf(isAuthenticated) }

    // Settings local state
    var selectedGrade by remember { mutableStateOf(studentGradeLevel) }
    var scheduleEnabled by remember { mutableStateOf(parentSettings.schoolSchedule.enabled) }
    var startHour by remember { mutableIntStateOf(parentSettings.schoolSchedule.startHour) }
    var endHour by remember { mutableIntStateOf(parentSettings.schoolSchedule.endHour) }
    var minutesPerChallenge by remember { mutableIntStateOf(parentSettings.minutesPerChallenge) }
    var strictMode by remember { mutableStateOf(parentSettings.strictMode) }
    var familyActivityEveryOtherDay by remember { mutableStateOf(parentSettings.familyActivityEveryOtherDay) }
    var newPin by remember { mutableStateOf(parentSettings.pin) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(SchoolBluePrimary.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = SchoolBluePrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Control Parental",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0xFF0F172A)
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (!authenticatedState) {
                    // PIN Screen
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Introduce el PIN de Padres",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )
                        Text(
                            text = "(Por defecto: 1234)",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        OutlinedTextField(
                            value = enteredPin,
                            onValueChange = { if (it.length <= 6) enteredPin = it },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            label = { Text("PIN de seguridad") },
                            isError = pinError != null,
                            supportingText = {
                                if (pinError != null) Text(pinError, color = LockRed)
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("parent_pin_input")
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                if (onVerifyPin(enteredPin)) {
                                    authenticatedState = true
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("parent_pin_confirm_button"),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SchoolBluePrimary)
                        ) {
                            Icon(imageVector = Icons.Default.LockOpen, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Entrar a Ajustes", fontWeight = FontWeight.Bold)
                        }
                    }
                } else {
                    // Authenticated Parental Settings
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Quick Unlock Button
                        Card(
                            colors = CardDefaults.cardColors(containerColor = SuccessGreen.copy(alpha = 0.1f)),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Desbloqueo Inmediato",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = SuccessGreen
                                    )
                                    Text(
                                        text = "Otorga 45 min sin hacer reto educativo",
                                        fontSize = 12.sp,
                                        color = Color(0xFF475569)
                                    )
                                }
                                Button(
                                    onClick = {
                                        onDirectUnlock(45)
                                        onDismiss()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("+45 min", fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        // Schedule Toggle
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = "Bloqueo Escolar Automático", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(text = "De Lunes a Viernes (8:00 - 14:30)", fontSize = 12.sp, color = Color.Gray)
                            }
                            Switch(
                                checked = scheduleEnabled,
                                onCheckedChange = { scheduleEnabled = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = SchoolBluePrimary)
                            )
                        }

                        // Minutes per challenge
                        Column {
                            Text(text = "Tiempo ganado por reto educativo", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                listOf(10, 15, 20, 30).forEach { mins ->
                                    val isSelected = minutesPerChallenge == mins
                                    Button(
                                        onClick = { minutesPerChallenge = mins },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (isSelected) SchoolBluePrimary else Color(0xFFE2E8F0),
                                            contentColor = if (isSelected) Color.White else Color(0xFF334155)
                                        ),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Text("$mins m", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    }
                                }
                            }
                        }

                        // Grado Escolar del Estudiante
                        Column {
                            Text(text = "Nivel Escolar del Estudiante (Retos IA)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(text = "La IA adapta la dificultad de los retos a este nivel", fontSize = 12.sp, color = Color.Gray)
                            Spacer(modifier = Modifier.height(8.dp))
                            val gradesList = listOf(
                                "Primaria - 1° Ciclo (1° a 3° Grado)",
                                "Primaria - 2° Ciclo (4° a 7° Grado)",
                                "Secundaria - Ciclo Básico (1° a 3° Año)",
                                "Secundaria - Ciclo Orientado (4° a 6° Año)"
                            )
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                gradesList.forEach { gr ->
                                    val isSel = selectedGrade == gr
                                    Surface(
                                        onClick = {
                                            selectedGrade = gr
                                            onUpdateGradeLevel(gr)
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (isSel) SchoolBluePrimary.copy(alpha = 0.12f) else Color(0xFFF8FAFC),
                                        border = androidx.compose.foundation.BorderStroke(
                                            width = if (isSel) 2.dp else 1.dp,
                                            color = if (isSel) SchoolBluePrimary else Color(0xFFE2E8F0)
                                        ),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(10.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            if (isSel) {
                                                Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = SchoolBluePrimary, modifier = Modifier.size(18.dp))
                                                Spacer(modifier = Modifier.width(8.dp))
                                            }
                                            Text(
                                                text = gr,
                                                fontSize = 13.sp,
                                                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                                color = if (isSel) SchoolBluePrimary else Color(0xFF0F172A)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Strict mode
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "Modo Estricto", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(text = "Bloquea desinstalación y acceso a ajustes", fontSize = 12.sp, color = Color.Gray)
                            }
                            Switch(
                                checked = strictMode,
                                onCheckedChange = { strictMode = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = SchoolBluePrimary)
                            )
                        }

                        // Actividad con adultos día por medio
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = "Actividad con Adultos Día por Medio", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(text = "Retos de interacción y diálogo familiar en días alternos", fontSize = 12.sp, color = Color.Gray)
                            }
                            Switch(
                                checked = familyActivityEveryOtherDay,
                                onCheckedChange = { familyActivityEveryOtherDay = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = SchoolBluePrimary)
                            )
                        }

                        // Info de seguridad y apps recientes ("3 palitos")
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFF1F5F9)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Security,
                                        contentDescription = null,
                                        tint = SchoolBluePrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Protección Antisalida ('3 Palitos'):",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF0F172A)
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "El servicio de accesibilidad cierra automáticamente el menú de aplicaciones recientes ('|||') cuando el teléfono está bloqueado para que el alumno no pueda ingresar a otras apps.",
                                    fontSize = 11.sp,
                                    color = Color(0xFF334155),
                                    lineHeight = 15.sp
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Button(
                                    onClick = {
                                        try {
                                            val intent = android.content.Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
                                                addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                                            }
                                            context.startActivity(intent)
                                        } catch (_: Exception) {}
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = SchoolBluePrimary),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.fillMaxWidth().height(42.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Security, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Activar Servicio Antisalida en Ajustes", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        // Change PIN
                        OutlinedTextField(
                            value = newPin,
                            onValueChange = { if (it.length <= 6) newPin = it },
                            label = { Text("PIN de Padres") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Save button
                        Button(
                            onClick = {
                                val updated = parentSettings.copy(
                                    pin = if (newPin.isNotBlank()) newPin else "1234",
                                    schoolSchedule = parentSettings.schoolSchedule.copy(enabled = scheduleEnabled),
                                    minutesPerChallenge = minutesPerChallenge,
                                    strictMode = strictMode,
                                    familyActivityEveryOtherDay = familyActivityEveryOtherDay
                                )
                                onSaveSettings(updated)
                                onDismiss()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .testTag("save_parental_settings_button"),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SchoolBluePrimary)
                        ) {
                            Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Guardar Configuración", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedButton(
                            onClick = { com.example.util.DeviceAppsManager.openHomeSettings(context) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Settings, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Cambiar App de Inicio en Ajustes", fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }
}
