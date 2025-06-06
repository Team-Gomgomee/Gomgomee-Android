package com.konkuk.gomgomee

import android.app.Application
import android.util.Log
import com.konkuk.gomgomee.data.util.DataInitializer
import com.konkuk.gomgomee.util.context.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GomgomeeApplication : Application() {
    companion object {
        private const val TAG = "GomgomeeApplication"
    }

    override fun onCreate() {
        super.onCreate()
        
        // 앱 시작 시 데이터 초기화
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d(TAG, "Starting application initialization")
                val dataInitializer = DataInitializer(applicationContext)
                
                // 강제로 데이터 재초기화
                dataInitializer.reinitializeData()
                
                Log.d(TAG, "Application initialization completed")
            } catch (e: Exception) {
                Log.e(TAG, "Error during application initialization", e)
                CoroutineScope(Dispatchers.Main).launch {
                    toast("데이터 초기화 중 오류가 발생했습니다.")
                }
            }
        }
    }
} 