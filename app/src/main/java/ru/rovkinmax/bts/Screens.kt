package ru.rovkinmax.bts

import ru.rovkinmax.bts.feature.global.FlowFragment
import ru.rovkinmax.bts.feature.photos.presentation.PhotosFlowFragment
import ru.rovkinmax.bts.feature.splash.presentation.view.SplashFragment

object Screens {

    const val FLOW_SPLASH = "FLOW_SPLASH"

    const val FLOW_PHOTOS = "FLOW_PHOTOS"
    const val SCREEN_PHOTO_SEARCH = "SCREEN_PHOTO_SEARCH"
    const val SCREEN_PHOTO_DETAIL = "SCREEN_PHOTO_DETAIL"


    @Suppress("UNCHECKED_CAST")
    fun getFlowFragment(flowKey: String, data: Any? = null): FlowFragment? {
        return when (flowKey) {
            FLOW_SPLASH -> SplashFragment.newInstance()
            FLOW_PHOTOS -> PhotosFlowFragment.newInstance()
            else -> null
        }
    }
}