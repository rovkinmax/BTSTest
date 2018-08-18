package ru.rovkinmax.bts.feature.global.presentation.view

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface PaginationView : BaseView {
    fun showPaginationLoading()
    fun hidePaginationLoading()
}