package ru.rovkinmax.bts.model

import android.content.Context
import android.support.v4.content.ContextCompat
import javax.inject.Inject

interface ResourceProvider {
    fun getString(resId: Int): String
    fun getString(resId: Int, vararg arg: Any): String
    fun getColor(colorRes: Int): Int
    fun getDimension(dimenRes: Int): Float
    fun getStringArray(resId: Int): Array<String>
}

class AndroidResourceProvider @Inject constructor(private val context: Context) : ResourceProvider {
    override fun getString(resId: Int): String = context.getString(resId)
    override fun getString(resId: Int, vararg arg: Any): String = context.getString(resId, *arg)
    override fun getColor(colorRes: Int): Int = ContextCompat.getColor(context, colorRes)
    override fun getDimension(dimenRes: Int): Float = context.resources.getDimension(dimenRes)
    override fun getStringArray(resId: Int): Array<String> = context.resources.getStringArray(resId)
}