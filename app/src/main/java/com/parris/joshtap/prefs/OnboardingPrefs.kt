package com.parris.joshtap.prefs

import android.content.Context
import androidx.core.content.edit

object OnboardingPrefs {
    private const val PREFS_NAME = "joshtap_prefs"
    private const val KEY_ONBOARDING_DONE = "onboarding_done"

    fun isDone(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_ONBOARDING_DONE, false)
    }

    fun markDone(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit { putBoolean(KEY_ONBOARDING_DONE, true) }
    }
}
