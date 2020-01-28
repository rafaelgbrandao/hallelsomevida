package com.hallel.hallelsomevida

import android.app.Application
import com.hallel.data.dataModule
import com.hallel.domain.domainModule
import com.hallel.presentation.di.presentationModule
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            modules(
                listOf(
                    dataModule,
                    domainModule,
                    presentationModule
                )
            )
        }
    }
}