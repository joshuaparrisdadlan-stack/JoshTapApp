package com.parris.yotolite

import android.app.Application
import com.parris.yotolite.data.AppDatabase

class MyApplication : Application() {
    lateinit var db: AppDatabase
    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getInstance(this)
    }
}
