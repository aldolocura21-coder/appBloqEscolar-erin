package com.example.model

enum class Subject(val displayName: String, val iconName: String, val category: String = "General") {
    MATH("Matemática", "calculate", "Curricular"),
    LANGUAGE("Prácticas del Lenguaje", "menu_book", "Curricular"),
    SCIENCE("Ciencias Naturales y Biología", "biotech", "Curricular"),
    PHYSICS_CHEMISTRY("Física y Química", "science", "Curricular"),
    HISTORY("Ciencias Sociales y Geografía", "public", "Curricular"),
    ARGENTINE_HISTORY("Historia Argentina", "account_balance", "Curricular"),
    CITIZENSHIP("Construcción de Ciudadanía y Ética", "gavel", "Curricular"),
    ENGLISH("Idioma Extranjero (Inglés)", "language", "Idiomas"),
    INFORMATICS("Informática y Computación", "computer", "Tecnología"),
    ROBOTICS("Robótica y Programación", "smart_toy", "Tecnología"),
    ARTIFICIAL_INTELLIGENCE("Inteligencia Artificial", "psychology", "Tecnología"),
    PHYSICAL_EDUCATION("Educación Física y Deportes", "sports_soccer", "Curricular"),
    ART_MUSIC("Educación Artística y Música", "palette", "Curricular"),
    ENVIRONMENT("Ecología y Medio Ambiente", "eco", "Transversal"),
    FINANCIAL_EDUCATION("Educación Financiera", "savings", "Transversal"),
    LOGIC("Lógica y Acertijos", "extension", "Transversal"),
    FAMILY("En Familia con Adultos", "family_restroom", "Familiar"),
    AI_SPECIAL("Misión IA Gemini", "auto_awesome", "Especial")
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
