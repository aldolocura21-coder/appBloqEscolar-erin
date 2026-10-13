package com.example.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class InstalledAppItem(
    val label: String,
    val packageName: String,
    val activityName: String,
    val icon: Drawable? = null
)

object DeviceAppsManager {

    suspend fun getInstalledApps(context: Context): List<InstalledAppItem> = withContext(Dispatchers.IO) {
        val pm = context.packageManager
        val mainIntent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val resolveInfos: List<ResolveInfo> = try {
            pm.queryIntentActivities(mainIntent, 0)
        } catch (_: Exception) {
            emptyList()
        }

        val ourPackage = context.packageName

        resolveInfos
            .filter { it.activityInfo.packageName != ourPackage }
            .map { resolveInfo ->
                val label = resolveInfo.loadLabel(pm).toString()
                val pkg = resolveInfo.activityInfo.packageName
                val act = resolveInfo.activityInfo.name
                val icon = try {
                    resolveInfo.loadIcon(pm)
                } catch (_: Exception) {
                    null
                }
                InstalledAppItem(
                    label = if (label.isNotBlank()) label else pkg,
                    packageName = pkg,
                    activityName = act,
                    icon = icon
                )
            }
            .sortedBy { it.label.lowercase() }
    }

    fun launchApp(context: Context, packageName: String) {
        val pm = context.packageManager
        val launchIntent = pm.getLaunchIntentForPackage(packageName)
        if (launchIntent != null) {
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            try {
                context.startActivity(launchIntent)
                (context as? Activity)?.moveTaskToBack(true)
            } catch (_: Exception) {}
        }
    }

    fun exitToSystemHomeScreen(activity: Activity) {
        val pm = activity.packageManager
        val homeIntent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
        }

        val resolveInfos = pm.queryIntentActivities(homeIntent, PackageManager.MATCH_DEFAULT_ONLY)
        val otherHome = resolveInfos.firstOrNull { it.activityInfo.packageName != activity.packageName }

        if (otherHome != null) {
            val intent = Intent(Intent.ACTION_MAIN).apply {
                setClassName(otherHome.activityInfo.packageName, otherHome.activityInfo.name)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            try {
                activity.startActivity(intent)
                activity.moveTaskToBack(true)
                return
            } catch (_: Exception) {}
        }

        // Fallback: minimize task to background
        activity.moveTaskToBack(true)
    }
}
