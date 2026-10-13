package com.example.util

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.provider.Settings
import android.widget.Toast
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
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
            try {
                context.startActivity(launchIntent)
            } catch (e: Exception) {
                Toast.makeText(context, "No se pudo abrir la aplicación: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "No se encontró el ejecutable para esta aplicación", Toast.LENGTH_SHORT).show()
        }
    }

    fun openHomeSettings(context: Context) {
        try {
            val intent = Intent(Settings.ACTION_HOME_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (_: Exception) {
            try {
                val intent = Intent(Settings.ACTION_SETTINGS).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
            } catch (_: Exception) {
                Toast.makeText(context, "Abre Ajustes > Aplicaciones > Inicio", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun exitToSystemHomeScreen(activity: Activity): Boolean {
        val pm = activity.packageManager
        val homeIntent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
        }

        // Query all installed home/launcher apps (0 = all, NOT MATCH_DEFAULT_ONLY)
        val resolveInfos: List<ResolveInfo> = try {
            pm.queryIntentActivities(homeIntent, 0)
        } catch (_: Exception) {
            emptyList()
        }

        // Known manufacturer system launchers prioritized
        val knownSystemLaunchers = listOf(
            "com.miui.home",                         // Xiaomi / Redmi / POCO
            "com.sec.android.app.launcher",          // Samsung One UI
            "com.google.android.apps.nexuslauncher", // Pixel Launcher
            "com.android.launcher3",                 // AOSP / Motorola / Nokia
            "com.motorola.launcher3",
            "com.oppo.launcher",                     // OPPO
            "com.coloros.launcher",                  // Realme / ColorOS
            "net.oneplus.launcher",                  // OnePlus
            "com.huawei.android.launcher",           // Huawei
            "com.bbk.launcher2",                     // Vivo
            "com.vivo.launcher",
            "com.transsion.hilauncher",              // Tecno / Infinix
            "com.transsion.XOSLauncher",
            "com.android.launcher"
        )

        val otherHomes = resolveInfos.filter {
            it.activityInfo.packageName != activity.packageName &&
            it.activityInfo.packageName != "android"
        }

        val targetHome = otherHomes.minByOrNull { resolve ->
            val idx = knownSystemLaunchers.indexOf(resolve.activityInfo.packageName)
            if (idx >= 0) idx else 999
        }

        if (targetHome != null) {
            val pkg = targetHome.activityInfo.packageName
            val cls = targetHome.activityInfo.name

            // Method 1: Start system home by component
            try {
                val intent = Intent(Intent.ACTION_MAIN).apply {
                    addCategory(Intent.CATEGORY_HOME)
                    component = ComponentName(pkg, cls)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                }
                activity.startActivity(intent)
                return true
            } catch (_: Exception) {}

            // Method 2: Launch by package intent
            try {
                val launchIntent = pm.getLaunchIntentForPackage(pkg)
                if (launchIntent != null) {
                    launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                    activity.startActivity(launchIntent)
                    return true
                }
            } catch (_: Exception) {}
        }

        // Method 3: Try generic HOME intent without specifying our component
        try {
            val intent = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            activity.startActivity(Intent.createChooser(intent, "Selecciona tu pantalla de inicio"))
            return true
        } catch (_: Exception) {}

        // Fallback: Minimize to back
        activity.moveTaskToBack(true)
        return false
    }
}
