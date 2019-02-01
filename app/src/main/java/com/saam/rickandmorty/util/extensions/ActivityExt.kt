package com.saam.rickandmorty.util.extensions

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.saam.rickandmorty.R

fun AppCompatActivity.addFragment(tag: String?, layoutRes: Int, fragment: () -> Fragment) {
    supportFragmentManager.beginTransaction()
        .add(layoutRes, fragment.invoke(), tag)
        .commit()
}

fun AppCompatActivity.replaceFragment(tag: String?, layoutRes: Int, fragment: () -> Fragment) {
    supportFragmentManager.beginTransaction()
        .replace(layoutRes, fragment.invoke(), tag)
        .commit()
}

fun AppCompatActivity.addFragmentToBackStack(tag: String?, layoutRes: Int, fragment: () -> Fragment) {
    supportFragmentManager.beginTransaction()
        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
            R.anim.enter_from_left, R.anim.exit_to_right)
        .replace(layoutRes, fragment.invoke(), tag)
        .addToBackStack(tag)
        .commit()
}

fun AppCompatActivity.clearStack() {
    while (supportFragmentManager.backStackEntryCount > 0) {
        supportFragmentManager.popBackStackImmediate()
    }
}

fun AppCompatActivity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun AppCompatActivity.showToast(resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
}