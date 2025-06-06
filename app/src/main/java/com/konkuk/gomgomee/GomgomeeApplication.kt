package com.konkuk.gomgomee

import android.app.Application
import com.konkuk.gomgomee.data.util.DataInitializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GomgomeeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // 앱 시작 시 데이터 초기화
        CoroutineScope(Dispatchers.IO).launch {
            val dataInitializer = DataInitializer(applicationContext)
            dataInitializer.initializeDataIfNeeded()
        }
    }
} 