package com.example.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import com.example.MainActivity
import com.example.data.AppPreferencesManager

class BloqAccessibilityService : AccessibilityService() {

    private lateinit var prefs: AppPreferencesManager

    override fun onCreate() {
        super.onCreate()
        prefs = AppPreferencesManager(this)
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED or AccessibilityEvent.TYPE_WINDOWS_CHANGED
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS or
                    AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS
            notificationTimeout = 20
        }
        serviceInfo = info
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        val unlockedUntil = prefs.getUnlockedUntilTimestamp()
        val isLocked = (unlockedUntil <= System.currentTimeMillis())

        // Only enforce when phone is locked
        if (!isLocked) return

        val targetPackage = event.packageName?.toString() ?: return
        val targetClass = event.className?.toString() ?: ""

        // Allowed packages during lock: BloqEscolar itself or phone emergency dialer
        val ourPackage = packageName
        val isOurApp = targetPackage == ourPackage
        val isDialer = targetPackage.contains("dialer") ||
                targetPackage.contains("telecom") ||
                targetPackage.contains("incall")

        if (isOurApp || isDialer) {
            return
        }

        // If the user pressed recent apps ("|||" / Overview) or launched another app:
        // Close recents / overview immediately and pull BloqEscolar back to front
        try {
            performGlobalAction(GLOBAL_ACTION_BACK)
            performGlobalAction(GLOBAL_ACTION_HOME)
        } catch (_: Exception) {}

        try {
            val intent = Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            startActivity(intent)
        } catch (_: Exception) {}
    }

    override fun onInterrupt() {}
}
