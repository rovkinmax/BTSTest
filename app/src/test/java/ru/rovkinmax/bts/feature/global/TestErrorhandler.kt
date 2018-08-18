package ru.rovkinmax.bts.feature.global

import io.reactivex.functions.Consumer
import ru.rovkinmax.bts.feature.global.domain.CommonException
import ru.rovkinmax.bts.feature.global.presentation.ErrorHandler
import ru.rovkinmax.bts.feature.global.presentation.view.ErrorView

class TestErrorhandler : ErrorHandler {
    override fun proceed(errorView: ErrorView?, action: ((CommonException) -> Unit)?): Consumer<Throwable> {
        return Consumer { }
    }

    override fun proceedInvoke(errorView: ErrorView?, e: Throwable, action: ((CommonException) -> Unit)?) {

    }

    override fun commonExceptionToString(exception: CommonException): String {
        return ""
    }
}