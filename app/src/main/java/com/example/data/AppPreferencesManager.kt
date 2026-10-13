package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.AvatarConfig
import com.example.model.EmergencyContact
import com.example.model.KidProfile
import com.example.model.ParentSettings
import com.example.model.SchoolSchedule

class AppPreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("bloq_escolar_prefs", Context.MODE_PRIVATE)

    fun loadProfile(): KidProfile {
        val name = prefs.getString("kid_name", "Alex") ?: "Alex"
        val age = prefs.getInt("kid_age", 10)
        val grade = prefs.getString("kid_grade", "Primaria Superior (4°-6°)") ?: "Primaria Superior (4°-6°)"
        val xp = prefs.getInt("kid_xp", 180)
        val coins = prefs.getInt("kid_coins", 65)
        val streak = prefs.getInt("kid_streak", 5)
        val totalSolved = prefs.getInt("kid_total_solved", 14)
        val weekendBank = prefs.getInt("kid_weekend_bank", 75)
        val isOnboarding = prefs.getBoolean("is_onboarding_completed", false)

        val avatar = AvatarConfig(
            skinToneIndex = prefs.getInt("av_skin", 1),
            hairStyleIndex = prefs.getInt("av_hair_style", 0),
            hairColorIndex = prefs.getInt("av_hair_color", 1),
            eyeExpressionIndex = prefs.getInt("av_eye", 0),
            outfitIndex = prefs.getInt("av_outfit", 0),
            accessoryIndex = prefs.getInt("av_acc", 1)
        )

        val cosmetics = prefs.getStringSet("unlocked_cosmetics", setOf("outfit_0", "outfit_1", "acc_0", "acc_1")) ?: emptySet()

        return KidProfile(
            name = name,
            age = age,
            gradeLevel = grade,
            avatar = avatar,
            xp = xp,
            coins = coins,
            streakDays = streak,
            totalActivitiesSolved = totalSolved,
            weekendBankMinutes = weekendBank,
            unlockedCosmetics = cosmetics,
            isOnboardingCompleted = isOnboarding
        )
    }

    fun saveProfile(profile: KidProfile) {
        prefs.edit().apply {
            putString("kid_name", profile.name)
            putInt("kid_age", profile.age)
            putString("kid_grade", profile.gradeLevel)
            putInt("kid_xp", profile.xp)
            putInt("kid_coins", profile.coins)
            putInt("kid_streak", profile.streakDays)
            putInt("kid_total_solved", profile.totalActivitiesSolved)
            putInt("kid_weekend_bank", profile.weekendBankMinutes)
            putBoolean("is_onboarding_completed", profile.isOnboardingCompleted)
            putInt("av_skin", profile.avatar.skinToneIndex)
            putInt("av_hair_style", profile.avatar.hairStyleIndex)
            putInt("av_hair_color", profile.avatar.hairColorIndex)
            putInt("av_eye", profile.avatar.eyeExpressionIndex)
            putInt("av_outfit", profile.avatar.outfitIndex)
            putInt("av_acc", profile.avatar.accessoryIndex)
            putStringSet("unlocked_cosmetics", profile.unlockedCosmetics)
            apply()
        }
    }

    fun loadParentSettings(): ParentSettings {
        val pin = prefs.getString("parent_pin", "1234") ?: "1234"
        val scheduleEnabled = prefs.getBoolean("sched_enabled", true)
        val startH = prefs.getInt("sched_start_h", 8)
        val startM = prefs.getInt("sched_start_m", 0)
        val endH = prefs.getInt("sched_end_h", 14)
        val endM = prefs.getInt("sched_end_m", 30)
        val minPerChallenge = prefs.getInt("min_per_challenge", 15)
        val maxDaily = prefs.getInt("max_daily_min", 90)
        val strictMode = prefs.getBoolean("strict_mode", false)

        return ParentSettings(
            pin = pin,
            schoolSchedule = SchoolSchedule(
                enabled = scheduleEnabled,
                startHour = startH,
                startMinute = startM,
                endHour = endH,
                endMinute = endM
            ),
            minutesPerChallenge = minPerChallenge,
            maxDailyMinutes = maxDaily,
            strictMode = strictMode
        )
    }

    fun saveParentSettings(settings: ParentSettings) {
        prefs.edit().apply {
            putString("parent_pin", settings.pin)
            putBoolean("sched_enabled", settings.schoolSchedule.enabled)
            putInt("sched_start_h", settings.schoolSchedule.startHour)
            putInt("sched_start_m", settings.schoolSchedule.startMinute)
            putInt("sched_end_h", settings.schoolSchedule.endHour)
            putInt("sched_end_m", settings.schoolSchedule.endMinute)
            putInt("min_per_challenge", settings.minutesPerChallenge)
            putInt("max_daily_min", settings.maxDailyMinutes)
            putBoolean("strict_mode", settings.strictMode)
            apply()
        }
    }

    fun getUnlockedUntilTimestamp(): Long {
        return prefs.getLong("unlocked_until_ts", 0L)
    }

    fun setUnlockedUntilTimestamp(timestamp: Long) {
        prefs.edit().putLong("unlocked_until_ts", timestamp).apply()
    }
}
