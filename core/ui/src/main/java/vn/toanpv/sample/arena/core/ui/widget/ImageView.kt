package vn.toanpv.sample.arena.core.ui.widget

import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.dispose
import coil.load
import coil.request.ImageRequest
import coil.transform.Transformation

fun ImageView.loadOrClear(
    url: String?,
    transformations: List<Transformation>? = null,
    @DrawableRes error: Int? = null,
    builder: ImageRequest.Builder.() -> Unit = {}
) {
    if (error != null) {
        load(url) {
            crossfade(true)
            if (!transformations.isNullOrEmpty())
                transformations(transformations)
            this.error(error)
            builder.invoke(this)
        }
    } else {
        if (url.isNullOrEmpty() || url == "N/A") {
            dispose()
            setImageDrawable(null)
        } else {
            load(url) {
                crossfade(true)
                if (!transformations.isNullOrEmpty())
                    transformations(transformations)
                builder.invoke(this)
            }
        }
    }
}