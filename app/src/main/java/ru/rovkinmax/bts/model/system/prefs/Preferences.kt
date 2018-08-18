package ru.rovkinmax.bts.model.system.prefs

import android.content.Context
import javax.inject.Inject


class Preferences @Inject constructor(context: Context) {
    private val appContext: Context = context.applicationContext
    var suggestions by appContext.bindPreference("[]")
}