package com.parris.joshtap

import android.app.Application
import com.parris.joshtap.data.AppDatabase

class MyApplication : Application() {
    lateinit var db: AppDatabase
    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getInstance(this)
    }
}
