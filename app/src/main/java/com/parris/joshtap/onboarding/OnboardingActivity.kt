package com.parris.joshtap.onboarding

import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.parris.joshtap.prefs.OnboardingPrefs
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.parris.joshtap.MainActivity
import com.parris.joshtap.R

/**
 * OnboardingActivity guides users through permissions and initial setup.
 * Handles:
 * - NFC permission request (Android 12+)
 * - Media read permission (Android 10+)
 * - Feature availability check
 */
class OnboardingActivity : AppCompatActivity() {
    private lateinit var tvWelcome: TextView
    private lateinit var cbNfc: CheckBox
    private lateinit var cbMedia: CheckBox
    private lateinit var btnContinue: Button
    private var allPermissionsGranted = false

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 100
        private val REQUIRED_PERMISSIONS = mutableListOf(
            android.Manifest.permission.NFC
        ).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                add(android.Manifest.permission.READ_MEDIA_AUDIO)
            }
        }.toTypedArray()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Skip onboarding if already completed
        if (OnboardingPrefs.isDone(this)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }
        setContentView(R.layout.activity_onboarding)

        tvWelcome = findViewById(R.id.tvWelcome)
        cbNfc = findViewById(R.id.cbNfc)
        cbMedia = findViewById(R.id.cbMedia)
        btnContinue = findViewById(R.id.btnContinue)

        tvWelcome.text = """
            Welcome to YotoLite!
            
            YotoLite lets you store audio tracks and play them via NFC cards.
            
            To get started, we need permission to:
        """.trimIndent()

        btnContinue.setOnClickListener {
            requestPermissions()
        }

        checkPermissionStatus()
    }

    private fun checkPermissionStatus() {
        val nfcGranted = ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.NFC
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED

        val mediaGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.READ_MEDIA_AUDIO
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        } else {
            true // Not required on older Android
        }

        cbNfc.isChecked = nfcGranted
        cbMedia.isChecked = mediaGranted

        allPermissionsGranted = nfcGranted && mediaGranted
        btnContinue.text = if (allPermissionsGranted) "Continue to App" else "Request Permissions"
    }

    private fun requestPermissions() {
        if (allPermissionsGranted) {
            navigateToApp()
            return
        }

        ActivityCompat.requestPermissions(
            this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            checkPermissionStatus()
            if (allPermissionsGranted) {
                Toast.makeText(this, "Permissions granted!", Toast.LENGTH_SHORT).show()
                navigateToApp()
            }
        }
    }

    private fun navigateToApp() {
        // mark onboarding completed so we don't show it again
        OnboardingPrefs.markDone(this)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
