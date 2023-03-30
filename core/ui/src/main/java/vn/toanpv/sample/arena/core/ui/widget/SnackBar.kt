package vn.toanpv.sample.arena.core.ui.widget

import android.view.View
import androidx.annotation.StringRes
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

fun ViewBinding.snackbar(
    @StringRes message: Int,
    duration: Int = Snackbar.LENGTH_LONG,
    action: String? = null,
    listener: View.OnClickListener? = null
) {
    snackbar(root.context.getString(message), duration, action, listener)
}
fun ViewBinding.snackbar(
    message: String,
    duration: Int = Snackbar.LENGTH_LONG,
    action: String? = null,
    listener: View.OnClickListener? = null
) {
    root.snackbar(message, duration, action, listener)
}

fun View.snackbar(
    message: String,
    duration: Int = Snackbar.LENGTH_LONG,
    action: String? = null,
    listener: View.OnClickListener? = null
) {
    val builder = Snackbar.make(this, message, duration)
    if (action != null && listener != null) builder.setAction(action, listener)
    builder.show()
}

fun View.snackbar(
    @StringRes message: Int,
    duration: Int = Snackbar.LENGTH_LONG,
    action: String? = null,
    listener: View.OnClickListener? = null
) {
    snackbar(context.getString(message), duration, action, listener)
}