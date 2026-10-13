package com.example

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.Subject
import com.example.ui.AppScreen
import com.example.ui.BloqEscolarViewModel
import com.example.ui.screens.ActivityRunnerScreen
import com.example.ui.screens.AvatarCreatorScreen
import com.example.ui.screens.EmergencyDialog
import com.example.ui.screens.LauncherHelpDialog
import com.example.ui.screens.OnboardingProfileScreen
import com.example.ui.screens.ParentalControlDialog
import com.example.ui.screens.PhoneLockScreen
import com.example.ui.screens.PhoneUnlockedScreen
import com.example.ui.screens.WeekendHubScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.util.DeviceAppsManager

class MainActivity : ComponentActivity() {
    private val viewModel: BloqEscolarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                BloqEscolarApp(
                    viewModel = viewModel,
                    onExitToBackground = {
                        val launched = DeviceAppsManager.exitToSystemHomeScreen(this@MainActivity)
                        if (!launched) {
                            Toast.makeText(this@MainActivity, "Acceso desbloqueado al teléfono", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadInstalledApps()
        viewModel.checkAndEnforceLock()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val state = viewModel.uiState.value
            when (state.currentScreen) {
                AppScreen.LOCK -> {
                    Toast.makeText(
                        this,
                        "¡Teléfono bloqueado! Resuelve un reto escolar para usar el teléfono.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return true
                }
                AppScreen.ACTIVITY_RUNNER -> {
                    viewModel.cancelChallengeAndReturnToLock()
                    Toast.makeText(
                        this,
                        "Reto cancelado. El teléfono continúa bloqueado.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return true
                }
                AppScreen.ONBOARDING -> {
                    return true
                }
                else -> {
                    // Let other screens handle back
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}

@Composable
fun BloqEscolarApp(
    viewModel: BloqEscolarViewModel,
    onExitToBackground: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    var showEmergencyModal by remember { mutableStateOf(false) }
    var showParentalModal by remember { mutableStateOf(false) }
    var showLauncherHelpModal by remember { mutableStateOf(false) }

    LaunchedEffect(state.statusMessage) {
        state.statusMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearStatusMessage()
        }
    }

    LaunchedEffect(state.shouldExitToBackground) {
        if (state.shouldExitToBackground) {
            viewModel.clearExitToBackgroundFlag()
            onExitToBackground()
        }
    }

    // Handle back button navigation
    BackHandler(enabled = true) {
        when (state.currentScreen) {
            AppScreen.ACTIVITY_RUNNER -> {
                // If user presses back during challenge, cancel challenge and return to LOCK screen.
                // NEVER exit to Android or unlock without solving!
                viewModel.cancelChallengeAndReturnToLock()
                Toast.makeText(
                    context,
                    "Reto cancelado. El teléfono continúa bloqueado.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            AppScreen.LOCK -> {
                // Keep locked on lock screen, do NOT exit
                Toast.makeText(
                    context,
                    "¡Teléfono bloqueado! Resuelve un reto escolar para usar el teléfono.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            AppScreen.ONBOARDING -> {
                // Keep in onboarding until user completes
            }
            AppScreen.AVATAR_CREATOR, AppScreen.WEEKEND_HUB -> {
                viewModel.navigateBack()
            }
            AppScreen.UNLOCKED -> {
                // In unlocked mode, pressing back minimizes the app to let user use their phone!
                onExitToBackground()
            }
            else -> {
                viewModel.navigateBack()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            when (state.currentScreen) {
                AppScreen.ONBOARDING -> {
                    OnboardingProfileScreen(
                        initialProfile = state.profile,
                        onCompleteOnboarding = { profile ->
                            viewModel.completeOnboarding(profile)
                        }
                    )
                }

                AppScreen.LOCK -> {
                    PhoneLockScreen(
                        state = state,
                        onStartChallenge = { subject ->
                            viewModel.startChallenge(subject)
                        },
                        onOpenEmergency = { showEmergencyModal = true },
                        onOpenParentControl = { showParentalModal = true },
                        onOpenAvatarCreator = { viewModel.navigateTo(AppScreen.AVATAR_CREATOR) },
                        onOpenWeekendHub = { viewModel.navigateTo(AppScreen.WEEKEND_HUB) },
                        onOpenLauncherHelp = { showLauncherHelpModal = true }
                    )
                }

                AppScreen.UNLOCKED -> {
                    PhoneUnlockedScreen(
                        state = state,
                        onMoreTimeClick = { subject ->
                            viewModel.startChallenge(subject)
                        },
                        onLockNowClick = { viewModel.lockNow() },
                        onOpenAvatarCreator = { viewModel.navigateTo(AppScreen.AVATAR_CREATOR) },
                        onOpenWeekendHub = { viewModel.navigateTo(AppScreen.WEEKEND_HUB) },
                        onOpenParentControl = { showParentalModal = true },
                        onExitToSystemHome = onExitToBackground
                    )
                }

                AppScreen.ACTIVITY_RUNNER -> {
                    ActivityRunnerScreen(
                        activityState = state.activityState,
                        onSelectOption = { viewModel.selectActivityOption(it) },
                        onCheckAnswer = { viewModel.checkAnswer() },
                        onNextQuestion = { viewModel.nextQuestionOrFinish() },
                        onBackClick = { viewModel.cancelChallengeAndReturnToLock() },
                        onFinishAndUnlock = { viewModel.navigateTo(AppScreen.UNLOCKED) },
                        onExitToBackground = {
                            viewModel.navigateTo(AppScreen.UNLOCKED)
                            onExitToBackground()
                        }
                    )
                }

                AppScreen.AVATAR_CREATOR -> {
                    AvatarCreatorScreen(
                        profile = state.profile,
                        onSaveAvatar = { newConfig ->
                            viewModel.updateAvatar(newConfig)
                        },
                        onPurchaseCosmetic = { itemId, cost, config ->
                            viewModel.purchaseCosmetic(itemId, cost, config)
                        },
                        onBackClick = { viewModel.navigateBack() }
                    )
                }

                AppScreen.WEEKEND_HUB -> {
                    WeekendHubScreen(
                        profile = state.profile,
                        onSpinWheel = { viewModel.spinWheel() },
                        onRedeemBank = { mins -> viewModel.redeemWeekendBank(mins) },
                        onBackClick = { viewModel.navigateBack() }
                    )
                }

                else -> {
                    PhoneLockScreen(
                        state = state,
                        onStartChallenge = { viewModel.startChallenge(it) },
                        onOpenEmergency = { showEmergencyModal = true },
                        onOpenParentControl = { showParentalModal = true },
                        onOpenAvatarCreator = { viewModel.navigateTo(AppScreen.AVATAR_CREATOR) },
                        onOpenWeekendHub = { viewModel.navigateTo(AppScreen.WEEKEND_HUB) },
                        onOpenLauncherHelp = { showLauncherHelpModal = true }
                    )
                }
            }

            // Dialogs
            if (showEmergencyModal) {
                EmergencyDialog(
                    contacts = state.parentSettings.emergencyContacts,
                    onDismiss = { showEmergencyModal = false }
                )
            }

            if (showParentalModal) {
                ParentalControlDialog(
                    parentSettings = state.parentSettings,
                    studentGradeLevel = state.profile.gradeLevel,
                    onUpdateGradeLevel = { newGrade -> viewModel.updateStudentGrade(newGrade) },
                    isAuthenticated = state.isParentAuthenticated,
                    pinError = state.parentPinError,
                    onVerifyPin = { pin -> viewModel.verifyParentPin(pin) },
                    onDirectUnlock = { mins -> viewModel.parentUnlockDirectly(mins) },
                    onSaveSettings = { newSettings -> viewModel.saveParentSettings(newSettings) },
                    onDismiss = { showParentalModal = false }
                )
            }

            if (showLauncherHelpModal) {
                LauncherHelpDialog(
                    onDismiss = { showLauncherHelpModal = false }
                )
            }
        }
    }
}
