package com.example.model

data class SchoolSchedule(
    val enabled: Boolean = true,
    val startHour: Int = 8,
    val startMinute: Int = 0,
    val endHour: Int = 14,
    val endMinute: Int = 30,
    val activeDays: Set<Int> = setOf(1, 2, 3, 4, 5) // 1 = Monday ... 7 = Sunday
)

data class EmergencyContact(
    val id: String,
    val name: String,
    val relationship: String,
    val phoneNumber: String
)

data class AllowedApp(
    val id: String,
    val name: String,
    val packageName: String,
    val iconTag: String,
    val category: String,
    val isEnabled: Boolean = true
)

data class ParentSettings(
    val pin: String = "1234",
    val schoolSchedule: SchoolSchedule = SchoolSchedule(),
    val minutesPerChallenge: Int = 15,
    val maxDailyMinutes: Int = 90,
    val strictMode: Boolean = false,
    val emergencyContacts: List<EmergencyContact> = listOf(
        EmergencyContact("1", "Mamá", "Madre", "+34600111222"),
        EmergencyContact("2", "Papá", "Padre", "+34600333444"),
        EmergencyContact("3", "Emergencias", "Servicio Nacional", "112")
    ),
    val allowedApps: List<AllowedApp> = listOf(
        AllowedApp("calc", "Calculadora", "com.android.calculator2", "calculate", "Utilidad"),
        AllowedApp("classroom", "Google Classroom", "com.google.android.apps.classroom", "school", "Educación"),
        AllowedApp("duolingo", "Duolingo Idiomas", "com.duolingo", "translate", "Educación"),
        AllowedApp("books", "Biblioteca Escolar", "com.google.android.apps.books", "menu_book", "Lectura"),
        AllowedApp("clock", "Reloj y Alarmas", "com.google.android.deskclock", "alarm", "Utilidad")
    )
)
