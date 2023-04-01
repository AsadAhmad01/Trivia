package com.goally.assignment.data.base

import android.app.Application
import android.content.Context


open class App : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }


}