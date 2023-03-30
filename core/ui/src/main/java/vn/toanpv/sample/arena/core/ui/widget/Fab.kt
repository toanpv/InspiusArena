package vn.toanpv.sample.arena.core.ui.widget

import androidx.core.widget.NestedScrollView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton


fun NestedScrollView.scrollWithEfab(fab: ExtendedFloatingActionButton) {
    setOnScrollChangeListener(OnScrollChangeWithFab(fab))
}

class OnScrollChangeWithFab(private val fab: ExtendedFloatingActionButton) :
    NestedScrollView.OnScrollChangeListener {
    override fun onScrollChange(
        v: NestedScrollView,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        if (scrollY > oldScrollY + 6 && fab.isExtended) {
            fab.show()
            fab.shrink()
        }

        if (scrollY < oldScrollY - 6 && !fab.isExtended) {
            fab.show()
            fab.extend()
        }

        if (scrollY == 0) {
            fab.show()
            fab.extend()
        }
        if (v.getChildAt(0).bottom <= v.height + scrollY) {
            fab.hide()
        }
    }

}