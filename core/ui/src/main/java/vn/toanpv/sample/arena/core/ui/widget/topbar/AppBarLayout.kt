package vn.toanpv.sample.arena.core.ui.widget.topbar

import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.view.updateLayoutParams
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import kotlin.math.abs

fun AppBarLayout.syncTitle(
    title: String,
    tvTitle: View,
    toolbar: MaterialToolbar,
    collapsingToolbarLayout: CollapsingToolbarLayout
) {
    var titleHeight = 0
    tvTitle.viewTreeObserver.addOnGlobalLayoutListener(object :
        ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            titleHeight = tvTitle.height
            tvTitle.viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    })
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                val distance = abs(appBarLayout.totalScrollRange + verticalOffset)
                val toolbarHeight = toolbar.measuredHeight
                val newHeight = if (distance < toolbarHeight) {
                    (distance.toFloat() / toolbar.measuredHeight * titleHeight).toInt()
                } else
                    titleHeight //Fix for fast scroll

                //TODO Show toolbar only when it need show, its ok for title with 2 lines but not work for 1 line
                if (newHeight <= toolbar.measuredHeight / 3)
                    collapsingToolbarLayout.title = title
                else
                    collapsingToolbarLayout.title = ""
                tvTitle.updateLayoutParams<ViewGroup.LayoutParams> {
                    height =
                        if (newHeight == 0) -3//Hack for zero size in ConstraintLayout
                        else newHeight
                }
            }
        }
    })
}