package com.common

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * @author mawenqing.
 */
fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(message: String) {
    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}