package com.example.proditi

import android.app.Application
import com.example.proditi.repositori.AppContainer
import com.example.proditi.repositori.DefaultAppContainer

class ProditiApplications : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}