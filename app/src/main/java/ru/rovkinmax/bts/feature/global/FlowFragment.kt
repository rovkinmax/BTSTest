package ru.rovkinmax.bts.feature.global

import ru.rovkinmax.bts.R
import ru.rovkinmax.bts.feature.global.presentation.view.BaseFragment
import ru.rovkinmax.bts.model.system.flow.FlowNavigator
import ru.rovkinmax.bts.model.system.flow.GlobalRouter
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

abstract class FlowFragment : BaseFragment() {

    override val layoutRes: Int = R.layout.fmt_flow

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: GlobalRouter

    protected val navigator: FlowNavigator by lazy { provideNavigator(router) }
    protected abstract fun provideNavigator(router: GlobalRouter): FlowNavigator

    override fun onResume() {
        super.onResume()
        if (this::navigatorHolder.isInitialized)
            navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        if (this::navigatorHolder.isInitialized)
            navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        val fragment = childFragmentManager.findFragmentById(R.id.container)
        (fragment as? BaseFragment)?.onBackPressed()
    }
}