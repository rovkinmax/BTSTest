package ru.rovkinmax.bts.di

import android.content.Context
import toothpick.Toothpick

object DI {
    const val SCOPE_APP = "SCOPE_APP"
    const val SCOPE_FLOW_PHOTOS = "SCOPE_FLOW_PHOTOS"

    fun initAppScope(context: Context) {
        Toothpick.openScope(SCOPE_APP).apply {
            installModules(moduleApp(context), moduleNetwork())
        }
    }
}