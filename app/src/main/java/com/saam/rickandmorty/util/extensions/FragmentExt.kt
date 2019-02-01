package com.saam.rickandmorty.util.extensions

import android.os.Looper
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun Fragment.showToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
}

fun Fragment.showToast(resId: Int) {
    Toast.makeText(this.context, resId, Toast.LENGTH_LONG).show()
}