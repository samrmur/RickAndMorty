package com.saam.rickandmorty.util.views

import androidx.annotation.StringRes

interface MessageView {
    fun showMessage(@StringRes resId: Int)
    fun showMessage(message: String)
    fun showGenericErrorMessage()
}