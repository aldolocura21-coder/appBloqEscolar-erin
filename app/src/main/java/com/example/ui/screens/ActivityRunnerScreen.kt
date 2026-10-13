package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Subject
import com.example.ui.ActivityState
import com.example.ui.theme.GoldReward
import com.example.ui.theme.LockRed
import com.example.ui.theme.SchoolBlueDark
import com.example.ui.theme.SchoolBluePrimary
import com.example.ui.theme.SuccessGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityRunnerScreen(
    activityState: ActivityState,
    onSelectOption: (Int) -> Unit,
    onCheckAnswer: () -> Unit,
    onNextQuestion: () -> Unit,
    onBackClick: () -> Unit,
    onFinishAndUnlock: () -> Unit,
    onExitToBackground: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = activityState.selectedSubject.displayName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF8FAFC))
        ) {
            when {
                activityState.isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = SchoolBluePrimary,
                            strokeWidth = 4.dp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Cargando preguntas escolares...",
                            color = Color(0xFF64748B),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                activityState.isFinished -> {
                    FinishedChallengeView(
                        result = activityState.result,
                        onContinue = onFinishAndUnlock,
                        onExitToBackground = onExitToBackground
                    )
                }

                activityState.questions.isNotEmpty() -> {
                    val currentQuestion = activityState.questions[activityState.currentIndex]
                    val total = activityState.questions.size
                    val progress = (activityState.currentIndex + 1).toFloat() / total.toFloat()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Progress bar & Counter
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Pregunta ${activityState.currentIndex + 1} de $total",
                                fontWeight = FontWeight.Bold,
                                color = SchoolBlueDark,
                                fontSize = 14.sp
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = GoldReward,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "+${currentQuestion.xpReward} XP",
                                    fontWeight = FontWeight.Bold,
                                    color = GoldReward,
                                    fontSize = 13.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = SchoolBluePrimary,
                            trackColor = Color(0xFFE2E8F0)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Question Card
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("question_card"),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text(
                                    text = currentQuestion.question,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A),
                                    lineHeight = 26.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Options
                        currentQuestion.options.forEachIndexed { index, optionText ->
                            val isSelected = activityState.selectedOptionIndex == index
                            val isChecked = activityState.isAnswerChecked
                            val isCorrectOption = (index == currentQuestion.correctIndex)

                            OptionCard(
                                indexLetter = ('A' + index).toString(),
                                text = optionText,
                                isSelected = isSelected,
                                isChecked = isChecked,
                                isCorrectOption = isCorrectOption,
                                onClick = {
                                    if (!isChecked) onSelectOption(index)
                                }
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Feedback Banner
                        if (activityState.isAnswerChecked) {
                            FeedbackBanner(
                                isCorrect = activityState.isCorrect,
                                explanation = currentQuestion.explanation
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        // Bottom Action Button
                        if (!activityState.isAnswerChecked) {
                            Button(
                                onClick = onCheckAnswer,
                                enabled = activityState.selectedOptionIndex != null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(54.dp)
                                    .testTag("check_answer_button"),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = SchoolBluePrimary)
                            ) {
                                Text(
                                    text = "Comprobar Respuesta",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        } else {
                            Button(
                                onClick = onNextQuestion,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(54.dp)
                                    .testTag("next_question_button"),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
                            ) {
                                Text(
                                    text = if (activityState.currentIndex + 1 < total) "Siguiente Pregunta ➡️" else "Ver Recompensas 🏆",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun OptionCard(
    indexLetter: String,
    text: String,
    isSelected: Boolean,
    isChecked: Boolean,
    isCorrectOption: Boolean,
    onClick: () -> Unit
) {
    val borderColor = when {
        isChecked && isCorrectOption -> SuccessGreen
        isChecked && isSelected && !isCorrectOption -> LockRed
        isSelected -> SchoolBluePrimary
        else -> Color(0xFFE2E8F0)
    }

    val backgroundColor = when {
        isChecked && isCorrectOption -> SuccessGreen.copy(alpha = 0.1f)
        isChecked && isSelected && !isCorrectOption -> LockRed.copy(alpha = 0.1f)
        isSelected -> SchoolBluePrimary.copy(alpha = 0.08f)
        else -> Color.White
    }

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        border = androidx.compose.foundation.BorderStroke(2.dp, borderColor),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("option_$indexLetter")
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) SchoolBluePrimary else Color(0xFFE2E8F0)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = indexLetter,
                    color = if (isSelected) Color.White else Color(0xFF334155),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Text(
                text = text,
                fontSize = 15.sp,
                color = Color(0xFF0F172A),
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                modifier = Modifier.weight(1f)
            )

            if (isChecked && isCorrectOption) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Correcto",
                    tint = SuccessGreen
                )
            } else if (isChecked && isSelected && !isCorrectOption) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Incorrecto",
                    tint = LockRed
                )
            }
        }
    }
}

@Composable
private fun FeedbackBanner(
    isCorrect: Boolean,
    explanation: String
) {
    val bgColor = if (isCorrect) SuccessGreen.copy(alpha = 0.12f) else Color(0xFFFEF2F2)
    val textColor = if (isCorrect) SuccessGreen else LockRed

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (isCorrect) Icons.Default.Check else Icons.Default.Close,
                    contentDescription = null,
                    tint = textColor
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isCorrect) "¡Excelente respuesta!" else "¡Sigue aprendiendo!",
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = explanation,
                fontSize = 13.sp,
                color = Color(0xFF334155),
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun FinishedChallengeView(
    result: com.example.model.ActivitySessionResult?,
    onContinue: () -> Unit,
    onExitToBackground: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .background(GoldReward.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = null,
                tint = GoldReward,
                modifier = Modifier.size(54.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "¡Reto Completado!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A)
        )

        Text(
            text = "Demostraste tu dedicación y esfuerzo escolar",
            fontSize = 14.sp,
            color = Color(0xFF64748B),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Rewards Summary Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                RewardRow(
                    label = "Tiempo Desbloqueado",
                    value = "+${result?.minutesUnlocked ?: 15} minutos",
                    color = SuccessGreen
                )
                RewardRow(
                    label = "Aciertos Obtenidos",
                    value = "${result?.correctCount ?: 3} de ${result?.totalAnswered ?: 3}",
                    color = SchoolBluePrimary
                )
                RewardRow(
                    label = "Experiencia Ganada",
                    value = "+${result?.xpEarned ?: 75} XP",
                    color = GoldReward
                )
                RewardRow(
                    label = "Monedas de Avatar",
                    value = "+${result?.coinsEarned ?: 25} ⭐",
                    color = Color(0xFFF97316)
                )
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        // PRIMARY BUTTON: SALIR AL TELÉFONO / EN SEGUNDO PLANO
        Button(
            onClick = onExitToBackground,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .testTag("exit_to_phone_button"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
        ) {
            Icon(imageVector = Icons.Default.PhoneAndroid, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "🚀 ¡Usar Teléfono Ahora! (Minimizar)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("claim_unlocked_time_button"),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                text = "📱 Ver Panel y Apps Permitidas",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun RewardRow(label: String, value: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = Color(0xFF475569), fontSize = 14.sp)
        Text(text = value, color = color, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}
