package com.homedev.weather

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

/**
 * Created by Alexandr Zheleznyakov on 2019-11-08.
 */
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}