package com.example.model

enum class Subject(val displayName: String, val iconName: String) {
    MATH("Matemáticas", "calculate"),
    LANGUAGE("Lengua y Lectura", "menu_book"),
    SCIENCE("Ciencias Naturales", "biotech"),
    HISTORY("Historia y Geo", "public"),
    LOGIC("Lógica y Acertijos", "psychology"),
    FAMILY("En Familia con Adultos", "family_restroom"),
    AI_SPECIAL("Misión IA Gemini", "auto_awesome")
}

data class ActivityQuestion(
    val id: String,
    val subject: Subject,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val xpReward: Int = 25,
    val coinsReward: Int = 10
)

data class ActivitySessionResult(
    val totalAnswered: Int,
    val correctCount: Int,
    val xpEarned: Int,
    val coinsEarned: Int,
    val minutesUnlocked: Int
)
