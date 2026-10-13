package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppPreferencesManager
import com.example.data.EducationalQuestionsRepository
import com.example.model.ActivityQuestion
import com.example.model.ActivitySessionResult
import com.example.model.AvatarConfig
import com.example.model.EmergencyContact
import com.example.model.KidProfile
import com.example.model.ParentSettings
import com.example.model.Subject
import com.example.service.GeminiAiService
import com.example.util.DeviceAppsManager
import com.example.util.InstalledAppItem
import com.example.util.UnlockedNotificationHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

enum class AppScreen {
    ONBOARDING,
    LOCK,
    UNLOCKED,
    ACTIVITY_RUNNER,
    AVATAR_CREATOR,
    WEEKEND_HUB,
    PARENTAL_CONTROLS,
    LAUNCHER_HELP
}

data class ActivityState(
    val isLoading: Boolean = false,
    val selectedSubject: Subject = Subject.MATH,
    val questions: List<ActivityQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedOptionIndex: Int? = null,
    val isAnswerChecked: Boolean = false,
    val isCorrect: Boolean = false,
    val correctAnswersCount: Int = 0,
    val isFinished: Boolean = false,
    val result: ActivitySessionResult? = null
)

data class BloqUiState(
    val profile: KidProfile = KidProfile(),
    val parentSettings: ParentSettings = ParentSettings(),
    val currentScreen: AppScreen = AppScreen.LOCK,
    val previousScreen: AppScreen = AppScreen.LOCK,
    val isLocked: Boolean = true,
    val remainingUnlockedSeconds: Long = 0L,
    val isSchoolHoursActive: Boolean = true,
    val activityState: ActivityState = ActivityState(),
    val isParentAuthenticated: Boolean = false,
    val parentPinError: String? = null,
    val emergencyContactToDial: EmergencyContact? = null,
    val statusMessage: String? = null,
    val installedApps: List<InstalledAppItem> = emptyList(),
    val shouldExitToBackground: Boolean = false
)

class BloqEscolarViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = AppPreferencesManager(application)

    private val _uiState = MutableStateFlow(BloqUiState())
    val uiState: StateFlow<BloqUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null
    private var lastNotifUpdateSecs: Long = 0L

    init {
        loadData()
        loadInstalledApps()
        startTimerLoop()
    }

    fun loadInstalledApps() {
        viewModelScope.launch {
            val apps = DeviceAppsManager.getInstalledApps(getApplication())
            _uiState.update { it.copy(installedApps = apps) }
        }
    }

    private fun loadData() {
        val loadedProfile = prefs.loadProfile()
        val loadedSettings = prefs.loadParentSettings()
        val unlockedUntil = prefs.getUnlockedUntilTimestamp()
        val now = System.currentTimeMillis()

        val remainingSecs = if (unlockedUntil > now) (unlockedUntil - now) / 1000L else 0L
        val schoolHours = checkIsSchoolHours(loadedSettings)
        val locked = remainingSecs <= 0

        val initialScreen = if (!loadedProfile.isOnboardingCompleted) {
            AppScreen.ONBOARDING
        } else if (locked) {
            AppScreen.LOCK
        } else {
            AppScreen.UNLOCKED
        }

        _uiState.update {
            it.copy(
                profile = loadedProfile,
                parentSettings = loadedSettings,
                isLocked = locked,
                remainingUnlockedSeconds = remainingSecs,
                isSchoolHoursActive = schoolHours,
                currentScreen = initialScreen
            )
        }
    }

    private fun checkIsSchoolHours(settings: ParentSettings): Boolean {
        if (!settings.schoolSchedule.enabled) return false
        val cal = Calendar.getInstance()
        val dayOfWeek = when (cal.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> 1
            Calendar.TUESDAY -> 2
            Calendar.WEDNESDAY -> 3
            Calendar.THURSDAY -> 4
            Calendar.FRIDAY -> 5
            Calendar.SATURDAY -> 6
            Calendar.SUNDAY -> 7
            else -> 1
        }
        if (!settings.schoolSchedule.activeDays.contains(dayOfWeek)) {
            return false
        }
        val currentMinutes = cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE)
        val startMinutes = settings.schoolSchedule.startHour * 60 + settings.schoolSchedule.startMinute
        val endMinutes = settings.schoolSchedule.endHour * 60 + settings.schoolSchedule.endMinute

        return currentMinutes in startMinutes..endMinutes
    }

    private fun startTimerLoop() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000L)
                val unlockedUntil = prefs.getUnlockedUntilTimestamp()
                val now = System.currentTimeMillis()
                val remaining = if (unlockedUntil > now) (unlockedUntil - now) / 1000L else 0L
                val schoolHours = checkIsSchoolHours(_uiState.value.parentSettings)
                val shouldBeLocked = remaining <= 0

                val wasUnlocked = _uiState.value.remainingUnlockedSeconds > 0

                // Update notification periodically
                if (remaining > 0) {
                    if (Math.abs(remaining - lastNotifUpdateSecs) >= 30 || lastNotifUpdateSecs == 0L) {
                        lastNotifUpdateSecs = remaining
                        UnlockedNotificationHelper.showUnlockedNotification(getApplication(), remaining)
                    }
                } else if (wasUnlocked && remaining <= 0) {
                    UnlockedNotificationHelper.cancelNotification(getApplication())
                    UnlockedNotificationHelper.showRelockedNotification(getApplication())
                }

                _uiState.update { current ->
                    val screen = if (current.currentScreen == AppScreen.ONBOARDING) {
                        current.currentScreen
                    } else if (shouldBeLocked && current.currentScreen == AppScreen.UNLOCKED) {
                        AppScreen.LOCK
                    } else current.currentScreen

                    current.copy(
                        remainingUnlockedSeconds = remaining,
                        isSchoolHoursActive = schoolHours,
                        isLocked = shouldBeLocked,
                        currentScreen = screen
                    )
                }
            }
        }
    }

    fun checkAndEnforceLock() {
        val unlockedUntil = prefs.getUnlockedUntilTimestamp()
        val now = System.currentTimeMillis()
        val remaining = if (unlockedUntil > now) (unlockedUntil - now) / 1000L else 0L
        if (remaining <= 0) {
            _uiState.update { current ->
                val targetScreen = if (current.currentScreen == AppScreen.ONBOARDING || current.currentScreen == AppScreen.ACTIVITY_RUNNER) {
                    current.currentScreen
                } else {
                    AppScreen.LOCK
                }
                current.copy(
                    remainingUnlockedSeconds = 0L,
                    isLocked = true,
                    currentScreen = targetScreen
                )
            }
        }
    }

    fun navigateTo(screen: AppScreen) {
        _uiState.update {
            it.copy(
                previousScreen = it.currentScreen,
                currentScreen = screen,
                parentPinError = null
            )
        }
    }

    fun navigateBack() {
        _uiState.update {
            val destination = when (it.currentScreen) {
                AppScreen.ACTIVITY_RUNNER -> {
                    // Cancelling a challenge without finishing NEVER unlocks the phone!
                    AppScreen.LOCK
                }
                AppScreen.AVATAR_CREATOR, AppScreen.WEEKEND_HUB,
                AppScreen.PARENTAL_CONTROLS, AppScreen.LAUNCHER_HELP -> {
                    if (it.remainingUnlockedSeconds > 0 && !it.isLocked) AppScreen.UNLOCKED else AppScreen.LOCK
                }
                else -> {
                    if (it.remainingUnlockedSeconds > 0 && !it.isLocked) AppScreen.UNLOCKED else AppScreen.LOCK
                }
            }
            it.copy(
                currentScreen = destination,
                isLocked = it.remainingUnlockedSeconds <= 0,
                parentPinError = null
            )
        }
    }

    fun cancelChallengeAndReturnToLock() {
        _uiState.update {
            it.copy(
                currentScreen = AppScreen.LOCK,
                isLocked = it.remainingUnlockedSeconds <= 0,
                statusMessage = "Reto cancelado. El teléfono continúa bloqueado."
            )
        }
    }

    fun lockNow() {
        prefs.setUnlockedUntilTimestamp(0L)
        UnlockedNotificationHelper.cancelNotification(getApplication())
        _uiState.update {
            it.copy(
                remainingUnlockedSeconds = 0L,
                isLocked = true,
                currentScreen = AppScreen.LOCK
            )
        }
    }

    fun triggerExitToBackground() {
        _uiState.update { it.copy(shouldExitToBackground = true) }
    }

    fun clearExitToBackgroundFlag() {
        _uiState.update { it.copy(shouldExitToBackground = false) }
    }

    fun completeOnboarding(profile: KidProfile) {
        val updated = profile.copy(isOnboardingCompleted = true)
        prefs.saveProfile(updated)
        _uiState.update {
            val dest = if (it.isLocked) AppScreen.LOCK else AppScreen.UNLOCKED
            it.copy(
                profile = updated,
                currentScreen = dest,
                statusMessage = "¡Bienvenido a BloqEscolar, ${updated.name}!"
            )
        }
    }

    fun updateStudentGrade(newGrade: String) {
        val updated = _uiState.value.profile.copy(gradeLevel = newGrade)
        prefs.saveProfile(updated)
        _uiState.update { it.copy(profile = updated) }
    }

    // --- Educational Activity Flow ---

    fun startChallenge(subject: Subject) {
        _uiState.update {
            it.copy(
                currentScreen = AppScreen.ACTIVITY_RUNNER,
                activityState = ActivityState(
                    isLoading = true,
                    selectedSubject = subject
                )
            )
        }

        viewModelScope.launch {
            val questions = GeminiAiService.generateQuestions(
                subject = subject,
                gradeLevel = _uiState.value.profile.gradeLevel,
                age = _uiState.value.profile.age,
                count = 3
            )

            _uiState.update {
                it.copy(
                    activityState = it.activityState.copy(
                        isLoading = false,
                        questions = questions,
                        currentIndex = 0,
                        selectedOptionIndex = null,
                        isAnswerChecked = false,
                        isFinished = false,
                        correctAnswersCount = 0
                    )
                )
            }
        }
    }

    fun selectActivityOption(optionIndex: Int) {
        if (_uiState.value.activityState.isAnswerChecked) return
        _uiState.update {
            it.copy(
                activityState = it.activityState.copy(
                    selectedOptionIndex = optionIndex
                )
            )
        }
    }

    fun checkAnswer() {
        val state = _uiState.value.activityState
        val selected = state.selectedOptionIndex ?: return
        val currentQ = state.questions.getOrNull(state.currentIndex) ?: return
        val isCorrect = (selected == currentQ.correctIndex)

        _uiState.update {
            it.copy(
                activityState = state.copy(
                    isAnswerChecked = true,
                    isCorrect = isCorrect,
                    correctAnswersCount = if (isCorrect) state.correctAnswersCount + 1 else state.correctAnswersCount
                )
            )
        }
    }

    fun nextQuestionOrFinish() {
        val state = _uiState.value.activityState
        if (state.currentIndex + 1 < state.questions.size) {
            _uiState.update {
                it.copy(
                    activityState = state.copy(
                        currentIndex = state.currentIndex + 1,
                        selectedOptionIndex = null,
                        isAnswerChecked = false,
                        isCorrect = false
                    )
                )
            }
        } else {
            // Finish session and compute rewards
            val minutesToReward = _uiState.value.parentSettings.minutesPerChallenge
            val xpEarned = state.correctAnswersCount * 30 + 15
            val coinsEarned = state.correctAnswersCount * 10 + 5

            val result = ActivitySessionResult(
                totalAnswered = state.questions.size,
                correctCount = state.correctAnswersCount,
                xpEarned = xpEarned,
                coinsEarned = coinsEarned,
                minutesUnlocked = minutesToReward
            )

            // Update kid profile
            val updatedProfile = _uiState.value.profile.let { prof ->
                prof.copy(
                    xp = prof.xp + xpEarned,
                    coins = prof.coins + coinsEarned,
                    totalActivitiesSolved = prof.totalActivitiesSolved + 1,
                    weekendBankMinutes = prof.weekendBankMinutes + 10
                )
            }
            prefs.saveProfile(updatedProfile)

            // Unlock screen time
            val addMillis = minutesToReward * 60 * 1000L
            val currentUntil = prefs.getUnlockedUntilTimestamp()
            val baseTime = if (currentUntil > System.currentTimeMillis()) currentUntil else System.currentTimeMillis()
            val newUntil = baseTime + addMillis
            prefs.setUnlockedUntilTimestamp(newUntil)

            val remaining = (newUntil - System.currentTimeMillis()) / 1000L
            UnlockedNotificationHelper.showUnlockedNotification(getApplication(), remaining)

            _uiState.update {
                it.copy(
                    profile = updatedProfile,
                    isLocked = false,
                    remainingUnlockedSeconds = remaining,
                    activityState = state.copy(
                        isFinished = true,
                        result = result
                    )
                )
            }
        }
    }

    // --- Avatar Customization Flow ---

    fun updateAvatar(newConfig: AvatarConfig) {
        val updated = _uiState.value.profile.copy(avatar = newConfig)
        prefs.saveProfile(updated)
        _uiState.update { it.copy(profile = updated) }
    }

    fun purchaseCosmetic(itemId: String, costCoins: Int, newConfig: AvatarConfig) {
        val currentProf = _uiState.value.profile
        if (currentProf.coins >= costCoins) {
            val updated = currentProf.copy(
                coins = currentProf.coins - costCoins,
                unlockedCosmetics = currentProf.unlockedCosmetics + itemId,
                avatar = newConfig
            )
            prefs.saveProfile(updated)
            _uiState.update {
                it.copy(
                    profile = updated,
                    statusMessage = "¡Artículo desbloqueado con éxito!"
                )
            }
        } else {
            _uiState.update {
                it.copy(statusMessage = "Monedas insuficientes. ¡Resuelve más retos escolares!")
            }
        }
    }

    // --- Weekend Rewards Hub ---

    fun spinWheel() {
        val prizeCoins = (10..30).random()
        val prizeXp = (20..50).random()
        val updated = _uiState.value.profile.let {
            it.copy(
                coins = it.coins + prizeCoins,
                xp = it.xp + prizeXp
            )
        }
        prefs.saveProfile(updated)
        _uiState.update {
            it.copy(
                profile = updated,
                statusMessage = "¡Giraste la ruleta y ganaste +$prizeCoins Monedas y +$prizeXp XP! 🎉"
            )
        }
    }

    fun redeemWeekendBank(minutes: Int) {
        val profile = _uiState.value.profile
        if (profile.weekendBankMinutes >= minutes) {
            val updated = profile.copy(
                weekendBankMinutes = profile.weekendBankMinutes - minutes
            )
            prefs.saveProfile(updated)

            val addMillis = minutes * 60 * 1000L
            val currentUntil = prefs.getUnlockedUntilTimestamp()
            val baseTime = if (currentUntil > System.currentTimeMillis()) currentUntil else System.currentTimeMillis()
            val newUntil = baseTime + addMillis
            prefs.setUnlockedUntilTimestamp(newUntil)

            val remaining = (newUntil - System.currentTimeMillis()) / 1000L
            UnlockedNotificationHelper.showUnlockedNotification(getApplication(), remaining)

            _uiState.update {
                it.copy(
                    profile = updated,
                    isLocked = false,
                    remainingUnlockedSeconds = remaining,
                    statusMessage = "¡Canjeaste $minutes min de tu banco escolar!"
                )
            }
        }
    }

    // --- Parental Controls Flow ---

    fun verifyParentPin(pin: String): Boolean {
        if (pin == _uiState.value.parentSettings.pin) {
            _uiState.update {
                it.copy(
                    isParentAuthenticated = true,
                    parentPinError = null
                )
            }
            return true
        } else {
            _uiState.update {
                it.copy(parentPinError = "PIN incorrecto. Inténtalo de nuevo.")
            }
            return false
        }
    }

    fun parentUnlockDirectly(minutes: Int = 45) {
        val addMillis = minutes * 60 * 1000L
        val newUntil = System.currentTimeMillis() + addMillis
        prefs.setUnlockedUntilTimestamp(newUntil)
        val remaining = addMillis / 1000L
        UnlockedNotificationHelper.showUnlockedNotification(getApplication(), remaining)

        _uiState.update {
            it.copy(
                isLocked = false,
                remainingUnlockedSeconds = remaining,
                currentScreen = AppScreen.UNLOCKED,
                isParentAuthenticated = false,
                statusMessage = "Desbloqueado por control parental ($minutes min)"
            )
        }
    }

    fun saveParentSettings(newSettings: ParentSettings) {
        prefs.saveParentSettings(newSettings)
        _uiState.update {
            it.copy(
                parentSettings = newSettings,
                statusMessage = "Configuración parental guardada correctamente"
            )
        }
    }

    fun clearStatusMessage() {
        _uiState.update { it.copy(statusMessage = null) }
    }
}
