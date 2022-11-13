package com.example.pulstestapp

import android.app.Application
import com.example.pulstestapp.data.AppContainer
import com.example.pulstestapp.data.BaseAppContainer

class NewsApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = BaseAppContainer()
    }
}