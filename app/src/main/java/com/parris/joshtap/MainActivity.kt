package com.parris.joshtap

import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.os.Bundle
import com.parris.joshtap.nfc.NfcHandler
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import androidx.viewpager2.widget.ViewPager2
import com.parris.joshtap.data.AppDatabase
import com.parris.joshtap.data.AppRepository
import com.parris.joshtap.nfc.NfcViewModel
import com.parris.joshtap.nfc.NfcViewModelFactory

class MainActivity : AppCompatActivity() {
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var nfcViewModel: NfcViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = MainPagerAdapter(this)

        val tabs = findViewById<com.google.android.material.tabs.TabLayout>(R.id.tabs)
        val tabTitles = listOf("Play", "Library", "Cards")
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        PlayerController.initialize(this)

        // Initialize NFC ViewModel via factory
        val db = AppDatabase.getInstance(this)
        val repo = AppRepository(db)
        nfcViewModel = androidx.lifecycle.ViewModelProvider(this, NfcViewModelFactory(repo)).get(NfcViewModel::class.java)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
    }

    override fun onResume() {
        super.onResume()
        try {
            val filter = arrayOf(IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED))
            NfcHandler.enableForegroundDispatch(this, this, filter, arrayOf(arrayOf("android.nfc.tech.Ndef")))
        } catch (ex: Exception) {
            // ignore
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            NfcHandler.disableForegroundDispatch(this, this)
        } catch (ex: Exception) {
            // ignore
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.action == NfcAdapter.ACTION_TAG_DISCOVERED) {
            val tag: android.nfc.Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
            if (tag != null) {
                nfcViewModel.onTag(tag)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        PlayerController.release()
    }
}
