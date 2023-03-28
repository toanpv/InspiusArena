package vn.inspius.toanpv.arena.core.ui.widget

import android.widget.ImageView
import coil.dispose
import coil.load
import coil.transform.CircleCropTransformation

fun ImageView.loadOrClear(url: String?) {
    if (url.isNullOrEmpty()) {
        dispose()
        setImageDrawable(null)
    } else {
        load(url) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
    }
}