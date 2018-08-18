package ru.rovkinmax.bts.feature.global.presentation.view.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import ru.rovkinmax.bts.R
import ru.rovkinmax.bts.model.ui.extentions.showFragment


class QuestionDialog : DialogFragment() {

    companion object {
        fun show(fragmentManager: FragmentManager,
                 title: CharSequence,
                 message: CharSequence,
                 positiveFunc: (() -> Unit),
                 negativeFunc: (() -> Unit),
                 positiveButtonName: Int = R.string.button_ok,
                 negativeButtonName: Int = R.string.button_cancel,
                 isCancelable: Boolean = true) {
            val dialog = QuestionDialog().apply {
                this.title = title
                this.message = message
                positiveCallback = positiveFunc
                negativeCallback = negativeFunc
                this.positiveButtonName = positiveButtonName
                this.negativeButtonName = negativeButtonName
                this.isCancelable = isCancelable
            }

            fragmentManager.showFragment(dialog)
        }
    }

    private var positiveCallback: (() -> Unit)? = null
    private var negativeCallback: (() -> Unit)? = null
    private var title: CharSequence = ""
    private var message: CharSequence = ""
    private var positiveButtonName: Int = 0
    private var negativeButtonName: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity!!)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(positiveButtonName) { _, _ ->
                    positiveCallback?.invoke()
                    dismiss()
                }
                .setNegativeButton(negativeButtonName) { _, _ ->
                    negativeCallback?.invoke()
                    dismiss()
                }
                .create()
    }
}