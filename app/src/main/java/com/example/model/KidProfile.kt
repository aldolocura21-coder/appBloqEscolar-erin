package com.example.model

data class AvatarConfig(
    val skinToneIndex: Int = 1, // 0..4
    val hairStyleIndex: Int = 0, // 0: Corto, 1: Rizado, 2: Largo, 3: Coleta, 4: Con Flequillo
    val hairColorIndex: Int = 1, // 0: Negro, 1: Castaño, 2: Rubio, 3: Rojo, 4: Fantasía Azul
    val eyeExpressionIndex: Int = 0, // 0: Alegre, 1: Concentrado, 2: Guiño, 3: Estrella
    val outfitIndex: Int = 0, // 0: Uniforme Escolar, 1: Sudadera Deportiva, 2: Bata Científico, 3: Astronauta
    val accessoryIndex: Int = 0 // 0: Ninguno, 1: Gafas Intelectuales, 2: Gorra Escolar, 3: Auriculares, 4: Capa de Héroe
)

data class KidProfile(
    val name: String = "Estudiante",
    val age: Int = 10,
    val gradeLevel: String = "Primaria - 2° Ciclo (4° a 7° Grado)",
    val avatar: AvatarConfig = AvatarConfig(),
    val xp: Int = 120,
    val coins: Int = 45,
    val streakDays: Int = 4,
    val totalActivitiesSolved: Int = 12,
    val weekendBankMinutes: Int = 60,
    val unlockedCosmetics: Set<String> = setOf("outfit_0", "outfit_1", "acc_0", "acc_1"),
    val isOnboardingCompleted: Boolean = false
) {
    val level: Int get() = (xp / 100) + 1
    val xpInCurrentLevel: Int get() = xp % 100
}
