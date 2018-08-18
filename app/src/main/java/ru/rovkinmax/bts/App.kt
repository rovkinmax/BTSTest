package ru.rovkinmax.bts

import android.app.Application
import ru.rovkinmax.bts.di.DI
import timber.log.Timber
import toothpick.Toothpick
import toothpick.configuration.Configuration
import toothpick.registries.FactoryRegistryLocator
import toothpick.registries.MemberInjectorRegistryLocator


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        initDi()
    }

    private fun initDi() {
        val configuration = if (BuildConfig.DEBUG)
            Configuration.forDevelopment().preventMultipleRootScopes().disableReflection()
        else Configuration.forProduction().disableReflection()

        Toothpick.setConfiguration(configuration)
        FactoryRegistryLocator.setRootRegistry(ru.rovkinmax.bts.FactoryRegistry())
        MemberInjectorRegistryLocator.setRootRegistry(ru.rovkinmax.bts.MemberInjectorRegistry())

        DI.initAppScope(context = this)
    }
}